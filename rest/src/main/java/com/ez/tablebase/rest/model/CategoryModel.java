package com.ez.tablebase.rest.model;

import org.springframework.hateoas.ResourceSupport;

public class CategoryModel extends ResourceSupport
{
    private int categoryId;
    private int tableId;
    private String attributeName;
    private Integer parentId;
    private DataType type;

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

    public DataType getType()
    {
        return type;
    }

    public void setType(DataType type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "CategoryModel{" +
                "categoryId=" + categoryId +
                ", tableId=" + tableId +
                ", attributeName='" + attributeName + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                '}';
    }
}
