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
    private int treeId;
    private byte type;

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

    public int getTreeId()
    {
        return treeId;
    }

    public void setTreeId(int treeId)
    {
        this.treeId = treeId;
    }

    public byte getType()
    {
        return type;
    }

    public void setType(byte type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "DataAccessPath{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", entryId=" + entryId +
                ", categoryId=" + categoryId +
                ", treeId=" + treeId +
                ", type=" + type +
                "} " + super.toString();
    }
}
