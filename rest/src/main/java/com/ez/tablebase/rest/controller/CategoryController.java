package com.ez.tablebase.rest.controller;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.model.CategoryRequest;
import com.ez.tablebase.rest.service.CategoryService;
import com.ez.tablebase.rest.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tablebase/table/{tableId}")
public class CategoryController
{
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/categories/create")
    Object createCategories(@PathVariable int tableId, @RequestBody CategoryRequest request)
    {
        return null;
    }

    @PostMapping(value = "/category/create")
    Object createCategory(@PathVariable int tableId, @RequestBody CategoryRequest request)
    {
        request.setTableId(tableId);
        return categoryService.createCategory(request);
    }

    @GetMapping(value = "/categories")
    Object getCategories(@PathVariable int tableId)
    {
        return categoryService.getTableCategories(tableId);
    }

    @GetMapping(value = "/category/{categoryId}")
    Object getCategory(@PathVariable int tableId, @PathVariable int categoryId)
    {
        return categoryService.getCategory(tableId, categoryId);
    }

    @PostMapping(value = "/category/{categoryId}")
    Object updateCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategoryRequest request)
    {
        request.setTableId(tableId);
        request.setCategoryId(categoryId);
        return categoryService.updateCategory(request);
    }

    @PostMapping(value = "/category/duplicate/{categoryId}")
    void duplicateCategory(@PathVariable int tableId, @PathVariable int categoryId)
    {
        categoryService.duplicateCategory(tableId, categoryId);
    }

    @DeleteMapping(value = "/category/{categoryId}")
    void deleteCategory(@PathVariable int tableId, @PathVariable int categoryId)
    {
        categoryService.deleteCategory(tableId, categoryId);
    }
}
