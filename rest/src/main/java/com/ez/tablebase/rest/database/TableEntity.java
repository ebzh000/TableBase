package com.ez.tablebase.rest.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "tablelist")
public class TableEntity implements Serializable
{
    @Id
    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "user_id")
    private Integer userId;

    public TableEntity()
    {

    }

    public TableEntity(Integer tableId, Integer userId)
    {
        super();
        this.tableId = tableId;
        this.userId = userId;
    }

    public Integer getTableId()
    {
        return tableId;
    }

    public void setTableId(Integer tableId)
    {
        this.tableId = tableId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "TableEntity{" +
                "tableId=" + tableId +
                ", userId=" + userId +
                '}';
    }
}
