package com.ez.tablebase.rest.model.requests;

/**
 * Created by Erik Zhong on 9/11/2017.
 */

public class TableRequest
{
    private int tableId;
    private int userId;
    private String tableName;
    private String tags;
    private boolean isPublic;
    private byte type;

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

    public boolean getPublic()
    {
        return isPublic;
    }

    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }

    public byte getType()
    {
        return type;
    }

    public void setType(byte type)
    {
        this.type = type;
    }
}
