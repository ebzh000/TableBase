package com.ez.tablebase.rest.model;

/**
 * Created by Erik Zhong on 9/11/2017.
 */

public class TableRequest
{
    private String tableId;
    private String userId;

    public String getTableId()
    {
        return tableId;
    }

    public void setTableId(String tableId)
    {
        this.tableId = tableId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}
