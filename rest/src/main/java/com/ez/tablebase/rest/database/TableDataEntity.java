package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "tabledata")
@IdClass(TableDataKey.class)
public class TableDataEntity implements Serializable
{
    @Id
    @Column(name = "access_id")
    private int accessId;

    @Id
    @Column(name = "header_id")
    private int headerId;

    @Id
    @Column(name = "table_id")
    private int tableId;

    @Column(name = "data")
    private String data;

    public Integer getAccessId()
    {
        return accessId;
    }

    public void setAccessId(Integer accessId)
    {
        this.accessId = accessId;
    }

    public Integer getHeaderId()
    {
        return headerId;
    }

    public void setHeaderId(Integer headerId)
    {
        this.headerId = headerId;
    }

    public Integer getTableId()
    {
        return tableId;
    }

    public void setTableId(Integer tableId)
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
        return "TableDataEntity{" +
                "accessId=" + accessId +
                ", headerId=" + headerId +
                ", tableId=" + tableId +
                ", data='" + data + '\'' +
                '}';
    }
}
