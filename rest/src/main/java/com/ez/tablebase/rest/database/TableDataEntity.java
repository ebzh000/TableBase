package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "table_data")
@IdClass(TableDataKey.class)
public class TableDataEntity implements Serializable
{
    @Id
    @Column(name = "table_id")
    private int tableId;

    @Id
    @Column(name = "entry_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int entryId;

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
        return "TableDataEntity{" +
                "tableId=" + tableId +
                ", entryId=" + entryId +
                ", data='" + data + '\'' +
                '}';
    }
}
