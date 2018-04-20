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
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.util.List;

public class DataAccessPathUtils extends BaseUtils
{

    public DataAccessPathUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void createPathsForEntry(List<Integer> categories, EntryEntity entity)
    {
        for(Integer category : categories)
        {
            List<CategoryEntity> rootCategories = findRootNodes(entity.getTableId());
            CategoryEntity category1 = rootCategories.get(0);
            CategoryEntity category2 = rootCategories.get(1);

            List<Integer> categoryList1 = getAllCategoryChildren(category1.getTableId(), category1.getCategoryId());
            List<Integer> categoryList2 = getAllCategoryChildren(category2.getTableId(), category2.getCategoryId());

            Integer treeId = null;
            if(categoryList1.contains(category))
                treeId = 1;
            else if(categoryList2.contains(category))
                treeId = 2;

            createDataAccessPath(entity.getTableId(), entity.getEntryId(), category, treeId);
        }
    }

}
