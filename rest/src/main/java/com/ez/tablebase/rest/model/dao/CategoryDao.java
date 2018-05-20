package com.ez.tablebase.rest.model.dao;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 17-May-18.
 */

import com.ez.tablebase.rest.database.CategoryEntity;

import java.util.List;
import java.util.Map;

public interface CategoryDao
{
    CategoryEntity createCategory(Integer tableId, String name, Integer parentId, Integer treeId);

    CategoryEntity getCategory(Integer categoryId);

    CategoryEntity getRootCategoryByTreeId(Integer tableId, Integer treeId);

    List<Integer> getTreeIds(Integer tableId);

    List<CategoryEntity> findPathToCategory(Integer tableId, Integer categoryId);

    List<CategoryEntity> findCategoryChildren(Integer categoryId);

    List<CategoryEntity> findAllCategoryChildren(Integer categoryId);

    void updateCategoryParent(Integer categoryId, Integer parentId);

    void updateCategoryLabel(Integer categoryId, String name);

    List<List<CategoryEntity>> buildPathListForTree(Integer tableId, Integer treeId);

    Map<Integer, List<CategoryEntity>> buildPathMapForTree(Integer tableId, Integer treeId);

    List<List<CategoryEntity>> findPathCombinations(Integer tableId, List<Integer> treeIds);

    CategoryEntity duplicateCategory(CategoryEntity selectedCategory);

    void deleteCategory(CategoryEntity category);

    void deleteCategoryList(List<CategoryEntity> categories);
}
