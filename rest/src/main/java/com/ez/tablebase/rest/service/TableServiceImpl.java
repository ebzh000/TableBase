package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.requests.TableRequest;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import com.ez.tablebase.rest.service.utils.CategoryUtils;
import com.ez.tablebase.rest.service.utils.DataAccessPathUtils;
import com.ez.tablebase.rest.service.utils.TableEntryUtils;
import com.ez.tablebase.rest.service.utils.TableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Erik Zhong on 9/6/2017.
 */

@Service
@javax.transaction.Transactional
public class TableServiceImpl implements TableService
{
    private TableUtils tableUtils;
    private CategoryUtils categoryUtils;
    private DataAccessPathUtils dapUtils;
    private TableEntryUtils entryUtils;

    private static final String EMPTY_STRING = "";

    public TableServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository, TableEntryRepository tableEntryRepository, DataAccessPathRepository dataAccessPathRepository)
    {
        this.tableUtils = new TableUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.categoryUtils = new CategoryUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.dapUtils = new DataAccessPathUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.entryUtils = new TableEntryUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    @Override
    public Table createTable(TableRequest request)
    {
        TableEntity newTable = tableUtils.createTable(request.getUserId(), request.getTableName(), request.getTags(), request.getPublic());

        // We need to set up a basic table
        tableUtils.initialiseBasicTable(newTable);

        return Table.buildModel(newTable);
    }

    @Override
    @Transactional(readOnly = true)
    public Table getTable(int tableId)
    {
        TableEntity entity = tableUtils.validateTable(tableId);
        return Table.buildModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Table> getUserTables(int userId)
    {
        List<TableEntity> tables = tableUtils.getUserTables(userId);
        List<Table> models = new ArrayList<>();
        tables.forEach(table -> models.add(Table.buildModel(table)));
        return models;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Table> searchTable(String keyword)
    {
        List<TableEntity> entities = tableUtils.searchTable(keyword);
        List<Table> models = new ArrayList<>();
        entities.forEach(entity -> models.add(Table.buildModel(entity)));
        return models;
    }

    @Transactional(readOnly = true)
    public List<Table> getTables() throws RuntimeException
    {
        Iterable<TableEntity> entities = tableUtils.findAll();
        List<Table> models = new ArrayList<>();
        entities.forEach(entity -> models.add(Table.buildModel(entity)));
        return models;
    }

    @Override
    public void deleteTable(int tableId)
    {
        TableEntity entity = tableUtils.validateTable(tableId);
        tableUtils.delete(entity);
    }
}