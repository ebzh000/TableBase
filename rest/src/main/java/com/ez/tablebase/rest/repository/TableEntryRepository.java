package com.ez.tablebase.rest.repository;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.database.TableDataEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TableEntryRepository extends PagingAndSortingRepository<TableDataEntity, String>
{
    @Query(value = "SELECT * FROM tabledata WHERE table_id = :tableId ORDER BY table_id, access_id, header_id ASC", nativeQuery = true)
    List<TableDataEntity> findAllTableEntries(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM tabledata WHERE table_id = :tableId AND access_id = :accessId AND header_id = :headerId", nativeQuery = true)
    TableDataEntity findTableEntry(@Param("tableId") int tableId, @Param("accessId")  int accessId, @Param("headerId") int headerId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tabledata SET data = :entry WHERE table_id = :tableId AND access_id = :accessId AND header_id = :headerId", nativeQuery = true)
    void updateTableEntry(@Param("tableId") int tableId, @Param("accessId") int accessId, @Param("headerId") int headerId, @Param("entry") String data);
}
