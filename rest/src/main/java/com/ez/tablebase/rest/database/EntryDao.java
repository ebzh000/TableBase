package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "table_data")
public class EntryDao implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id", insertable = false, updatable = false)
    private int entryId;

    @Column(name = "table_id", nullable = false)
    private int tableId;

    @Column(name = "data")
    private String data;

    @Column(name = "type")
    private byte type;

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
        return "EntryDao{" +
                "entryId=" + entryId +
                ", tableId=" + tableId +
                ", data='" + data + '\'' +
                ", type=" + type +
                '}';
    }
}
