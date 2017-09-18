package com.ez.tablebase.rest.database;

import com.ez.tablebase.rest.model.DataType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "categories")
@IdClass(CategoryKey.class)
public class CategoryEntity implements Serializable
{

    @Id
    @Column(name = "category_id")
    private int categoryId;

    @Id
    @Column(name = "table_id")
    private int tableId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "type")
    private byte type;

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getTableId()
    {
        return tableId;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public String getAttributeName()
    {
        return attributeName;
    }

    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
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
        return "CategoryEntity{" +
                "categoryId=" + categoryId +
                ", tableId=" + tableId +
                ", attributeName='" + attributeName + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                '}';
    }
}
