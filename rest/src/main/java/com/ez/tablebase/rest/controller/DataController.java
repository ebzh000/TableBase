package com.ez.tablebase.rest.controller;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.model.DataModel;
import com.ez.tablebase.rest.model.DataRequest;
import com.ez.tablebase.rest.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tablebase/table/{tableId}")
public class DataController
{
    private TableService tableService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DataController(TableService tableService)
    {
        this.tableService = tableService;
    }

    @PostMapping(value = "/entry/create")
    public DataModel createTableEntry(@PathVariable int tableId, DataRequest request)
    {
        request.setTableId(tableId);
        return tableService.createTableEntry(request);
    }

    @GetMapping(value = "/entries")
    public List<DataModel> getTableEntries(@PathVariable int tableId)
    {
        return tableService.getTableEntries(tableId);
    }
}
