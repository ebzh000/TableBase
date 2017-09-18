package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 15/09/2017.
 */

public class TableModelBuilder
{
    public static TableModel buildModel(int id, int user, String name, String tags)
    {
        TableModel model = new TableModel();

        model.setTableId(id);
        model.setUserId(user);
        model.setTableName(name);
        model.setTags(tags);
        return model;
    }
}
