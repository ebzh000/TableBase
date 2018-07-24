package com.ez.tablebase.rest.model.dao;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by Erik on 20-May-18.
 */

import com.ez.tablebase.rest.database.EntryEntity;

import java.util.List;

public interface EntryDao
{
    EntryEntity createEntry(Integer tableId, String label, byte type);

    List<EntryEntity> getEntryByTableId(Integer tableId);

    List<EntryEntity> getEntriesForCategoryId(Integer categoryId);

    EntryEntity getEntry(Integer entryId);

    void deleteEntry(EntryEntity entry);

    void deleteEntryList(List<EntryEntity> entryList);
}
