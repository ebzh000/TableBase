package com.ez.tablebase.rest.common.utils;
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
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataAccessPathUtils extends BaseUtils
{

    public DataAccessPathUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    public void updateDataAccessPaths(CategoryEntity entity, Integer oldParentId, Integer newParentId)
    {
        // We need to create the entries and data access paths related to the new category
        List<CategoryEntity> rootCategories = categoryRepository.findRootNodes(entity.getTableId());
        CategoryEntity category1 = rootCategories.get(0);
        CategoryEntity category2 = rootCategories.get(1);

        // By retrieving all children of the given root node, we are able to determine which tree the new category is located in
        List<Integer> categoryList1 = categoryRepository.getAllChildren(category1.getTableId(), category1.getCategoryId());
        List<Integer> categoryList2 = categoryRepository.getAllChildren(category2.getTableId(), category2.getCategoryId());

        Map<Integer, List<CategoryEntity>> treeMap1 = constructTreeMap(category1);
        Map<Integer, List<CategoryEntity>> treeMap2 = constructTreeMap(category2);

        // Get all entries that have a DAP that contains the affected category in its path
        List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(entity.getTableId(), entity.getCategoryId());
        List<List<DataAccessPathEntity>> daps = new LinkedList<>();

        for (Integer entry : entries)
        {
            List<DataAccessPathEntity> dap = new LinkedList<>();
            dap.addAll(dataAccessPathRepository.getEntryAccessPath(entity.getTableId(), entry));

            // We need to remove any categories that are not part of the affected tree
            for (DataAccessPathEntity pathEntity : dap)
            {
                if (categoryList1.contains(entity.getCategoryId()))
                    if (categoryList2.contains(pathEntity.getCategoryId()))
                        dap.remove(pathEntity);
                    else
                    if (categoryList1.contains(pathEntity.getCategoryId()))
                        dap.remove(pathEntity);
            }

            System.out.print("Entry: " + entry + "; Path: ");
            dap.forEach(data -> System.out.print(data.getCategoryId() + " "));
            System.out.println();
            daps.add(dap);
        }

        for (List<DataAccessPathEntity> path : daps)
        {
            for (DataAccessPathEntity dap : path)
            {

            }
        }
    }
}
