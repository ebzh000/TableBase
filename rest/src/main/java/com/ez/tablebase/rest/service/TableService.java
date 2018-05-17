package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.Table;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;

import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    // Table Operations
    Table createTable(CreateTableRequest request);

    Table getTable(int tableId);

    List<Table> getUserTables(int userId);

    List<Table> getTables();

    String toHtml(int tableId);

    List<Table> searchTable(String keyword);

    void deleteTable(int tableId);
}
