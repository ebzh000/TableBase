package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import org.springframework.hateoas.ResourceSupport;

public class DataAccessPath extends ResourceSupport
{
    private int id;
    private int tableId;
    private int entryId;
    private int categoryId;

    public int getPathId()
    {
        return id;
    }

    public void setPathId(int id)
    {
        this.id = id;
    }

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public int getEntryId()
    {
        return entryId;
    }

    public void setEntryId(int entryId)
    {
        this.entryId = entryId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    @Override
    public String toString()
    {
        return "DataAccessPath{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", entryId=" + entryId +
                ", categoryId=" + categoryId +
                '}';
    }
}