package com.ez.tablebase.rest.model;

import com.ez.tablebase.rest.database.CategoryEntity;
import org.springframework.hateoas.ResourceSupport;

public class Category extends ResourceSupport
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

    public static Category buildModel(CategoryEntity entity)
    {
        Category model = new Category();

        model.setTableId(entity.getTableId());
        model.setCategoryId(entity.getCategoryId());
        model.setAttributeName(entity.getAttributeName());
        model.setParentId(entity.getParentId());
        model.setType(DataType.values()[entity.getType()]);
        return model;
    }

    @Override
    public String toString()
    {
        return "Category{" +
                "categoryId=" + categoryId +
                ", tableId=" + tableId +
                ", attributeName='" + attributeName + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                '}';
    }
}
