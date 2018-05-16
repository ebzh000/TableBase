package com.ez.tablebase.rest.model;
/*

 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.database.TableDao;

public class Table
{
    private int tableId;
    private int userId;
    private String tableName;
    private String tags;
    private boolean isPublic;

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
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

    public static Table buildModel(TableDao entity)
    {
        Table model = new Table();
        model.setTableId(entity.getTableId());
        model.setUserId(entity.getUserId());
        model.setTableName(entity.getTableName());
        model.setTags(entity.getTags());
        model.setPublic(entity.isPublic());
        return model;
    }

    @Override
    public String toString()
    {
        return "Table{" +
                "tableId=" + tableId +
                ", userId=" + userId +
                ", tableName='" + tableName + '\'' +
                ", tags='" + tags + '\'' +
                ", getPublic=" + isPublic +
                '}';
    }
}
