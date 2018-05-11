package com.ez.tablebase.rest.model.requests;

public class CategoryCreateRequest
{
    private int categoryId;
    private int tableId;
    private String attributeName;
    private Integer parentId;
    private boolean linkChildren;

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

    public boolean isLinkChildren()
    {
        return linkChildren;
    }

    public void setLinkChildren(boolean linkChildren)
    {
        this.linkChildren = linkChildren;
    }

    @Override
    public String toString()
    {
        return "CategoryCreateRequest{" +
                "categoryId=" + categoryId +
                ", tableId=" + tableId +
                ", attributeName='" + attributeName + '\'' +
                ", parentId=" + parentId +
                ", linkChildren=" + linkChildren +
                '}';
    }
}
