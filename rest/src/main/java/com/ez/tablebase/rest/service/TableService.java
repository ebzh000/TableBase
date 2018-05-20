package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;

import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    // TableDaoImpl Operations
    TableEntity createTable(CreateTableRequest request);

    TableEntity getTable(int tableId);

    List<TableEntity> getUserTables(int userId);

    List<TableEntity> getTables();

    String toHtml(int tableId);

    List<TableEntity> searchTable(String keyword);

    void deleteTable(int tableId);
}
