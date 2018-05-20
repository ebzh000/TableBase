package com.ez.tablebase.rest.model.dao;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 20-May-18.
 */

import com.ez.tablebase.rest.database.PathEntity;

public interface PathDao
{
    void createPath(Integer tableId, Integer entryId, Integer categoryId, Integer treeId);

    PathEntity getPathByEntryId(Integer entryId);

    PathEntity getPathByCategoryId(Integer categoryId);

    PathEntity getPathByTreeId(Integer tableId, Integer treeId);

    void deletePath(PathEntity path);
}
