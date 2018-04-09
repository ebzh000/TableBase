package com.ez.tablebase.rest.service;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 *
 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.common.IncompatibleCategoryTypeException;
import com.ez.tablebase.rest.common.InvalidOperationException;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.common.OperationUtils;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.*;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@javax.transaction.Transactional
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;
    private final TableRepository tableRepository;
    private final DataAccessPathRepository dataAccessPathRepository;
    private final TableEntryRepository tableEntryRepository;

    public CategoryServiceImpl(TableRepository tableRepository, CategoryRepository categoryRepository,
                               DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        this.categoryRepository = categoryRepository;
        this.tableRepository = tableRepository;
        this.dataAccessPathRepository = dataAccessPathRepository;
        this.tableEntryRepository = tableEntryRepository;
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
        entity = categoryRepository.findCategory(entity.getTableId(), entity.getCategoryId());
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
        categoryRepository.updateTableCategory(entity.getTableId(), entity.getCategoryId(), entity.getAttributeName(), entity.getParentId(), entity.getType());
        return CategoryModelBuilder.buildModel(entity);
    }

    @Override
    public void duplicateCategory(int tableId, int categoryId)
    {
        CategoryEntity category = validateCategory(tableId, categoryId);

        if(category.getParentId() == null)
            throw new InvalidOperationException("Invalid operation! Resultant table will no longer be an abstract table");

        // Need to prompt user with a confirmation if the selected category is the root node of the whole tree (categories are in a tree structure)
        duplicateCategories(category, category.getParentId(), category.getCategoryId());
    }

    @Override
    public CategoryModel combineCategory(CategoryCombineRequest request) throws ParseException
    {
        CategoryEntity category1 = validateCategory(request.getTableId(), request.getCategoryId1());
        CategoryEntity category2 = validateCategory(request.getTableId(), request.getCategoryId2());

        if(category1.getType() != category2.getType())
            throw new IncompatibleCategoryTypeException("Specified categories have different types");

        categoryRepository.updateTableCategory(category1.getTableId(), category1.getCategoryId(), request.getNewCategoryName(), category1.getParentId(), category1.getType());
        combineEntries(category1, category2, OperationType.values()[request.getDataOperationType()]);

        categoryRepository.delete(category2);
        category1 = categoryRepository.findCategory(category1.getTableId(), category1.getCategoryId());
        return CategoryModelBuilder.buildModel(category1);
    }

    @Override
    public void deleteCategory(int tableId, int categoryId)
    {
        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(tableId);
        entity.setCategoryId(categoryId);
        categoryRepository.delete(entity);
    }

    private void duplicateCategories(CategoryEntity entity, Integer newParentId, Integer rootCategoryId)
    {
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setTableId(entity.getTableId());
        newCategory.setAttributeName(entity.getAttributeName());
        newCategory.setParentId(newParentId);
        newCategory.setType(entity.getType());
        categoryRepository.save(newCategory);

        // Get the all children that are being duplicated so we can then duplicate the dataAccessPaths correctly.
        List<Integer> affectedCategories = categoryRepository.getAllChildren(entity.getTableId(), rootCategoryId);

        List<CategoryEntity> children = categoryRepository.findChildren(entity.getTableId(), entity.getCategoryId());
        if(!children.isEmpty())
        {
            // If the first child is not null then we must recursively duplicate the children of this node
            if(children.get(0) != null)
                for (CategoryEntity child : children)
                    duplicateCategories(child, newCategory.getCategoryId(),rootCategoryId);

            // If the first child is null then we must now duplicate all entries for each DataAccessPaths (DAP) that ends with this child's category_id
            // 1. Get all DAPs that end with this category
            // 2. Loop through
            //    a. Get entry for DAP
            //    b. Clone the entry and then the new DAP
            else
            {
                List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(entity.getTableId(), entity.getCategoryId());
                if(!entries.isEmpty())
                {
                    for (Integer entry : entries)
                    {
                        EntryEntity newEntry = duplicateEntry(entity.getTableId(), entry);

                        // Need to get full path of the current entry
                        List<DataAccessPathEntity> origPath = dataAccessPathRepository.getEntryAccessPath(entity.getTableId(), entry);
                        for(DataAccessPathEntity pathEntity : origPath)
                        {
                            // Duplicate DataAccessPath
                            DataAccessPathEntity newPath = new DataAccessPathEntity();
                            newPath.setEntryId(newEntry.getEntryId());
                            newPath.setTableId(newEntry.getTableId());
                            newPath.setCategoryId((affectedCategories.contains(pathEntity.getCategoryId())) ? newCategory.getCategoryId() : pathEntity.getCategoryId());
                            dataAccessPathRepository.save(newPath);
                        }

                    }
                }
            }
        }
    }

    private EntryEntity duplicateEntry(Integer tableId, Integer entryId)
    {
        EntryEntity entity = validateEntry(tableId, entryId);
        EntryEntity newEntity = new EntryEntity();
        newEntity.setTableId(entity.getTableId());
        newEntity.setData(entity.getData());
        return tableEntryRepository.save(newEntity);
    }

    private void combineEntries(CategoryEntity category1, CategoryEntity category2, OperationType operationType) throws ParseException
    {
        List<Integer> category1Entries = dataAccessPathRepository.getEntriesForCategory(category1.getTableId(), category1.getCategoryId());
        List<Integer> category2Entries = dataAccessPathRepository.getEntriesForCategory(category2.getTableId(), category2.getCategoryId());

        for(int index = 0; index < category2Entries.size(); index++)
        {
            EntryEntity entry1 = tableEntryRepository.findTableEntry(category1.getTableId(), category1Entries.get(index));
            EntryEntity entry2 = tableEntryRepository.findTableEntry(category2.getTableId(), category2Entries.get(index));

            String data1 = entry1.getData();
            String data2 = entry2.getData();

            System.out.println("Data1: " + data1 + ", Data2: " + data2);

            switch (operationType)
            {
                case MAX:
                    data1 = OperationUtils.max(data1, data2, category1.getType());
                    break;
                case MIN:
                    data1 = OperationUtils.min(data1, data2, category1.getType());
                    break;
                case MEAN:
                    data1 = OperationUtils.mean(data1, data2, category1.getType());
                    break;
                case SUM:
                    data1 = OperationUtils.sum(data1, data2, category1.getType());
                    break;
                case DIFFERENCE:
                    data1 = OperationUtils.difference(data1, data2, category1.getType());
                    break;
                case CONCATENATE_STRING:
                    data1 = OperationUtils.concatenateString(data1, data2, category1.getType());
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    data1 = data2;
                    break;
                case NO_OPERATION:
                    break;
            }

            System.out.println("Result: " + data1);
            tableEntryRepository.updateTableEntry(entry1.getTableId(), entry1.getEntryId(), data1);
            tableEntryRepository.delete(entry2);
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

    private EntryEntity validateEntry(int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        EntryEntity entity = tableEntryRepository.findTableEntry(tableEntity.getTableId(), entryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Entry found for entry id: " + entryId + ", in table id: " + tableId);

        return entity;
    }
}
