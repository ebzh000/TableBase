package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.DataAccessPath;
import com.ez.tablebase.rest.model.Entry;
import com.ez.tablebase.rest.model.requests.DataRequest;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@javax.transaction.Transactional
public class DataServiceImpl implements DataService
{
    private final TableRepository tableRepository;
    private final TableEntryRepository tableEntryRepository;
    private final DataAccessPathRepository dataAccessPathRepository;

    public DataServiceImpl (TableRepository tableRepository, TableEntryRepository tableEntryRepository, DataAccessPathRepository dataAccessPathRepository)
    {
        this.tableRepository = tableRepository;
        this.tableEntryRepository = tableEntryRepository;
        this.dataAccessPathRepository = dataAccessPathRepository;
    }

    @Override
    public Entry createTableEntry(DataRequest request)
    {
        /*
         * TODO : Need to figure out how to pass this endpoint a list of categories.
         * This means how the GUI will grab all related categories and send them to the end point.
         */
        EntryEntity entity = new EntryEntity();
        entity.setTableId(request.getTableId());
        entity.setData(request.getData());
        tableEntryRepository.save(entity);

        saveDataAccessPath(request.getCategories(), entity);

        return Entry.buildModel(entity.getTableId(), entity.getEntryId(), entity.getData());
    }

    private void saveDataAccessPath(List<Integer> categories, EntryEntity entity)
    {
        for(Integer category : categories)
        {
            DataAccessPathEntity accessPathEntity = new DataAccessPathEntity();
            accessPathEntity.setTableId(entity.getTableId());
            accessPathEntity.setEntryId(entity.getEntryId());
            accessPathEntity.setCategoryId(category);
            dataAccessPathRepository.save(accessPathEntity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getTableEntries(int tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<EntryEntity> entities = tableEntryRepository.findAllTableEntries(tableEntity.getTableId());
        List<Entry> models = new ArrayList<>();
        entities.forEach(row -> models.add(Entry.buildModel(row.getTableId(), row.getEntryId(), row.getData())));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public Entry getTableEntry(int tableId, int entryId)
    {
        EntryEntity entity = validateTableEntry(tableId, entryId);
        return Entry.buildModel(entity.getTableId(),entity.getEntryId(),entity.getData());
    }

    @Override
    public Entry updateTableEntry(DataRequest request)
    {
        EntryEntity entity = validateTableEntry(request.getTableId(), request.getEntryId());
        entity.setData(request.getData());
        tableEntryRepository.updateTableEntry(entity.getTableId(), entity.getEntryId(), entity.getData());
        return Entry.buildModel(entity.getTableId(), entity.getEntryId(), entity.getData());
    }

    @Override
    public List<DataAccessPath> getDataAccessPath(int tableId, int entryId)
    {
        return null;
    }

    private TableEntity validateTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }

    private EntryEntity validateTableEntry(int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<DataAccessPathEntity> accessPathEntity = validateAccessPath(tableEntity.getTableId(), entryId);
        EntryEntity entity = tableEntryRepository.findTableEntry(tableEntity.getTableId(), accessPathEntity.get(0).getEntryId());

        if(entity == null)
            throw new ObjectNotFoundException("No Entry found for the combination; entry id: " + entryId + " in table id: " + tableId);

        return entity;
    }

    private List<DataAccessPathEntity> validateAccessPath(int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<DataAccessPathEntity> entities = dataAccessPathRepository.getEntryAccessPath(tableEntity.getTableId(), entryId);

        if(entities == null)
            throw new ObjectNotFoundException("No data access path found for the entry id: " + entryId + " in table id: " + tableId);

        return entities;
    }
}
