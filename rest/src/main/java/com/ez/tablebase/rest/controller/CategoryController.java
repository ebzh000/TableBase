package com.ez.tablebase.rest.controller;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.model.dao.CategoryDaoImpl;
import com.ez.tablebase.rest.model.requests.*;
import com.ez.tablebase.rest.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "/tablebase/table/{tableId}")
public class CategoryController
{
    private CategoryService categoryService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/category/createTopLevelCategory")
    Object createTopLevelCategory(@PathVariable int tableId, @RequestBody CategoryCreateRequest request, @RequestParam(value = "toHtml") boolean toHtml)
    {
        request.setTableId(tableId);
        request.setLinkChildren(false);
        CategoryDaoImpl categoryDaoImpl = categoryService.createTopLevelCategory(request);
        if(toHtml)
           return categoryService.toHtml(tableId);
        else
            return categoryDaoImpl;
    }

    @PostMapping(value = "/category/create")
    Object createCategory(@PathVariable int tableId, @RequestBody CategoryCreateRequest request, @RequestParam(value = "toHtml") boolean toHtml)
    {
        request.setTableId(tableId);
        CategoryDaoImpl categoryDaoImpl = categoryService.createCategory(request);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return categoryDaoImpl;
    }

    @GetMapping(value = "/categories")
    Object getCategories(@PathVariable int tableId, @RequestParam("excludeRoot") boolean excludeRoot)
    {
        List<CategoryDaoImpl> categories = categoryService.getTableCategories(tableId, excludeRoot);
        logger.info("Returning "+ categories.size() + " categories");
        return categories;
    }

    @GetMapping(value = "/rootCategories")
    Object getRootCategories(@PathVariable int tableId)
    {
        List<CategoryDaoImpl> categories = categoryService.getTableRootCategories(tableId);
        logger.info("Returning "+ categories.size() + " categories");
        return categories;
    }

    @GetMapping(value = "/category/{categoryId}")
    Object getCategory(@PathVariable int tableId, @PathVariable int categoryId)
    {
        return categoryService.getCategory(tableId, categoryId);
    }

    @PostMapping(value = "/category/{categoryId}")
    Object updateCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategoryUpdateRequest request, @RequestParam(value = "toHtml") boolean toHtml)
    {
        request.setTableId(tableId);
        request.setCategoryId(categoryId);
        CategoryDaoImpl categoryDaoImpl = categoryService.updateCategory(request);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return categoryDaoImpl;
    }

    @PostMapping(value = "/category/duplicate/{categoryId}")
    Object duplicateCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestParam(value = "toHtml") boolean toHtml)
    {
        CategoryDaoImpl categoryDaoImpl = categoryService.duplicateCategory(tableId, categoryId);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return categoryDaoImpl;
    }

    @PostMapping(value = "/category/combine")
    Object combineCategory(@PathVariable int tableId, @RequestBody CategoryCombineRequest request, @RequestParam(value = "toHtml") boolean toHtml) throws ParseException
    {
        request.setTableId(tableId);
        CategoryDaoImpl categoryDaoImpl = categoryService.combineCategory(request);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return categoryDaoImpl;
    }

    @PostMapping(value = "/category/split/{categoryId}")
    Object splitCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategorySplitRequest request, @RequestParam(value = "toHtml") boolean toHtml) throws ParseException
    {
        request.setTableId(tableId);
        request.setCategoryId(categoryId);
        CategoryDaoImpl categoryDaoImpl = categoryService.splitCategory(request);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return categoryDaoImpl;
    }

    @DeleteMapping(value = "/category/{categoryId}/deleteTopLevelCategory")
    Object deleteTopLevelCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestBody CategoryDeleteRequest request, @RequestParam(value = "toHtml") boolean toHtml) throws ParseException
    {
        request.setTableId(tableId);
        request.setCategoryId(categoryId);
        categoryService.deleteTopLevelCategory(request);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return HttpStatus.OK;
    }

    @DeleteMapping(value = "/category/{categoryId}")
    Object deleteCategory(@PathVariable int tableId, @PathVariable int categoryId, @RequestParam(value = "deleteChildren") boolean deleteChildren, @RequestParam(value = "toHtml") boolean toHtml)
    {
        categoryService.deleteCategory(tableId, categoryId, deleteChildren);
        if(toHtml)
            return categoryService.toHtml(tableId);
        else
            return HttpStatus.OK;
    }
}
