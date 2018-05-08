package com.ez.tablebase.rest.common.html;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 8/05/2018.
 */

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Table
{
    private static final String STYLE =
            "<style>\n\t\t" +
                "table, th, td { border: 1px solid black; border-collapse: collapse; }\n\t\t" +
                "th, td { padding: 5px; text-align: center; }\n\t" +
            "</style>";

    private static final String COL_SPAN = "colspan=\"";
    private static final String ROW_SPAN = "rowspan=\"";
    private static final String TAB = "\t";
    private static final String NEW_LINE = "\n";


    private Integer tableId;
    private String tableName;
    private List<List<Cell>> table;

    // A list that contains Pairs of the row index and the row cell offset
    private List<Pair<Integer, Integer>> leafCells;
    private Integer headerGroupDepth;

    public Table(Integer tableId, String tableName)
    {
        this.tableId = tableId;
        this.tableName = tableName;
        this.table = new LinkedList<>();
        this.leafCells = new LinkedList<>();
    }

    public List<List<Cell>> getTable()
    {
        return this.table;
    }

    public Integer getHeaderGroupDepth()
    {
        return headerGroupDepth;
    }

    public void setHeaderGroupDepth(Integer headerGroupDepth)
    {
        this.headerGroupDepth = headerGroupDepth;
    }

    public void addNewRow()
    {
        this.table.add(new LinkedList<>());
    }

    public Integer getLatestRowIndex()
    {
        return this.table.size() - 1;
    }

    public void addCell(Integer row, Cell cell)
    {
        this.table.get(row).add(cell);
    }

    public List<Pair<Integer, Integer>> getLeafCells()
    {
        return this.leafCells;
    }

    public void saveLeafCell(Integer row, Integer rowCellOffset)
    {
        this.leafCells.add(new Pair<>(row, rowCellOffset));
    }

    public String toHtml()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>").append(NEW_LINE);
        sb.append("<head>").append(NEW_LINE).append(TAB);
        sb.append("<title>TableBase - ").append(this.tableName).append("</title>").append(NEW_LINE).append(TAB);
        sb.append(STYLE).append(NEW_LINE);
        sb.append("<body>").append(NEW_LINE);
        sb.append("<table name=\"TableId:").append(this.tableId).append("\" align=\"center\">");

        for(List<Cell> row : table){
            sb.append(NEW_LINE).append(TAB).append("<tr>").append(NEW_LINE);
            for(Cell cell : row)
            {
                if(cell != null)
                {
                    sb.append(TAB).append(TAB).append("<td ")
                            .append("name=\"CategoryId:").append(cell.getCategoryId()).append("\" ")
                            .append(COL_SPAN).append(cell.getColSpan()).append("\" ")
                            .append(ROW_SPAN).append(cell.getRowSpan()).append("\">")
                            .append(cell.getLabel()).append("</td>")
                            .append(NEW_LINE);
                }
            }
            sb.append(TAB).append("</tr>");
        }

        sb.append(NEW_LINE).append("</table>").append(NEW_LINE);
        sb.append("</body>").append(NEW_LINE);
        sb.append("</html>");
        return sb.toString();
    }
}
