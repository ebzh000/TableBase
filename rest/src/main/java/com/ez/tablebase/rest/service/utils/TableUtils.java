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
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.util.List;

public class TableUtils extends BaseUtils
{

    private static final String EMPTY_STRING = "";

    public TableUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        super(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    /* We need to create 2 parent categories that have 1 category each
     *
     * +-----------------------+--------------------+
     * | New Access Category   | New Category       |
     * +-----------------------+--------------------+
     * | New Access            | New Entry          |
     * +-----------------------+--------------------+
     *
     * We also need to create a data access path and an entry
     */
    public void initialiseBasicTable(TableEntity entity)
    {
        // Creating categories
        CategoryEntity newAccessHeader = createCategory(entity.getTableId(), "Access Header", null, DataType.UNKNOWN);
        CategoryEntity newAccess = createCategory(entity.getTableId(), "Access Category", newAccessHeader.getCategoryId(), DataType.UNKNOWN);
        CategoryEntity newCategory = createCategory(entity.getTableId(), "Category", null, DataType.UNKNOWN);

        // Creating Entry
        EntryEntity newEntry = createEntry(entity.getTableId(), EMPTY_STRING);

        // Create Data Access Path for the new entry
        createDataAccessPath(entity.getTableId(), newEntry.getEntryId(), newAccessHeader.getCategoryId());
        createDataAccessPath(entity.getTableId(), newEntry.getEntryId(), newAccess.getCategoryId());
        createDataAccessPath(entity.getTableId(), newEntry.getEntryId(), newCategory.getCategoryId());
    }

    public List<TableEntity> searchTable(String keyword)
    {
        return tableRepository.searchTable(keyword);
    }

    public List<TableEntity> getUserTables(int userId)
    {
        return tableRepository.getUserTables(userId);
    }

    public Iterable<TableEntity> findAll()
    {
        return tableRepository.findAll();
    }

    public void delete(TableEntity entity)
    {
        tableRepository.delete(entity);
    }

}
