package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.common.IncompatibleCategoryTypeException;
import com.ez.tablebase.rest.common.InvalidOperationException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.Category;
import com.ez.tablebase.rest.model.OperationType;
import com.ez.tablebase.rest.model.requests.CategoryCombineRequest;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import com.ez.tablebase.rest.model.requests.CategorySplitRequest;
import com.ez.tablebase.rest.model.requests.CategoryUpdateRequest;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import com.ez.tablebase.rest.service.utils.CategoryUtils;
import com.ez.tablebase.rest.service.utils.DataAccessPathUtils;
import com.ez.tablebase.rest.service.utils.TableEntryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@javax.transaction.Transactional
public class CategoryServiceImpl implements CategoryService
{
    private CategoryUtils categoryUtils;
    private DataAccessPathUtils dapUtils;
    private TableEntryUtils entryUtils;

    public CategoryServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository,
                               DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        this.categoryUtils = new CategoryUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.dapUtils = new DataAccessPathUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.entryUtils = new TableEntryUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    @Override
    public Category createCategory(CategoryCreateRequest request)
    {
        CategoryEntity category = categoryUtils.createCategory(request.getTableId(), request.getAttributeName(), request.getParentId(), (byte) request.getType().ordinal());
        CategoryEntity parentCategory = categoryUtils.validateCategory(category.getTableId(), category.getParentId());

        // Creating/Updating data access paths and entries
        categoryUtils.createCategoryDAPsAndEntries(category, parentCategory, request.isLinkChildren());
        return Category.buildModel(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getTableCategories(int tableId)
    {
        TableEntity tableEntity = categoryUtils.validateTable(tableId);
        List<CategoryEntity> entities = categoryUtils.findAllTableCategories(tableEntity.getTableId());
        List<Category> models = new ArrayList<>();
        entities.forEach(entity -> models.add(Category.buildModel(entity)));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = categoryUtils.validateCategory(tableId, categoryId);
        return Category.buildModel(entity);
    }

    @Override
    public Category updateCategory(CategoryUpdateRequest request)
    {
        CategoryEntity category = categoryUtils.validateCategory(request.getTableId(), request.getCategoryId());
        CategoryEntity oldParent = categoryUtils.validateCategory(category.getTableId(), category.getParentId());
        CategoryEntity newParent = categoryUtils.validateCategory(category.getTableId(), request.getParentId());

        if(!Objects.equals(categoryUtils.getTreeId(category), categoryUtils.getTreeId(newParent)))
            throw new InvalidOperationException("INVALID OPERATION! Unable to update current category's parent to a new parent that is in another category tree");

        if (newParent.getType() != category.getType())
            throw new IncompatibleCategoryTypeException("New parent and select category have incompatible types");

        if (!newParent.getCategoryId().equals(oldParent.getCategoryId()))
        {
            // Check if the new parent has any children. If not, create a child category for the new parent and update all data access paths
            List<CategoryEntity> newParentChildren = categoryUtils.findChildren(newParent.getTableId(), newParent.getCategoryId());
            if (newParentChildren.size() == 0)
            {
                CategoryEntity newChildCategory = categoryUtils.createCategory(newParent.getTableId(), "New Child", newParent.getCategoryId(), newParent.getType());
                dapUtils.updateDataAccessPaths(newChildCategory, newParent, categoryUtils.getTreeId(newParent));
            }

            categoryUtils.updateTableCategory(category.getTableId(), category.getCategoryId(), request.getAttributeName(), request.getParentId(), (byte) request.getType().ordinal());

            // If the desired category has a new parent, we must now update all of the data access paths that are affected by this operation.
            dapUtils.updateDataAccessPaths(category, oldParent, newParent);
        }
        else
            categoryUtils.updateTableCategory(category.getTableId(), category.getCategoryId(), request.getAttributeName(), request.getParentId(), (byte) request.getType().ordinal());

        category = categoryUtils.findCategory(category.getTableId(), category.getCategoryId());
        return Category.buildModel(category);
    }

    @Override
    public void duplicateCategory(int tableId, int categoryId)
    {
        CategoryEntity category = categoryUtils.validateCategory(tableId, categoryId);

        if (category.getParentId() == null)
            throw new InvalidOperationException("Invalid Operation! Resultant table will no longer be an abstract table");

        // Need to prompt user with a confirmation if the selected category is the root node of the whole tree (categories are in a tree structure)
        categoryUtils.duplicateCategories(category, category.getParentId(), category.getCategoryId());
    }

    @Override
    public Category combineCategory(CategoryCombineRequest request) throws ParseException
    {
        CategoryEntity category1 = categoryUtils.validateCategory(request.getTableId(), request.getCategoryId1());
        CategoryEntity category2 = categoryUtils.validateCategory(request.getTableId(), request.getCategoryId2());

        if (category1.getType() != category2.getType())
            throw new IncompatibleCategoryTypeException("Specified categories have different types");

        // We must restrict this operation to only leaf nodes of the category tree
        // In other words, we throw an exception when the selected category has children
        List<CategoryEntity> children1 = categoryUtils.findChildren(category1.getTableId(), category1.getCategoryId());
        List<CategoryEntity> children2 = categoryUtils.findChildren(category2.getTableId(), category2.getCategoryId());
        if (children1.get(0) != null || children2.get(0) != null)
            throw new InvalidOperationException("Invalid Operation! Selected categories must not have any subcategories");

        categoryUtils.updateTableCategory(category1.getTableId(), category1.getCategoryId(), request.getNewCategoryName(), category1.getParentId(), category1.getType());
        entryUtils.combineEntries(category1, category2, OperationType.values()[request.getDataOperationType()]);

        categoryUtils.deleteCategory(category2);
        category1 = categoryUtils.findCategory(category1.getTableId(), category1.getCategoryId());
        return Category.buildModel(category1);
    }

    @Override
    public void splitCategory(CategorySplitRequest request)
    {
        CategoryEntity category = categoryUtils.validateCategory(request.getTableId(), request.getCategoryId());

        // We must restrict this operation to only leaf nodes of the category tree
        // In other words, we throw an exception when the selected category has children
        List<CategoryEntity> children = categoryUtils.findChildren(category.getTableId(), category.getCategoryId());
        if (children.get(0) != null)
            throw new InvalidOperationException("Invalid Operation! Selected category must not have any subcategories");

        CategoryEntity newCategory = categoryUtils.createCategory(category.getTableId(), request.getNewCategoryName(), category.getParentId(), category.getType());
        categoryUtils.splitCategory(category, newCategory, request.getThreshold());
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryUtils.deleteCategory(entity);
    }
}
