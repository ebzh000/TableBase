package com.ez.tablebase.rest.common.html;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 8/05/2018.
 */

public class Cell
{
    private Integer categoryId;
    private String label;
    private int colSpan;
    private int rowSpan;

    public Cell(Integer categoryId, String label, int colSpan, int rowSpan)
    {
        this.categoryId = categoryId;
        this.label = label;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
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

    int getColSpan()
    {
        return colSpan;
    }

    int getRowSpan()
    {
        return rowSpan;
    }


    @Override
    public String toString()
    {
        return "Cell{" +
                "label='" + label + '\'' +
                ", colSpan=" + colSpan +
                ", rowSpan=" + rowSpan +
                '}';
    }
}
