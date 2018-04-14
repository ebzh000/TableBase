package com.ez.tablebase.rest.model.requests;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 14-Apr-18.
 */

public class CategorySplitRequest
{
    private int tableId;
    private int categoryId;
    private String newCategoryName;
    private String threshold;

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getNewCategoryName()
    {
        return newCategoryName;
    }

    public void setNewCategoryName(String newCategoryName)
    {
        this.newCategoryName = newCategoryName;
    }

    public String getThreshold()
    {
        return threshold;
    }

    public void setThreshold(String threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return "CategorySplitRequest{" +
                "tableId=" + tableId +
                ", categoryId=" + categoryId +
                ", newCategoryName='" + newCategoryName + '\'' +
                ", threshold='" + threshold + '\'' +
                '}';
    }
}