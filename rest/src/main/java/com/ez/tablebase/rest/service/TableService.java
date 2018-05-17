package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;

import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    // TableDaoImpl Operations
    TableDaoImpl createTable(CreateTableRequest request);

    TableDaoImpl getTable(int tableId);

    List<TableDaoImpl> getUserTables(int userId);

    List<TableDaoImpl> getTables();

    String toHtml(int tableId);

    List<TableDaoImpl> searchTable(String keyword);

    void deleteTable(int tableId);
}
