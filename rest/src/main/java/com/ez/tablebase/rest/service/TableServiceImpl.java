package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.requests.TableRequest;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
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
    private final TableRepository tableRepository;

    public TableServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository, TableEntryRepository tableEntryRepository, DataAccessPathRepository dataAccessPathRepository)
    {
        this.tableRepository = tableRepository;
    }

    @Override
    public Table createTable(TableRequest request)
    {
        TableEntity newTable = new TableEntity();
        newTable.setUserId(request.getUserId());
        newTable.setTableName(request.getTableName());
        newTable.setTags(request.getTags());
        newTable.setPublic(request.getPublic());
        TableEntity entity = tableRepository.save(newTable);

        

        return Table.buildModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Table getTable(int tableId)
    {
        TableEntity entity = validateTable(tableId);
        return Table.buildModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Table getUserTables(int userId)
    {
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Table> searchTable(String keyword)
    {
        List<TableEntity> entities = tableRepository.searchTable(keyword);
        List<Table> models = new ArrayList<>();
        entities.forEach(entity -> models.add(Table.buildModel(entity)));
        return models;
    }

    @Transactional(readOnly = true)
    public List<Table> getTables() throws RuntimeException
    {
        Iterable<TableEntity> entities = tableRepository.findAll();
        List<Table> models = new ArrayList<>();
        entities.forEach(entity -> models.add(Table.buildModel(entity)));
        return models;
    }

    @Override
    public void deleteTable(int tableId)
    {
        TableEntity entity = new TableEntity();
        entity.setUserId(1);
        entity.setTableId(tableId);
        tableRepository.delete(entity);
    }

    private TableEntity validateTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }
}