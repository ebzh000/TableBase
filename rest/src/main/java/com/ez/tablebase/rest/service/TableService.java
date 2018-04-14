package com.ez.tablebase.rest.service;

        import com.ez.tablebase.rest.model.*;
        import com.ez.tablebase.rest.model.requests.TableRequest;

        import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    // Table Operations
    Table createTable(TableRequest request);
    Table getTable(int tableId);
    Table getUserTables(int userId);
    List<Table> searchTable(String keyword);
    List<Table> getTables();
    void deleteTable(int tableId);
}
