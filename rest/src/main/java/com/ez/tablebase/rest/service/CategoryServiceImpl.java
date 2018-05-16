package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.Category;
import com.ez.tablebase.rest.model.requests.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 16-May-18.
 */

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Override
    public Category createTopLevelCategory(CategoryCreateRequest request)
    {
        return null;
    }

    @Override
    public Category createCategory(CategoryCreateRequest request)
    {
        return null;
    }

    @Override
    public List<Category> getTableCategories(int tableId, boolean excludeRoot)
    {
        return null;
    }

    @Override
    public List<Category> getTableRootCategories(int tableId)
    {
        return null;
    }

    @Override
    public Category getCategory(int tableId, int categoryId)
    {
        return null;
    }

    @Override
    public Category updateCategory(CategoryUpdateRequest request)
    {
        return null;
    }

    @Override
    public Category duplicateCategory(int tableId, int categoryId)
    {
        return null;
    }

    @Override
    public Category combineCategory(CategoryCombineRequest request) throws ParseException
    {
        return null;
    }

    @Override
    public Category splitCategory(CategorySplitRequest request) throws ParseException
    {
        return null;
    }

    @Override
    public void deleteCategory(int tableId, int categoryId, boolean deleteChildren)
    {

    }

    @Override
    public void deleteTopLevelCategory(CategoryDeleteRequest request) throws ParseException
    {

    }

    @Override
    public String toHtml(int tableId)
    {
        return null;
    }
}
