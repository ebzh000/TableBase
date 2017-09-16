package com.ez.tablebase.rest.service;

        import com.ez.tablebase.rest.database.CategoryEntity;
        import com.ez.tablebase.rest.model.DataType;
        import com.ez.tablebase.rest.model.TableModel;

        import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    // Table Operations
    TableModel createTable(int userId);
    TableModel getTable(int tableId);
    TableModel getUserTables(int userId);
    List<TableModel> getTables();
    void deleteTable(int id);

    CategoryEntity createCategory(int tableId, int categoryId, String attributeName, int parentId, DataType type);
    List<CategoryEntity> getTableCategories(int tableId);
    void deleteCategory(int tableId, int categoryId);
}
