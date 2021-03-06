package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "table_list")
public class TableEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id", insertable = false, updatable = false)
    private int tableId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "tags")
    private String tags;

    @Column(name = "public")
    private boolean isPublic;

    public TableEntity()
    {

    }

    public TableEntity(Integer tableId, Integer userId, String tableName, String tags, boolean isPublic)
    {
        super();
        this.tableId = tableId;
        this.userId = userId;
        this.tableName = tableName;
        this.tags = tags;
        this.isPublic = isPublic;
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

    public boolean isPublic()
    {
        return isPublic;
    }

    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }

    @Override
    public String toString()
    {
        return "TableEntity{" +
                "tableId=" + tableId +
                ", userId=" + userId +
                ", tableName='" + tableName + '\'' +
                ", tags='" + tags + '\'' +
                ", getPublic=" + isPublic +
                '}';
    }
}
