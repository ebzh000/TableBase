package com.ez.tablebase.rest.model;
/*

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
