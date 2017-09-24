package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 24/09/2017.
 */

public class DataRequest
{
    private int accessId;
    private int headerId;
    private int tableId;
    private String data;

    public int getAccessId()
    {
        return accessId;
    }

    public void setAccessId(int accessId)
    {
        this.accessId = accessId;
    }

    public int getHeaderId()
    {
        return headerId;
    }

    public void setHeaderId(int headerId)
    {
        this.headerId = headerId;
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

    @Override
    public String toString()
    {
        return "DataRequest{" +
                "accessId=" + accessId +
                ", headerId=" + headerId +
                ", tableId=" + tableId +
                ", data='" + data + '\'' +
                '}';
    }
}
