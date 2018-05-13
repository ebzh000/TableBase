package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.Category;
import com.ez.tablebase.rest.model.requests.*;

import java.text.ParseException;
import java.util.List;

public interface CategoryService
{
    Category createTopLevelCategory(CategoryCreateRequest request);

    Category createCategory(CategoryCreateRequest request);

    List<Category> getTableCategories(int tableId, boolean excludeRoot);

    List<Category> getTableRootCategories(int tableId);

    Category getCategory(int tableId, int categoryId);

    Category updateCategory(CategoryUpdateRequest request);

    Category duplicateCategory(int tableId, int categoryId);

    Category combineCategory(CategoryCombineRequest request) throws ParseException;

    Category splitCategory(CategorySplitRequest request) throws ParseException;

    void deleteCategory(int tableId, int categoryId, boolean deleteChildren);

    void deleteTopLevelCategory(CategoryDeleteRequest request);

    String toHtml(int tableId);
}
