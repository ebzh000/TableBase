package com.ez.tablebase.rest.service.utils;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CategoryUtils extends BaseUtils
{

    public CategoryUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void createCategoryDAPsAndEntries(CategoryEntity category, CategoryEntity parentCategory, boolean linkChildren)
    {
        Integer treeId = getTreeId(category);

        // Need to check if the parent category has no children. This implies that the parentId currently has DAPs and Entries initialised.
        // Which then implies that we need to update the DAPs to include the new category
        List<CategoryEntity> children = findChildren(parentCategory.getTableId(), parentCategory.getCategoryId());
        // Remove the new category that we just created
        children.remove(category);

        // There are other children for the parent, we must create new DAPs and Entries with relation to the correct tree
        if (children.size() != 0)
        {
            // If we want to link the current children of the parent category to the new category,
            // we need to get all the children to point to the new category and update the current data access paths containing the child category
            if (linkChildren)
            {
                for (CategoryEntity child : children)
                {
                    updateTableCategory(child.getTableId(), child.getCategoryId(), child.getAttributeName(), category.getCategoryId(), child.getType());

                    List<Integer> affectedEntries = dataAccessPathRepository.getEntryByPathContainingCategory(category.getTableId(), category.getCategoryId());
                    List<List<DataAccessPathEntity>> affectedPaths = new LinkedList<>();

                    for (Integer entryId : affectedEntries)
                        affectedPaths.add(dataAccessPathRepository.getEntryAccessPathByTree(category.getTableId(), entryId, treeId));

                    for (List<DataAccessPathEntity> dap : affectedPaths)
                    {
                        Integer tableId = dap.get(0).getTableId();
                        Integer entryId = dap.get(0).getEntryId();

                        createDataAccessPath(tableId, entryId, category.getCategoryId(), treeId);
                    }
                }
            }

            // If we don't want to link the current children of the parent category to the new category,
            // we will then create a new child category for the parent
            else
            {
                List<CategoryEntity> rootNodes = findRootNodes(category.getTableId());
                Map<Integer, List<CategoryEntity>> treeMap1 = constructTreeMap(rootNodes.get(0));
                Map<Integer, List<CategoryEntity>> treeMap2 = constructTreeMap(rootNodes.get(1));

                if (treeId == 1)
                    initialiseEntries(category, treeMap1.get(category.getCategoryId()), treeMap2, treeId);
                else if (treeId == 2)
                    initialiseEntries(category, treeMap2.get(category.getCategoryId()), treeMap1, treeId);
            }
        }

        // Since the parent category has no children we must then update the data access paths that end with the parent category
        // to now end with the new category
        else
            updateDataAccessPaths(category, parentCategory, treeId);
    }

    public void duplicateCategories(CategoryEntity entity, Integer newParentId, Integer rootCategoryId)
    {
        CategoryEntity newCategory = createCategory(entity.getTableId(), entity.getAttributeName(), newParentId, entity.getType());

        // Get the all children that are being duplicated so we can then duplicate the dataAccessPaths correctly.
        List<Integer> affectedCategories = getAllCategoryChildren(entity.getTableId(), rootCategoryId);

        List<CategoryEntity> children = categoryRepository.findChildren(entity.getTableId(), entity.getCategoryId());
        if (!children.isEmpty())
        {
            // If the first child is not null then we must recursively duplicate the children of this node
            if (children.get(0) != null)
                for (CategoryEntity child : children)
                    duplicateCategories(child, newCategory.getCategoryId(), rootCategoryId);

                // If the first child is null then we must now duplicate all entries for each DataAccessPaths (DAP) that ends with this child's category_id
                // 1. Get all DAPs that end with this category
                // 2. Loop through
                //    a. Get entry for DAP
                //    b. Clone the entry and then the new DAP
            else
            {
                List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(entity.getTableId(), entity.getCategoryId());
                if (!entries.isEmpty())
                {
                    for (Integer entry : entries)
                    {
                        EntryEntity newEntry = duplicateEntry(entity.getTableId(), entry);

                        // Need to get full path of the current entry
                        List<DataAccessPathEntity> origPath = dataAccessPathRepository.getEntryAccessPath(entity.getTableId(), entry);
                        for (DataAccessPathEntity pathEntity : origPath)
                        {
                            // Duplicate DataAccessPath
                            DataAccessPathEntity newPath = new DataAccessPathEntity();
                            newPath.setEntryId(newEntry.getEntryId());
                            newPath.setTableId(newEntry.getTableId());
                            newPath.setCategoryId((affectedCategories.contains(pathEntity.getCategoryId())) ? newCategory.getCategoryId() : pathEntity.getCategoryId());
                            newPath.setTreeId(pathEntity.getTreeId());
                            dataAccessPathRepository.save(newPath);
                        }
                    }
                }
            }
        }
    }

    public void splitCategory(CategoryEntity category, CategoryEntity newCategory, String threshold)
    {
        // Get entries of both categories

        // Perform Operation that can be one of: NO_OPERATION, COPY, THRESHOLD
        // This will involve moving the
    }

    public List<CategoryEntity> findAllTableCategories(Integer tableId)
    {
        return categoryRepository.findAllTableCategories(tableId);
    }

    public void updateTableCategory(Integer tableId, Integer categoryId, String attributeName, Integer parentId, byte type)
    {
        categoryRepository.updateTableCategory(tableId, categoryId, attributeName, parentId, type);
    }

    public void deleteCategory(CategoryEntity category)
    {
        categoryRepository.delete(category);
    }

    public CategoryEntity findCategory(Integer tableId, Integer categoryId)
    {
        return categoryRepository.findCategory(tableId, categoryId);
    }
}
