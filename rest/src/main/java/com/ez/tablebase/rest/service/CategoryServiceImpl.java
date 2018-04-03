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
    public List<CategoryModel> getTableCategories(int tableId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<CategoryEntity> entities = categoryRepository.findAllTableCategories(tableEntity.getTableId());
        List<CategoryModel> models = new ArrayList<>();
        entities.forEach(entity -> models.add(CategoryModelBuilder.buildModel(entity)));
        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryModel getCategory(int tableId, int categoryId)
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
    public void duplicateCategory(int tableId, int categoryId)
    {
        CategoryEntity category = validateCategory(tableId, categoryId);

        // Need to prompt user with a confirmation if the selected category is the root node of the whole tree (categories are in a tree structure)
        duplicateCategories(category, category.getParentId());

        //TODO: Duplicate entries: 1. Get all Data Access Paths that include the root's category_id 2. Get all entries from the list of dataAccessPaths and then duplicate all entries (seems like recusion is not needed here)
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryRepository.delete(entity);
    }

    private void duplicateCategories(CategoryEntity entity, Integer newParentId)
    {
        CategoryEntity newEntity = new CategoryEntity();
        newEntity.setTableId(entity.getTableId());
        newEntity.setAttributeName(entity.getAttributeName());
        newEntity.setParentId(newParentId);
        newEntity.setType(entity.getType());
        categoryRepository.save(newEntity);
        System.out.println("Duplicated Category: " + entity.getCategoryId() + " with parent: " + entity.getParentId() + "; New Category: " + newEntity.getCategoryId() + " with parent: " + newEntity.getParentId());

        List<CategoryEntity> children = categoryRepository.findChildren(entity.getTableId(), entity.getCategoryId());
        if(!children.isEmpty() && children.get(0) != null)
        {
            System.out.println("Children found: " + children.size());
            for(CategoryEntity child : children)
            {
                System.out.println("Duplicating Child: " + child.getCategoryId() + " , parent: " + (child.getParentId() == null ? "null" : child.getParentId()));
                duplicateCategories(child, newEntity.getCategoryId());
            }
        }
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
