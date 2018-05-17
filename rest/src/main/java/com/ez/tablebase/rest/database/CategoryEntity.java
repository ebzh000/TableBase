package com.ez.tablebase.rest.database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Erik Zhong on 9/10/2017.
 */

@Entity
@Table(name = "categories")
public class CategoryEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;

    @Column(name = "table_id", nullable = false)
    private Integer tableId;

    @Column(name = "tree_id", nullable = false)
    private Integer treeId;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    public CategoryEntity() {}

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
        return "CategoryDaoImpl{" +
                "categoryId=" + categoryId +
                ", tableId=" + tableId +
                ", treeId=" + treeId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
