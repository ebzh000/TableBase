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
import com.ez.tablebase.rest.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tablebase/table/{tableId}")
public class CategoryController
{
    private TableService tableService;

    @Autowired
    public CategoryController(TableService tableService)
    {
        this.tableService = tableService;
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
        return tableService.createCategory(request);
    }

    @GetMapping(value = "/categories")
    Object getCategories(@PathVariable int tableId)
    {
        return tableService.getTableCategories(tableId);
    }

    @GetMapping(value = "/category/{categoryId}")
    Object getCategory(@PathVariable int tableId, @PathVariable int categoryId)
    {
        return tableService.getCategory(tableId, categoryId);
    }

    @DeleteMapping(value = "/category/{categoryId}")
    void deleteCategory(@PathVariable int tableId, @PathVariable int categoryId)
    {
        tableService.deleteCategory(tableId, categoryId);
    }
}
