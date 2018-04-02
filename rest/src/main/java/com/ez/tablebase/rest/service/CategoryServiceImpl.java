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
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.save(entity);
        return CategoryModelBuilder.buildModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryModel> getTableCategories(Integer tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<CategoryEntity> entities = categoryRepository.findAllTableCategories(tableEntity.getTableId());
        List<CategoryModel> models = new ArrayList<>();
        entities.forEach(entity -> models.add(CategoryModelBuilder.buildModel(entity)));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryModel getCategory(Integer tableId, Integer categoryId)
    {
        CategoryEntity entity = validateCategory(tableId, categoryId);
        return CategoryModelBuilder.buildModel(entity);
    }

    @Override
    public CategoryModel updateCategory(CategoryRequest request)
    {
        CategoryEntity entity = validateCategory(request.getTableId(), request.getCategoryId());
        entity.setAttributeName(request.getAttributeName());
        entity.setParentId(request.getParentId());
        entity.setType((byte) request.getType().ordinal());
        categoryRepository.updateTableCateogry(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), entity.getType());
        return CategoryModelBuilder.buildModel(entity);
    }

    @Override
    public void duplicateCategory(Integer tableId, Integer categoryId, Integer parentId)
    {
        CategoryEntity newCategory = new CategoryEntity();
        CategoryEntity category = validateCategory(tableId, categoryId);
        newCategory.setTableId(tableId);
        newCategory.setAttributeName(category.getAttributeName());
        newCategory.setParentId((parentId == null) ? category.getParentId() : parentId);
        newCategory.setType(category.getType());
        categoryRepository.save(newCategory);

        List<CategoryEntity> children = categoryRepository.findChildren(tableId, categoryId);
        System.out.println(Arrays.toString(children.toArray()));
        if(children.isEmpty())
        {
            System.out.println("No children");
            return;
        }


        for(CategoryEntity entity : children)
        {
            duplicateCategory(entity.getTableId(), entity.getCategoryId(), entity.getParentId());
        }
    }

    @Override
    public void deleteCategory(Integer tableId, Integer categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryRepository.delete(entity);
    }

    private TableEntity validateTable(Integer tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }

    private CategoryEntity validateCategory(Integer tableId, Integer categoryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        CategoryEntity entity = categoryRepository.findCategory(tableEntity.getTableId(), categoryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Category found for category id: " + categoryId + ", in table id: " + tableId);

        return entity;
    }
}
