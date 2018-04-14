package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.DataAccessPath;
import com.ez.tablebase.rest.model.Entry;
import com.ez.tablebase.rest.model.requests.DataRequest;

import java.util.List;

public interface DataService
{
    Entry createTableEntry(DataRequest request);
    List<Entry> getTableEntries(int tableId);
    Entry getTableEntry(int tableId, int entryId);
    Entry updateTableEntry(DataRequest request);

    List<DataAccessPath> getDataAccessPath(int tableId, int entryId);
}
