package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/09/2017.
 */

public class DataModelBuilder
{
    public static DataModel buildModel(int tableId, int entryId, String data)
    {
        DataModel model = new DataModel();

        model.setTableId(tableId);
        model.setEntryId(entryId);
        model.setData(data);
        return model;
    }
}
