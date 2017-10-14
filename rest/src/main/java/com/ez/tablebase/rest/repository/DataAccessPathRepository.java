package com.ez.tablebase.rest.repository;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 8/10/2017.
 */

import com.ez.tablebase.rest.database.DataAccessPathEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataAccessPathRepository extends PagingAndSortingRepository<DataAccessPathEntity, String>
{
    @Query(value = "SELECT * FROM data_access_path WHERE table_id = :tableId AND entry_id = :entryId", nativeQuery = true)
    List<DataAccessPathEntity> getEntryAccessPath(@Param("tableId") int tableId, @Param("entry_id") int entry_id);
}
