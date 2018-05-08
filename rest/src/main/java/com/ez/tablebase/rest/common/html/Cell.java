package com.ez.tablebase.rest.common.html;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 8/05/2018.
 */

import java.util.List;

public class Cell
{
    public Integer categoryId;
    public String label;
    public int colSpan;
    public int rowSpan;
    public List<Integer> dataAccessPath;

    public Cell(Integer categoryId, String label, int colSpan, int rowSpan)
    {
        this.categoryId = categoryId;
        this.label = label;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        this.dataAccessPath = null;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public int getColSpan()
    {
        return colSpan;
    }

    public void setColSpan(int colSpan)
    {
        this.colSpan = colSpan;
    }

    public int getRowSpan()
    {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan)
    {
        this.rowSpan = rowSpan;
    }

    public List<Integer> getDataAccessPath()
    {
        return dataAccessPath;
    }

    public void setDataAccessPath(List<Integer> dataAccessPath)
    {
        this.dataAccessPath = dataAccessPath;
    }

    @Override
    public String toString()
    {
        return "Cell{" +
                "label='" + label + '\'' +
                ", colSpan=" + colSpan +
                ", rowSpan=" + rowSpan +
                ", dataAccessPath='" + dataAccessPath + '\'' +
                '}';
    }
}
