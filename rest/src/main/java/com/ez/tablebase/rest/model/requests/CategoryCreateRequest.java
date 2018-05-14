package com.ez.tablebase.rest.model.requests;

public class CategoryCreateRequest
{
    private int categoryId;
    private int tableId;
    private String attributeName;
    private String attributeName2;
    private Integer parentId;
    private boolean linkChildren;
    private byte entryType;

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

    public String getAttributeName2()
    {
        return attributeName2;
    }

    public void setAttributeName2(String attributeName2)
    {
        this.attributeName2 = attributeName2;
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

    public byte getEntryType()
    {
        return entryType;
    }

    public void setEntryType(byte entryType)
    {
        this.entryType = entryType;
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
                ", entryType=" + entryType +
                '}';
    }
}
