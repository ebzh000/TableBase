package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 *
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.common.IncompatibleCategoryTypeException;
import com.ez.tablebase.rest.common.InvalidOperationException;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.common.OperationUtils;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.model.requests.CategoryCombineRequest;
import com.ez.tablebase.rest.model.requests.CategoryRequest;
import com.ez.tablebase.rest.model.requests.CategorySplitRequest;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@javax.transaction.Transactional
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    private final TableRepository tableRepository;
    private final DataAccessPathRepository dataAccessPathRepository;
    private final TableEntryRepository tableEntryRepository;

    private final static String EMPTY_STRING = "";

    public CategoryServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository,
                               DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        this.categoryRepository = categoryRepository;
        this.tableRepository = tableRepository;
        this.dataAccessPathRepository = dataAccessPathRepository;
        this.tableEntryRepository = tableEntryRepository;
    }

    @Override
    public Category createCategory(CategoryRequest request)
    {
        CategoryEntity entity = createCategory(request.getTableId(), request.getAttributeName(), request.getParentId(), (byte) request.getType().ordinal());

        // We need to create the entries and data access paths related to the new category
        List<CategoryEntity> rootCategories = categoryRepository.findRootNodes(entity.getTableId());
        CategoryEntity category1 = rootCategories.get(0);
        CategoryEntity category2 = rootCategories.get(1);

        List<Integer> categoryList1 = categoryRepository.getAllChildren(category1.getTableId(), category1.getCategoryId());
        List<Integer> categoryList2 = categoryRepository.getAllChildren(category2.getTableId(), category2.getCategoryId());

        if (categoryList1.contains(entity.getCategoryId()))
            initialiseEntries(entity, categoryList1, categoryList2);
        else if (categoryList2.contains(entity.getCategoryId()))
            initialiseEntries(entity, categoryList2, categoryList1);

        return Category.buildModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getTableCategories(int tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<CategoryEntity> entities = categoryRepository.findAllTableCategories(tableEntity.getTableId());
        List<Category> models = new ArrayList<>();
        entities.forEach(entity -> models.add(Category.buildModel(entity)));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = validateCategory(tableId, categoryId);
        return Category.buildModel(entity);
    }

    @Override
    public Category updateCategory(CategoryRequest request)
    {
        CategoryEntity entity = validateCategory(request.getTableId(), request.getCategoryId());
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.updateTableCategory(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), entity.getType());
        return Category.buildModel(entity);
    }

    @Override
    public void duplicateCategory(int tableId, int categoryId)
    {
        CategoryEntity category = validateCategory(tableId, categoryId);

        if (category.getParentId() == null)
            throw new InvalidOperationException("Invalid Operation! Resultant table will no longer be an abstract table");

        // Need to prompt user with a confirmation if the selected category is the root node of the whole tree (categories are in a tree structure)
        duplicateCategories(category, category.getParentId(), category.getCategoryId());
    }

    @Override
    public Category combineCategory(CategoryCombineRequest request) throws ParseException
    {
        CategoryEntity category1 = validateCategory(request.getTableId(), request.getCategoryId1());
        CategoryEntity category2 = validateCategory(request.getTableId(), request.getCategoryId2());

        if (category1.getType() != category2.getType())
            throw new IncompatibleCategoryTypeException("Specified categories have different types");

        // We must restrict this operation to only leaf nodes of the category tree
        // In other words, we throw an exception when the selected category has children
        List<CategoryEntity> children1 = categoryRepository.findChildren(category1.getTableId(), category1.getCategoryId());
        List<CategoryEntity> children2 = categoryRepository.findChildren(category2.getTableId(), category2.getCategoryId());
        if (children1.get(0) != null || children2.get(0) != null)
            throw new InvalidOperationException("Invalid Operation! Selected categories must not have any subcategories");

        categoryRepository.updateTableCategory(category1.getTableId(), category1.getCategoryId(), request.getNewCategoryName(), category1.getParentId(), category1.getType());
        combineEntries(category1, category2, OperationType.values()[request.getDataOperationType()]);

        categoryRepository.delete(category2);
        category1 = categoryRepository.findCategory(category1.getTableId(), category1.getCategoryId());
        return Category.buildModel(category1);
    }

    @Override
    public void splitCategory(CategorySplitRequest request)
    {
        CategoryEntity category = validateCategory(request.getTableId(), request.getCategoryId());

        // We must restrict this operation to only leaf nodes of the category tree
        // In other words, we throw an exception when the selected category has children
        List<CategoryEntity> children = categoryRepository.findChildren(category.getTableId(), category.getCategoryId());
        if (children.get(0) != null)
            throw new InvalidOperationException("Invalid Operation! Selected category must not have any subcategories");

        CategoryEntity newCategory = createCategory(category.getTableId(), request.getNewCategoryName(), category.getParentId(), category.getType());
        splitCategory(category, newCategory, request.getThreshold());
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryRepository.delete(entity);
    }

    private CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, byte type)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setAttributeName(attributeName);
        entity.setParentId(parentId);
        entity.setType(type);
        categoryRepository.save(entity);
        entity = categoryRepository.findCategory(entity.getTableId(), entity.getCategoryId());

        return entity;
    }

    private void duplicateCategories(CategoryEntity entity, Integer newParentId, Integer rootCategoryId)
    {
        CategoryEntity newCategory = createCategory(entity.getTableId(), entity.getAttributeName(), newParentId, entity.getType());

        // Get the all children that are being duplicated so we can then duplicate the dataAccessPaths correctly.
        List<Integer> affectedCategories = categoryRepository.getAllChildren(entity.getTableId(), rootCategoryId);

        List<CategoryEntity> children = categoryRepository.findChildren(entity.getTableId(), entity.getCategoryId());
        if (!children.isEmpty()) {
            // If the first child is not null then we must recursively duplicate the children of this node
            if (children.get(0) != null)
                for (CategoryEntity child : children)
                    duplicateCategories(child, newCategory.getCategoryId(), rootCategoryId);

                // If the first child is null then we must now duplicate all entries for each DataAccessPaths (DAP) that ends with this child's category_id
                // 1. Get all DAPs that end with this category
                // 2. Loop through
                //    a. Get entry for DAP
                //    b. Clone the entry and then the new DAP
            else {
                List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(entity.getTableId(), entity.getCategoryId());
                if (!entries.isEmpty()) {
                    for (Integer entry : entries) {
                        EntryEntity newEntry = duplicateEntry(entity.getTableId(), entry);

                        // Need to get full path of the current entry
                        List<DataAccessPathEntity> origPath = dataAccessPathRepository.getEntryAccessPath(entity.getTableId(), entry);
                        for (DataAccessPathEntity pathEntity : origPath) {
                            // Duplicate DataAccessPath
                            DataAccessPathEntity newPath = new DataAccessPathEntity();
                            newPath.setEntryId(newEntry.getEntryId());
                            newPath.setTableId(newEntry.getTableId());
                            newPath.setCategoryId((affectedCategories.contains(pathEntity.getCategoryId())) ? newCategory.getCategoryId() : pathEntity.getCategoryId());
                            dataAccessPathRepository.save(newPath);
                        }
                    }
                }
            }
        }
    }

    private EntryEntity duplicateEntry(Integer tableId, Integer entryId)
    {
        EntryEntity entity = validateEntry(tableId, entryId);
        EntryEntity newEntity = new EntryEntity();
        newEntity.setTableId(entity.getTableId());
        newEntity.setData(entity.getData());
        return tableEntryRepository.save(newEntity);
    }

    private void combineEntries(CategoryEntity category1, CategoryEntity category2, OperationType operationType) throws ParseException
    {
        List<Integer> category1Entries = dataAccessPathRepository.getEntriesForCategory(category1.getTableId(), category1.getCategoryId());
        List<Integer> category2Entries = dataAccessPathRepository.getEntriesForCategory(category2.getTableId(), category2.getCategoryId());

        for (int index = 0; index < category2Entries.size(); index++) {
            EntryEntity entry1 = tableEntryRepository.findTableEntry(category1.getTableId(), category1Entries.get(index));
            EntryEntity entry2 = tableEntryRepository.findTableEntry(category2.getTableId(), category2Entries.get(index));

            String data1 = entry1.getData();
            String data2 = entry2.getData();

            switch (operationType) {
                case MAX:
                    data1 = OperationUtils.max(data1, data2, category1.getType());
                    break;
                case MIN:
                    data1 = OperationUtils.min(data1, data2, category1.getType());
                    break;
                case MEAN:
                    data1 = OperationUtils.mean(data1, data2, category1.getType());
                    break;
                case SUM:
                    data1 = OperationUtils.sum(data1, data2, category1.getType());
                    break;
                case DIFFERENCE:
                    data1 = OperationUtils.difference(data1, data2, category1.getType());
                    break;
                case CONCATENATE_STRING:
                    data1 = OperationUtils.concatenateString(data1, data2, category1.getType());
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    data1 = data2;
                    break;
                case NO_OPERATION:
                    break;
            }

            tableEntryRepository.updateTableEntry(entry1.getTableId(), entry1.getEntryId(), data1);
            tableEntryRepository.delete(entry2);
        }
    }

    private void splitCategory(CategoryEntity category, CategoryEntity newCategory, String threshold)
    {
        // Get entries of both categories

        // Perform Operation that can be one of: NO_OPERATION, COPY, THRESHOLD
        // This will involve moving the
    }

    private EntryEntity createEntry(Integer tableId, String data)
    {
        EntryEntity entry = new EntryEntity();
        entry.setTableId(tableId);
        entry.setData(data);
        return tableEntryRepository.save(entry);
    }

    private DataAccessPathEntity createDataAccessPath(Integer tableId, Integer entryId, Integer categoryId)
    {
        DataAccessPathEntity dap = new DataAccessPathEntity();
        dap.setTableId(tableId);
        dap.setEntryId(entryId);
        dap.setCategoryId(categoryId);
        return dataAccessPathRepository.save(dap);
    }

    private void initialiseEntries(CategoryEntity entity, List<Integer> relativeCategoryTree, List<Integer> accessCategoryList)
    {
        List<CategoryEntity> accessLeafNodes = new LinkedList<>();

        // We must find all leaf nodes of the access category list
        for(Integer categoryId : accessCategoryList)
        {
            CategoryEntity category = validateCategory(entity.getTableId(), categoryId);
            List<CategoryEntity> children = categoryRepository.findChildren(category.getTableId(), category.getCategoryId());
            if(children.get(0) == null)
                accessLeafNodes.add(category);
        }


    }

    private TableEntity validateTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }

    private CategoryEntity validateCategory(int tableId, int categoryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        CategoryEntity entity = categoryRepository.findCategory(tableEntity.getTableId(), categoryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Category found for category id: " + categoryId + ", in table id: " + tableId);

        return entity;
    }

    private EntryEntity validateEntry(int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        EntryEntity entity = tableEntryRepository.findTableEntry(tableEntity.getTableId(), entryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Entry found for entry id: " + entryId + ", in table id: " + tableId);

        return entity;
    }
}
