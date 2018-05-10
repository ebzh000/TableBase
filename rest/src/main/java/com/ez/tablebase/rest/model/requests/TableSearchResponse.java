package com.ez.tablebase.rest.model.requests;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 10/05/2018.
 */

import com.ez.tablebase.rest.model.Table;

import java.util.List;

public class TableSearchResponse
{
    private List<Table> tableList;

    public TableSearchResponse(List<Table> tableList)
    {
        this.tableList = tableList;
    }

    public List<Table> getTableList()
    {
        return tableList;
    }
}
