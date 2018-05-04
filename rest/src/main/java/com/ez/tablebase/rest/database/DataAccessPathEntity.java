package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/*

 * Created by ErikZ on 8/10/2017.
 */

@Entity
@Table(name = "data_access_path")
public class DataAccessPathEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private int id;

    @Column(name = "entry_id")
    private int entryId;

    @Column(name = "table_id", nullable = false)
    private int tableId;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "tree_id")
    private int treeId;

    @Column(name = "type")
    private byte type;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getEntryId()
    {
        return entryId;
    }

    public void setEntryId(int entryId)
    {
        this.entryId = entryId;
    }

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
        return "DataAccessPathEntity{" +
                "id=" + id +
                ", entryId=" + entryId +
                ", tableId=" + tableId +
                ", categoryId=" + categoryId +
                ", treeId=" + treeId +
                ", type=" + type +
                '}';
    }
}
