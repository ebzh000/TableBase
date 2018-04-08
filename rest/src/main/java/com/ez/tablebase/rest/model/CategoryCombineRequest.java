package com.ez.tablebase.rest.model;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 7/04/2018.
 */

public class CategoryCombineRequest
{
    private int tableId;
    private int categoryId1;
    private int categoryId2;
    private String newCategoryName;
    private byte dataOperationType;

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public int getCategoryId1()
    {
        return categoryId1;
    }

    public void setCategoryId1(int categoryId1)
    {
        this.categoryId1 = categoryId1;
    }

    public int getCategoryId2()
    {
        return categoryId2;
    }

    public void setCategoryId2(int categoryId2)
    {
        this.categoryId2 = categoryId2;
    }

    public String getNewCategoryName()
    {
        return newCategoryName;
    }

    public void setNewCategoryName(String newCategoryName)
    {
        this.newCategoryName = newCategoryName;
    }

    public byte getDataOperationType()
    {
        return dataOperationType;
    }

    public void setDataOperationType(byte dataOperationType)
    {
        this.dataOperationType = dataOperationType;
    }

    @Override
    public String toString()
    {
        return "CategoryCombineRequest{" +
                "tableId=" + tableId +
                ", categoryId1=" + categoryId1 +
                ", categoryId2=" + categoryId2 +
                ", newCategoryName='" + newCategoryName + '\'' +
                ", dataOperationType=" + dataOperationType +
                '}';
    }
}
