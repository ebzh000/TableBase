package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "table_data")
public class EntryEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id", insertable = false, updatable = false)
    private int entryId;

    @Column(name = "table_id", nullable = false)
    private int tableId;

    @Column(name = "data")
    private String data;

    public Integer getTableId()
    {
        return tableId;
    }

    public void setTableId(Integer tableId)
    {
        this.tableId = tableId;
    }

    public Integer getEntryId()
    {
        return entryId;
    }

    public void setEntryId(Integer entryId)
    {
        this.entryId = entryId;
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
        return "EntryEntity{" +
                "tableId=" + tableId +
                ", entryId=" + entryId +
                ", data='" + data + '\'' +
                '}';
    }
}
