package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public TableServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository)
    {
        this.tableRepository = tableRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TableModel createTable(TableRequest request)
    {
        TableEntity newTable = new TableEntity();
        newTable.setUserId(request.getUserId());
        newTable.setTableName(request.getTableName());
        newTable.setTags(request.getTags());
        TableEntity entity = tableRepository.save(newTable);
        return TableModelBuilder.buildModel(entity.getTableId(), entity.getUserId(), entity.getTableName(), entity.getTags());
    }

    @Override
    public TableModel getTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);
        return TableModelBuilder.buildModel(entity.getTableId(), entity.getUserId(), entity.getTableName(), entity.getTags());
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
        entities.forEach(entity -> models.add(TableModelBuilder.buildModel(entity.getTableId(), entity.getUserId(), entity.getTableName(), entity.getTags())));

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

    @Override
    public CategoryEntity createCategory(int tableId, int categoryId, String attributeName, int parentId, DataType type)
    {
        return null;
    }

    @Override
    public List<CategoryModel> getTableCategories(int tableId)
    {
        List<CategoryEntity> entities = categoryRepository.findAllTableCategories(tableId);
        List<CategoryModel> models = new ArrayList<>();
        entities.forEach(entity -> models.add(CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()])));
        return models;
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