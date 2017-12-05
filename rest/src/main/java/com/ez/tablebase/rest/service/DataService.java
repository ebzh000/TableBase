package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.DataAccessPathModel;
import com.ez.tablebase.rest.model.DataModel;
import com.ez.tablebase.rest.model.DataRequest;

import java.util.List;

public interface DataService
{
    DataModel createTableEntry(DataRequest request);
    List<DataModel> getTableEntries(int tableId);
    DataModel getTableEntry(int tableId, int entryId);
    DataModel updateTableEntry(DataRequest request);

    List<DataAccessPathModel> getDataAccessPath(int tableId, int entryId);
}
