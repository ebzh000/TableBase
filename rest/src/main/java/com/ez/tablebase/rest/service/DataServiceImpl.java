package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 16-May-18.
 */

import com.ez.tablebase.rest.model.Entry;
import com.ez.tablebase.rest.model.requests.DataRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService
{
    @Override
    public List<Entry> getTableEntries(int tableId)
    {
        return null;
    }

    @Override
    public Entry getTableEntry(int tableId, int entryId)
    {
        return null;
    }

    @Override
    public Entry updateTableEntry(DataRequest request)
    {
        return null;
    }

    @Override
    public String toHtml(int tableId)
    {
        return null;
    }
}
