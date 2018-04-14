package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/09/2017.
 */

import org.springframework.hateoas.ResourceSupport;

public class Entry extends ResourceSupport
{
    private int tableId;
    private int entryId;
    private String data;

    public int getEntryId()
    {
        return entryId;
    }

    public void setEntryId(int entryId)
    {
        this.entryId = entryId;
    }

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public static Entry buildModel(int tableId, int entryId, String data)
    {
        Entry model = new Entry();

        model.setTableId(tableId);
        model.setEntryId(entryId);
        model.setData(data);
        return model;
    }

    @Override
    public String toString()
    {
        return "Entry{" +
                "tableId=" + tableId +
                ", entryId=" + entryId +
                ", data='" + data + '\'' +
                '}';
    }
}
