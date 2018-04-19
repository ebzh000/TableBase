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

    @Override
    public String toString()
    {
        return "DataAccessPathEntity{" +
                "id=" + id +
                ", entryId=" + entryId +
                ", tableId=" + tableId +
                ", categoryId=" + categoryId +
                '}';
    }
}
