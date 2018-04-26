package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.Entry;
import com.ez.tablebase.rest.model.requests.DataRequest;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import com.ez.tablebase.rest.service.utils.DataAccessPathUtils;
import com.ez.tablebase.rest.service.utils.TableEntryUtils;
import com.ez.tablebase.rest.service.utils.TableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@javax.transaction.Transactional
public class DataServiceImpl implements DataService
{
    private TableUtils tableUtils;
    private DataAccessPathUtils dapUtils;
    private TableEntryUtils entryUtils;

    public DataServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository, TableEntryRepository tableEntryRepository, DataAccessPathRepository dataAccessPathRepository)
    {
        this.tableUtils = new TableUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.dapUtils = new DataAccessPathUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
        this.entryUtils = new TableEntryUtils(categoryRepository, tableRepository, dataAccessPathRepository, tableEntryRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getTableEntries(int tableId)
    {
        TableEntity tableEntity = tableUtils.validateTable(tableId);
        List<EntryEntity> entities = entryUtils.findAllTableEntries(tableEntity.getTableId());
        List<Entry> models = new ArrayList<>();
        entities.forEach(row -> models.add(Entry.buildModel(row.getTableId(), row.getEntryId(), row.getData())));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public Entry getTableEntry(int tableId, int entryId)
    {
        EntryEntity entity = entryUtils.validateEntry(tableId, entryId);
        return Entry.buildModel(entity.getTableId(), entity.getEntryId(), entity.getData());
    }

    @Override
    public Entry updateTableEntry(DataRequest request)
    {
        EntryEntity entity = entryUtils.validateEntry(request.getTableId(), request.getEntryId());
        entity.setData(request.getData());
        entryUtils.updateTableEntry(entity.getTableId(), entity.getEntryId(), entity.getData());
        return Entry.buildModel(entity.getTableId(), entity.getEntryId(), entity.getData());
    }
}
