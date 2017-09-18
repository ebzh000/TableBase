package com.ez.tablebase.rest.database;

import javax.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int tableId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "tags")
    private String tags;

    public TableEntity()
    {

    }

    public TableEntity(Integer tableId, Integer userId, String tableName, String tags)
    {
        super();
        this.tableId = tableId;
        this.userId = userId;
        this.tableName = tableName;
        this.tags = tags;
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

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    @Override
    public String toString()
    {
        return "TableEntity{" +
                "tableId=" + tableId +
                ", userId=" + userId +
                ", tableName='" + tableName + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
