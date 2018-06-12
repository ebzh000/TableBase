package com.ez.tablebase.rest.model.html;
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
    private String cellId;
    private String label;
    private CellType type;
    private int colSpan;
    private int rowSpan;

    public Cell(String cellId, String label, CellType type, int colSpan, int rowSpan)
    {
        this.cellId = cellId;
        this.label = label;
        this.type = type;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
    }

    String getCellId()
    {
        return cellId;
    }

    public String getLabel()
    {
        return label;
    }

    public CellType getType()
    {
        return type;
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
