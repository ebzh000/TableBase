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
    List<CategoryModel> getTableCategories(Integer tableId);
    CategoryModel getCategory(Integer tableId, Integer categoryId);
    CategoryModel updateCategory(CategoryRequest request);
    void duplicateCategory (Integer tableId, Integer categoryId, Integer parentId);
    void deleteCategory(Integer tableId, Integer categoryId);
}
