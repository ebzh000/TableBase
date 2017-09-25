package com.ez.tablebase.rest.service;

        import com.ez.tablebase.rest.database.CategoryEntity;
        import com.ez.tablebase.rest.model.*;
        import com.ez.tablebase.rest.repository.CategoryRepository;

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
    List<TableModel> searchTable(String keyword);
    List<TableModel> getTables();
    void deleteTable(int tableId);

    CategoryModel createCategory(CategoryRequest request);
    List<CategoryModel> getTableCategories(int tableId);
    CategoryModel getCategory(int tableId, int categoryId);
    CategoryModel updateCategory(CategoryRequest request);
    void deleteCategory(int tableId, int categoryId);

    DataModel createTableEntry(DataRequest request);
    List<DataModel> getTableEntries(int tableId);
    DataModel getTableEntry(int tableId, int accessId, int headerId);
    DataModel updateTableEntry(DataRequest request);
}
