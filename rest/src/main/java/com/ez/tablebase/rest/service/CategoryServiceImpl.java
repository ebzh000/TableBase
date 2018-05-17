package com.ez.tablebase.rest.service;

import com.ez.tablebase.rest.model.dao.CategoryDaoImpl;
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
    public CategoryDaoImpl createTopLevelCategory(CategoryCreateRequest request)
    {
        return null;
    }

    @Override
    public CategoryDaoImpl createCategory(CategoryCreateRequest request)
    {
        return null;
    }

    @Override
    public List<CategoryDaoImpl> getTableCategories(int tableId, boolean excludeRoot)
    {
        return null;
    }

    @Override
    public List<CategoryDaoImpl> getTableRootCategories(int tableId)
    {
        return null;
    }

    @Override
    public CategoryDaoImpl getCategory(int tableId, int categoryId)
    {
        return null;
    }

    @Override
    public CategoryDaoImpl updateCategory(CategoryUpdateRequest request)
    {
        return null;
    }

    @Override
    public CategoryDaoImpl duplicateCategory(int tableId, int categoryId)
    {
        return null;
    }

    @Override
    public CategoryDaoImpl combineCategory(CategoryCombineRequest request) throws ParseException
    {
        return null;
    }

    @Override
    public CategoryDaoImpl splitCategory(CategorySplitRequest request) throws ParseException
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
