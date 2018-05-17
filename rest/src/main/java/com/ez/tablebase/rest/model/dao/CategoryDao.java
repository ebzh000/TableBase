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

public interface CategoryDao
{
    Integer createCategory(Integer tableId, String name, Integer parentId, Integer treeId);

    CategoryEntity getCategory(Integer categoryId);

    CategoryEntity getRootCategoryByTreeId(Integer tableId, Integer treeId);

    List<CategoryEntity> findCategoryChildren(Integer categoryId);

    
}
