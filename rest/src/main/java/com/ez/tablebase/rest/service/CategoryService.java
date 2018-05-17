package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.dao.CategoryDaoImpl;
import com.ez.tablebase.rest.model.requests.*;

import java.text.ParseException;
import java.util.List;

public interface CategoryService
{
    CategoryDaoImpl createTopLevelCategory(CategoryCreateRequest request);

    CategoryDaoImpl createCategory(CategoryCreateRequest request);

    List<CategoryDaoImpl> getTableCategories(int tableId, boolean excludeRoot);

    List<CategoryDaoImpl> getTableRootCategories(int tableId);

    CategoryDaoImpl getCategory(int tableId, int categoryId);

    CategoryDaoImpl updateCategory(CategoryUpdateRequest request);

    CategoryDaoImpl duplicateCategory(int tableId, int categoryId);

    CategoryDaoImpl combineCategory(CategoryCombineRequest request) throws ParseException;

    CategoryDaoImpl splitCategory(CategorySplitRequest request) throws ParseException;

    void deleteCategory(int tableId, int categoryId, boolean deleteChildren);

    void deleteTopLevelCategory(CategoryDeleteRequest request) throws ParseException;

    String toHtml(int tableId);
}
