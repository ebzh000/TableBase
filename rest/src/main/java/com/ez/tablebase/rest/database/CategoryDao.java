package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "categories")
public class CategoryDao implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;

    @Column(name = "table_id", nullable = false)
    private Integer tableId;

    @Column(name = "tree_id", nullable = false)
    private Integer treeId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "parent_id")
    private Integer parentId;

    public CategoryDao () {}

    public Integer getTableId()
    {
        return tableId;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public void setTableId(Integer tableId)
    {
        this.tableId = tableId;
    }

    public Integer getTreeId()
    {
        return treeId;
    }

    public void setTreeId(Integer treeId)
    {
        this.treeId = treeId;
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

    @Override
    public String toString()
    {
        return "CategoryDao{" +
                "categoryId=" + categoryId +
                ", tableId=" + tableId +
                ", treeId=" + treeId +
                ", attributeName='" + attributeName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
