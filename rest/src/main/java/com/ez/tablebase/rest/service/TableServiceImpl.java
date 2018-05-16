package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 16-May-18.
 */

import com.ez.tablebase.rest.model.Table;
import com.ez.tablebase.rest.model.requests.TableRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService
{
    @Override
    public Table createTable(TableRequest request)
    {
        return null;
    }

    @Override
    public Table getTable(int tableId)
    {
        return null;
    }

    @Override
    public List<Table> getUserTables(int userId)
    {
        return null;
    }

    @Override
    public List<Table> getTables()
    {
        return null;
    }

    @Override
    public String toHtml(int tableId)
    {
        return null;
    }

    @Override
    public List<Table> searchTable(String keyword)
    {
        return null;
    }

    @Override
    public void deleteTable(int tableId)
    {

    }
}
