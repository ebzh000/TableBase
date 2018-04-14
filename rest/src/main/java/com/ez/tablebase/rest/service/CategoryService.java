package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.Category;
import com.ez.tablebase.rest.model.requests.CategoryCombineRequest;
import com.ez.tablebase.rest.model.requests.CategoryRequest;
import com.ez.tablebase.rest.model.requests.CategorySplitRequest;

import java.text.ParseException;
import java.util.List;

public interface CategoryService
{
    Category createCategory(CategoryRequest request);
    List<Category> getTableCategories(int tableId);
    Category getCategory(int tableId, int categoryId);
    Category updateCategory(CategoryRequest request);
    void duplicateCategory (int tableId, int categoryId);
    Category combineCategory (CategoryCombineRequest request) throws ParseException;
    void splitCategory (CategorySplitRequest request);
    void deleteCategory(int tableId, int categoryId);
}
