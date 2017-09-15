package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.TableModel;
import com.ez.tablebase.rest.model.TableModelBuilder;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.TableRequest;
import com.ez.tablebase.rest.model.TableResponse;
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

    public TableResponse createTable(TableRequest request)
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

    private TableEntity validateTable(String tableId)
    {
        if (tableId == null)
            throw new IllegalArgumentException("Country must be provided to get public holidays for");

        TableEntity entity = tableRepository.findOne(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }
}