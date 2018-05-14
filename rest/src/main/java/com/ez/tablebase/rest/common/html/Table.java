package com.ez.tablebase.rest.common.html;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 8/05/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;

import java.util.*;

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
    private List<List<Cell>> table;

    // A Map populated with Entry<ColIndex, colDAP>
    private Map<Integer, List<Integer>> colDAPs;

    // A Map populated with Entry<rowIndex, rowDAP>
    private Map<Integer, List<Integer>> rowDAPs;

    private Integer headerGroupDepth;
    private Integer headerGroupWidth;
    private Integer rowCount;
    private Integer accessTreeDepth;

    public Table(Integer tableId)
    {
        this.tableId = tableId;
        this.table = new LinkedList<>();
        this.colDAPs = new HashMap<>();
        this.rowDAPs = new HashMap<>();
    }

    public List<List<Cell>> getTable()
    {
        return table;
    }

    public Integer getHeaderGroupDepth()
    {
        return headerGroupDepth;
    }

    public void setHeaderGroupDepth(Integer headerGroupDepth)
    {
        this.headerGroupDepth = headerGroupDepth;
    }

    public Integer getHeaderGroupWidth()
    {
        return headerGroupWidth;
    }

    public void setHeaderGroupWidth(Integer headerGroupWidth)
    {
        this.headerGroupWidth = headerGroupWidth;
    }

    public Integer getRowCount()
    {
        return rowCount;
    }

    public void setRowCount(Integer rowCount)
    {
        this.rowCount = rowCount;
    }

    public Integer getAccessTreeDepth()
    {
        return accessTreeDepth;
    }

    public void setAccessTreeDepth(Integer accessTreeDepth)
    {
        this.accessTreeDepth = accessTreeDepth;
    }

    public void addNewRow()
    {
        table.add(new LinkedList<>());
    }

    public Integer getLatestRowIndex()
    {
        return table.size() - 1;
    }

    public void addCell(Integer row, Cell cell)
    {
        table.get(row).add(cell);
    }

    public Map<Integer, List<Integer>> getColDAPs()
    {
        return colDAPs;
    }

    public void saveColDAP(Integer colOffset, List<Integer> colDap)
    {
        colDAPs.put(colOffset, colDap);
    }

    public Map<Integer, List<Integer>> getRowDAPs()
    {
        return rowDAPs;
    }

    public void saveRowDAPS(List<List<CategoryEntity>> allRowDAPs)
    {
        for(int index = 0; index < allRowDAPs.size(); index++)
        {
            List<Integer> rowDAP = new LinkedList<>();
            allRowDAPs.get(index).forEach(category -> rowDAP.add(category.getCategoryId()));

            rowDAPs.put(index, rowDAP);
        }
    }

    public void printTable()
    {
        for (List<Cell> row : this.table)
        {
            for (int count = 0; count < row.size(); count++)
            {
                Cell cell = row.get(count);
                System.out.print(count + "|" + ((cell != null)? cell.getLabel() : null));
                if (count != row.size() - 1)
                    System.out.print(" - ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // This function is used to send a html string to the front end.
    // Copy code and uncomment if you want to export this same html string to a document
    public String toHtml()
    {
        StringBuilder sb = new StringBuilder();
//        sb.append("<html>").append(NEW_LINE);
//        sb.append("<head>").append(NEW_LINE).append(TAB);
//        sb.append("<title>TableBase - ").append(this.tableName).append("</title>").append(NEW_LINE).append(TAB);
//        sb.append(STYLE).append(NEW_LINE);
//        sb.append("<body>").append(NEW_LINE);
        sb.append("<table class=\"tablebase-table\" name=\"TableId:").append(this.tableId).append("\" align=\"center\">");

        for(int rowIndex = 0; rowIndex < table.size(); rowIndex++){
            List<Cell> row = table.get(rowIndex);
            String tdOrTh;
            String classTdOrTh;
            if(rowIndex < headerGroupDepth)
            {
                tdOrTh = "th";
                classTdOrTh = "tablebase-th";
            }
            else
            {
                tdOrTh = "td";
                classTdOrTh = "tablebase-td";
            }

            if(!row.isEmpty())
            {
                sb.append(NEW_LINE).append(TAB).append("<tr>").append(NEW_LINE);
                for (Cell cell : row)
                {
                    if (cell != null)
                    {
                        String boldStyle = "";
                        if(cell.getType().equals(CellType.ACCESS_CATEGORY))
                            boldStyle = "access-bold";

                        String entryId = "";
                        if(cell.getType().equals(CellType.DATA))
                            entryId = "id: " + cell.getCellId() + " | ";

                        sb.append(TAB).append(TAB).append("<").append(tdOrTh).append(" class=\"").append(classTdOrTh).append(" ").append(boldStyle).append("\" ")
                                .append("id=\"").append(cell.getCellId()).append("\" ")
                                .append(COL_SPAN).append(cell.getColSpan()).append("\" ")
                                .append(ROW_SPAN).append(cell.getRowSpan()).append("\">")
                                .append(entryId).append(cell.getLabel()).append("</").append(tdOrTh).append(">")
                                .append(NEW_LINE);
                    }
                }
                sb.append(TAB).append("</tr>");
            }
        }

        sb.append(NEW_LINE).append("</table>").append(NEW_LINE);

//        sb.append("</body>").append(NEW_LINE);
//        sb.append("</html>");
        return sb.toString();
    }
}
