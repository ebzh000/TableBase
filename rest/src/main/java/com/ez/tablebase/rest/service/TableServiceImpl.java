package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 16-May-18.
 */

import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.TableDao;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.operations.CreateTable;
import com.ez.tablebase.rest.model.operations.Operation;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class TableServiceImpl implements TableService
{
    private TableDao tableDao = new TableDaoImpl();

    @Override
    public TableEntity createTable(CreateTableRequest request) throws ParseException
    {
        Operation operation = new CreateTable(request);
        return (TableEntity) operation.exec();
    }

    @Override
    public TableEntity getTable(int tableId)
    {
        return tableDao.getTable(tableId);
    }

    @Override
    public List<TableEntity> getUserTables(int userId)
    {
        return null;
    }

    @Override
    public List<TableEntity> getTables()
    {
        return null;
    }

    @Override
    public String toHtml(int tableId)
    {
        return null;
    }

    @Override
    public List<TableEntity> searchTable(String keyword)
    {
        return null;
    }

    @Override
    public void deleteTable(int tableId)
    {

    }
}
