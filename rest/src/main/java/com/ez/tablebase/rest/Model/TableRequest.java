package com.ez.tablebase.rest.model;

/**
 * Created by Erik Zhong on 9/11/2017.
 */

public class TableRequest
{
    private int tableId;
    private int userId;
    private String tableName;
    private String tags;

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
}
