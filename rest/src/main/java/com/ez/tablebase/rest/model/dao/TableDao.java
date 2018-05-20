package com.ez.tablebase.rest.model.dao;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 17-May-18.
 */

import com.ez.tablebase.rest.database.TableEntity;

public interface TableDao
{
    TableEntity createTable(String name, String tags, boolean isPublic);

    TableEntity getTable(Integer tableId);

    void updateTable(Integer tableId, String name, String tags, boolean isPublic);

    void deleteTable(Integer tableId);
}
