package com.ez.tablebase.rest.service;

        import com.ez.tablebase.rest.database.CategoryEntity;
        import com.ez.tablebase.rest.model.CategoryModel;
        import com.ez.tablebase.rest.model.DataType;
        import com.ez.tablebase.rest.model.TableModel;
        import com.ez.tablebase.rest.model.TableRequest;

        import java.util.List;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public interface TableService
{
    // Table Operations
    TableModel createTable(TableRequest request);
    TableModel getTable(int tableId);
    TableModel getUserTables(int userId);
    List<TableModel> getTables();
    void deleteTable(int tableId);

    CategoryEntity createCategory(int tableId, int categoryId, String attributeName, int parentId, DataType type);
    List<CategoryModel> getTableCategories(int tableId);
    void deleteCategory(int tableId, int categoryId);
}
