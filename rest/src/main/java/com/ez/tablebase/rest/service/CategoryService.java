package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.model.CategoryModel;
import com.ez.tablebase.rest.model.CategoryRequest;

import java.util.List;

public interface CategoryService
{
    CategoryModel createCategory(CategoryRequest request);
    List<CategoryModel> getTableCategories(int tableId);
    CategoryModel getCategory(int tableId, int categoryId);
    CategoryModel updateCategory(CategoryRequest request);
    void duplicateCategory (int tableId, int categoryId);
    void deleteCategory(int tableId, int categoryId);
}
