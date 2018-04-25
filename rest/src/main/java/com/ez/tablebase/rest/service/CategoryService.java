package com.ez.tablebase.rest.service;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.Category;
import com.ez.tablebase.rest.model.requests.CategoryCombineRequest;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import com.ez.tablebase.rest.model.requests.CategorySplitRequest;
import com.ez.tablebase.rest.model.requests.CategoryUpdateRequest;

import java.text.ParseException;
import java.util.List;

public interface CategoryService
{
    Category createTopLevelCategory(CategoryCreateRequest request);

    Category createCategory(CategoryCreateRequest request);

    List<Category> getTableCategories(int tableId);

    Category getCategory(int tableId, int categoryId);

    Category updateCategory(CategoryUpdateRequest request);

    void duplicateCategory(int tableId, int categoryId);

    Category combineCategory(CategoryCombineRequest request) throws ParseException;

    void splitCategory(CategorySplitRequest request) throws ParseException;

    void deleteCategory(int tableId, int categoryId, boolean deleteChildren);
}
