package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 15/09/2017.
 */

import org.springframework.hateoas.ResourceSupport;

public class TableModel extends ResourceSupport
{
    private int tableId;
    private int userId;
    private String tableName;
    private String tags;
    private boolean isPublic;

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public boolean isPublic()
    {
        return isPublic;
    }

    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }

    @Override
    public String toString()
    {
        return "TableModel{" +
                "tableId=" + tableId +
                ", userId=" + userId +
                ", tableName='" + tableName + '\'' +
                ", tags='" + tags + '\'' +
                ", getPublic=" + isPublic +
                '}';
    }
}
