package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Erik Zhong on 9/6/2017.
 */

@Service
public class TableServiceImpl implements TableService
{
    private final TableRepository tableRepository;

    public TableServiceImpl(TableRepository tableRepository)
    {
        this.tableRepository = tableRepository;
    }

    @Override
    public TableModel createTable(int userId)
    {
        return tableRepository.createTable(userId);
    }

    @Override
    public TableModel getTable(int tableId)
    {
        return null;
    }

    @Override
    public TableModel getUserTables(int userId)
    {
        return null;
    }

    @Transactional(readOnly = true)
    public List<TableModel> getTables() throws RuntimeException
    {
        Iterable<TableEntity> entities = tableRepository.findAll();

        List<TableModel> models = new ArrayList<>();

        entities.forEach(entity -> models.add(TableModelBuilder.buildModel(entity.getTableId(), entity.getUserId())));

        return models;
    }

    @Override
    public void deleteTable(int id)
    {
    }

    @Override
    public CategoryEntity createCategory(int tableId, int categoryId, String attributeName, int parentId, DataType type)
    {
        return null;
    }

    @Override
    public List<CategoryEntity> getTableCategories(int tableId)
    {
        return null;
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
    }

    private TableEntity validateTable(String tableId)
    {
        if (tableId == null)
            throw new IllegalArgumentException("Table ID must be provided to get tables");

        TableEntity entity = tableRepository.findOne(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }
}