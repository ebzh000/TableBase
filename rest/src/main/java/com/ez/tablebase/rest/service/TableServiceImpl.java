package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.TableDataEntity;
import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final TableEntryRepository tableEntryRepository;

    public TableServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository, TableEntryRepository tableEntryRepository)
    {
        this.tableRepository = tableRepository;
        this.categoryRepository = categoryRepository;
        this.tableEntryRepository = tableEntryRepository;
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
    @Transactional(readOnly = true)
    public TableModel getTable(int tableId)
    {
        TableEntity entity = validateTable(tableId);
        return TableModelBuilder.buildModel(entity.getTableId(), entity.getUserId(), entity.getTableName(), entity.getTags());
    }

    @Override
    @Transactional(readOnly = true)
    public TableModel getUserTables(int userId)
    {
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public List<TableModel> searchTable(String keyword)
    {
        List<TableEntity> entities = tableRepository.searchTable(keyword);
        List<TableModel> models = new ArrayList<>();
        entities.forEach(entity -> models.add(TableModelBuilder.buildModel(entity.getTableId(), entity.getUserId(), entity.getTableName(), entity.getTags())));
        return models;
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
    public CategoryModel createCategory(CategoryRequest request)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(request.getTableId());
        entity.setCategoryId(request.getCategoryId());
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.save(entity);
        return CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()]);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryModel> getTableCategories(int tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<CategoryEntity> entities = categoryRepository.findAllTableCategories(tableEntity.getTableId());
        List<CategoryModel> models = new ArrayList<>();
        entities.forEach(entity -> models.add(CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()])));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryModel getCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = validateCategory(tableId, categoryId);
        return CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()]);
    }

    @Override
    public CategoryModel updateCategory(CategoryRequest request)
    {
        CategoryEntity entity = validateCategory(request.getTableId(), request.getCategoryId());
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.updateTableCateogry(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), entity.getType());
        return CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()]);
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryRepository.delete(entity);
    }

    @Override
    public DataModel createTableEntry(DataRequest request)
    {
        TableDataEntity entity = new TableDataEntity();
        entity.setTableId(request.getTableId());
        entity.setAccessId(request.getAccessId());
        entity.setHeaderId(request.getHeaderId());
        entity.setData(request.getData());
        tableEntryRepository.save(entity);
        return DataModelBuilder.buildModel(entity.getTableId(), entity.getAccessId(), entity.getHeaderId(), entity.getData());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataModel> getTableEntries(int tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<TableDataEntity> entities = tableEntryRepository.findAllTableEntries(tableEntity.getTableId());
        List<DataModel> models = new ArrayList<>();
        entities.forEach(row -> models.add(DataModelBuilder.buildModel(row.getTableId(), row.getAccessId(), row.getHeaderId(), row.getData())));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public DataModel getTableEntry(int tableId, int accessId, int headerId)
    {
        TableDataEntity entity = validateTableEntry(tableId, accessId, headerId);
        return DataModelBuilder.buildModel(entity.getTableId(),entity.getAccessId(),entity.getHeaderId(),entity.getData());
    }

    @Override
    public DataModel updateTableEntry(DataRequest request)
    {
        TableDataEntity entity = validateTableEntry(request.getTableId(), request.getAccessId(), request.getHeaderId());
        entity.setData(request.getData());
        tableEntryRepository.updateTableEntry(entity.getTableId(), entity.getAccessId(), entity.getHeaderId(), entity.getData());
        return DataModelBuilder.buildModel(entity.getTableId(), entity.getAccessId(), entity.getHeaderId(), entity.getData());
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

    private TableDataEntity validateTableEntry(int tableId, int accessId, int headerId)
    {
        TableEntity tableEntity = validateTable(tableId);
        CategoryEntity accessEntity = validateCategory(tableEntity.getTableId(), accessId);
        CategoryEntity headerEntity = validateCategory(tableEntity.getTableId(), headerId);

        TableDataEntity entity = tableEntryRepository.findTableEntry(tableEntity.getTableId(), accessEntity.getCategoryId(), headerEntity.getCategoryId());

        if(entity == null)
            throw new ObjectNotFoundException("No Entry found for the combination; access id: " + accessId + ", header id: " + headerId + " in table id: " + tableId);

        return entity;
    }
}