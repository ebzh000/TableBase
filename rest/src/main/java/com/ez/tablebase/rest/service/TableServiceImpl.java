package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
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
    private final CategoryRepository categoryRepository;
    private final TableEntryRepository tableEntryRepository;
    private final DataAccessPathRepository dataAccessPathRepository;

    private static final String EMPTY_STRING = "";

    public TableServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository, TableEntryRepository tableEntryRepository, DataAccessPathRepository dataAccessPathRepository)
    {
        this.tableRepository = tableRepository;
        this.categoryRepository = categoryRepository;
        this.tableEntryRepository = tableEntryRepository;
        this.dataAccessPathRepository = dataAccessPathRepository;
    }

    @Override
    public Table createTable(TableRequest request)
    {
        TableEntity newTable = new TableEntity();
        newTable.setUserId(request.getUserId());
        newTable.setTableName(request.getTableName());
        newTable.setTags(request.getTags());
        newTable.setPublic(request.getPublic());
        newTable = tableRepository.save(newTable);

        // We need to set up a basic table
        initialiseBasicTable(newTable);

        return Table.buildModel(newTable);
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

    /* We need to create 2 parent categories that have 1 category each
     *
     * +-----------------------+--------------------+
     * | New Access Category   | New Category       |
     * +-----------------------+--------------------+
     * | New Access            | New Entry          |
     * +-----------------------+--------------------+
     *
     * Where new category is contained within a Virtual Header (VH)
     *
     * We also need to create a data access path and an entry.
     */
    private void initialiseBasicTable(TableEntity entity)
    {
        // Creating categories
        CategoryEntity virtualHeader = createCategory(entity.getTableId(), "VH", null, DataType.UNKNOWN);
        CategoryEntity accessHeader = createCategory(entity.getTableId(), "Access Category", null, DataType.UNKNOWN);
        CategoryEntity newAccess = createCategory(entity.getTableId(), "Access Category", accessHeader.getCategoryId(), DataType.UNKNOWN);
        CategoryEntity newCategory = createCategory(entity.getTableId(), "Category", virtualHeader.getCategoryId(), DataType.UNKNOWN);

        // Creating Entry
        EntryEntity newEntry = createEntry(entity.getTableId(), EMPTY_STRING);

        // Create Data Access Path for the new entry
        createDataAccessPath(entity.getTableId(), newEntry.getEntryId(), newAccess.getCategoryId());
        createDataAccessPath(entity.getTableId(), newEntry.getEntryId(), newCategory.getCategoryId());
    }

    private CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, DataType type)
    {
        CategoryEntity category = new CategoryEntity();
        category.setTableId(tableId);
        category.setAttributeName(attributeName);
        category.setParentId(parentId);
        category.setType((byte) type.ordinal());
        return categoryRepository.save(category);
    }

    private EntryEntity createEntry(Integer tableId, String data)
    {
        EntryEntity entry = new EntryEntity();
        entry.setTableId(tableId);
        entry.setData(data);
        return tableEntryRepository.save(entry);
    }

    private DataAccessPathEntity createDataAccessPath(Integer tableId, Integer entryId, Integer categoryId)
    {
        DataAccessPathEntity dap = new DataAccessPathEntity();
        dap.setTableId(tableId);
        dap.setEntryId(entryId);
        dap.setCategoryId(categoryId);
        return dataAccessPathRepository.save(dap);
    }

    private TableEntity validateTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }
}