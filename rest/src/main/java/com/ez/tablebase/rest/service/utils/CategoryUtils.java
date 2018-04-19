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

import java.util.*;

public class CategoryUtils extends BaseUtils
{

    public CategoryUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void duplicateCategories(CategoryEntity entity, Integer newParentId, Integer rootCategoryId)
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

    public void splitCategory(CategoryEntity category, CategoryEntity newCategory, String threshold)
    {
        // Get entries of both categories

        // Perform Operation that can be one of: NO_OPERATION, COPY, THRESHOLD
        // This will involve moving the
    }

    public List<CategoryEntity> findChildren(Integer tableId, Integer categoryId)
    {
        return categoryRepository.findChildren(tableId, categoryId);
    }

    public List<CategoryEntity> findRootNodes(Integer tableId)
    {
        return categoryRepository.findRootNodes(tableId);
    }

    public List<Integer> getAllChildren(Integer tableId, Integer categoryId)
    {
        return categoryRepository.getAllChildren(tableId, categoryId);
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
