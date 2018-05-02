package com.ez.tablebase.rest.model.requests;
/*
 * Created by ErikZ on 30/04/2018.
 */

public class CategoryDeleteRequest
{
    private int tableId;
    private int categoryId;
    private byte dataOperationType;

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
        return "CategoryDeleteRequest{" +
                "tableId=" + tableId +
                ", categoryId=" + categoryId +
                ", dataOperationType=" + dataOperationType +
                '}';
    }
}
