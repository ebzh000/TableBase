package com.ez.tablebase.rest.common.html;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 8/05/2018.
 */

import java.util.LinkedList;
import java.util.List;

public class Table
{
    private static final String HTML_HEADER =
            "<head>\n\t" +
                "<title>TableBase</title>\n\t" +
                "<style>\n\t\t" +
                    "table, th, td { border: 1px solid black; border-collapse: collapse; }\n\t\t" +
                    "th, td { padding: 5px; text-align: center; }\n\t" +
                "</style>\n" +
            "</head>\n";
    private static final String COL_SPAN = "colspan=\"";
    private static final String ROW_SPAN = "rowspan=\"";
    private static final String TAB = "\t";
    private static final String NEW_LINE = "\n";

    public List<List<Cell>> table;

    public Table()
    {
        this.table = new LinkedList<>();
    }

    public List<List<Cell>> getTable()
    {
        return table;
    }

    public void addNewRow()
    {
        this.table.add(new LinkedList<>());
    }

    public Integer getRowCount()
    {
        return this.table.size() - 1;
    }

    public void addCell(Integer row, Cell cell)
    {
        this.table.get(row).add(cell);
    }

    public String toHtml()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>").append(NEW_LINE).append(HTML_HEADER);
        sb.append("<body>").append(NEW_LINE);
        sb.append("<table>").append(NEW_LINE).append(TAB);

        for(List<Cell> row : table){
            sb.append("<tr>").append(NEW_LINE);
            for(Cell cell : row)
            {
                sb.append(TAB).append(TAB).append("<td ")
                        .append(COL_SPAN).append(cell.colSpan).append("\" ")
                        .append(ROW_SPAN).append(cell.rowSpan).append("\">")
                        .append(cell.label).append("</td>")
                        .append(NEW_LINE);
            }
            sb.append(TAB).append("</tr>").append(NEW_LINE);
        }

        sb.append("</table>").append(NEW_LINE);
        sb.append("</body>").append(NEW_LINE);
        sb.append("</html>");
        return sb.toString();
    }
}
