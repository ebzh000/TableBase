package com.ez.tablebase.rest.controller;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.model.requests.*;
import com.ez.tablebase.rest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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

    @PostMapping(value = "/category/createTopLevelCategory")
    Object createTopLevelCategory(@PathVariable int tableId, @RequestBody CategoryCreateRequest request)
    {
        request.setTableId(tableId);
        return categoryService.createTopLevelCategory(request);
    }

    @PostMapping(value = "/category/create")
    Object createCategory(@PathVariable int tableId, @RequestBody CategoryCreateRequest request)
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
    Object updateCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategoryUpdateRequest request)
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

    @PostMapping(value = "/category/combine")
    Object combineCategory(@PathVariable int tableId, @RequestBody CategoryCombineRequest request) throws ParseException
    {
        request.setTableId(tableId);
        return categoryService.combineCategory(request);
    }

    @PostMapping(value = "/category/split/{categoryId}")
    void splitCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategorySplitRequest request) throws ParseException
    {
        request.setTableId(tableId);
        request.setCategoryId(categoryId);
        categoryService.splitCategory(request);
    }

    @DeleteMapping(value = "/category/{categoryId}/deleteTopLevelCategory")
    void deleteTopLevelCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategoryDeleteRequest request)
    {
        request.setTableId(tableId);
        request.setCategoryId(categoryId);
        categoryService.deleteTopLevelCategory(request);
    }

    @DeleteMapping(value = "/category/{categoryId}")
    void deleteCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestParam(value = "deleteChildren") boolean deleteChildren)
    {
        categoryService.deleteCategory(tableId, categoryId, deleteChildren);
    }
}
