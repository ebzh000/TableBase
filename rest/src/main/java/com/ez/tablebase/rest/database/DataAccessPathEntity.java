package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 8/10/2017.
 */

@Entity
@Table(name = "data_access_path")
public class DataAccessPathEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id", insertable = false, updatable = false)
    private int entryId;

    @Column(name = "table_id", nullable = false)
    private int tableId;

    @Column(name = "category_id")
    private int categoryId;

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
        return "DataAccessPathEntity{" +
                "tableId=" + tableId +
                ", entryId=" + entryId +
                ", categoryId=" + categoryId +
                '}';
    }
}
