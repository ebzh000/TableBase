package com.ez.tablebase.rest.model.dao;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 20-May-18.
 */

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.PathEntity;

import java.util.List;

public interface PathDao
{
    void createPath(Integer tableId, Integer entryId, Integer categoryId, Integer treeId);

    List<PathEntity> getPathByEntryId(Integer entryId);

    List<PathEntity> getPathsByEntryIdExcludingTreeId(Integer entryId, Integer treeId);

    List<PathEntity> getPathsByEntryIdAndTreeId(Integer entryId, Integer treeId);

    List<Integer> getPathEntryIdByCategoryId(Integer categoryId);

    List<List<PathEntity>> getPathByCategoryIdAndTreeId(CategoryEntity category, Integer treeId);

    List<PathEntity> getPathByTreeId(Integer tableId, Integer treeId);
}
