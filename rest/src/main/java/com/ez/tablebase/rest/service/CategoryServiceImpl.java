package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.CategoryModel;
import com.ez.tablebase.rest.model.CategoryModelBuilder;
import com.ez.tablebase.rest.model.CategoryRequest;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@javax.transaction.Transactional
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    private final TableRepository tableRepository;

    public CategoryServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public CategoryModel createCategory(CategoryRequest request)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(request.getTableId());
        entity.setCategoryId(request.getCategoryId());
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.save(entity);
        return CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()]);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryModel> getTableCategories(int tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<CategoryEntity> entities = categoryRepository.findAllTableCategories(tableEntity.getTableId());
        List<CategoryModel> models = new ArrayList<>();
        entities.forEach(entity -> models.add(CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()])));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryModel getCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = validateCategory(tableId, categoryId);
        return CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()]);
    }

    @Override
    public CategoryModel updateCategory(CategoryRequest request)
    {
        CategoryEntity entity = validateCategory(request.getTableId(), request.getCategoryId());
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.updateTableCateogry(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), entity.getType());
        return CategoryModelBuilder.buildModel(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), DataType.values()[entity.getType()]);
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryRepository.delete(entity);
    }

    private TableEntity validateTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }

    private CategoryEntity validateCategory(int tableId, int categoryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        CategoryEntity entity = categoryRepository.findCategory(tableEntity.getTableId(), categoryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Category found for category id: " + categoryId + ", in table id: " + tableId);

        return entity;
    }
}
