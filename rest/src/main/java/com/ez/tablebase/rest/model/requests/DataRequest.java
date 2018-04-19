package com.ez.tablebase.rest.model.requests;
/*

 * Created by ErikZ on 24/09/2017.
 */

import java.util.List;

public class DataRequest
{
    private int entryId;
    private List<Integer> categories;
    private int tableId;
    private String data;

    public int getEntryId()
    {
        return entryId;
    }

    public void setEntryId(int entryId)
    {
        this.entryId = entryId;
    }

    public List<Integer> getCategories()
    {
        return categories;
    }

    public void setCategories(List<Integer> categories)
    {
        this.categories = categories;
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
                "entryId=" + entryId +
                ", tableId=" + tableId +
                ", data='" + data + '\'' +
                '}';
    }
}
