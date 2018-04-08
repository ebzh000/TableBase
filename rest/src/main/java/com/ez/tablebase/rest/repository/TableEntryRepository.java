package com.ez.tablebase.rest.repository;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.database.EntryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TableEntryRepository extends PagingAndSortingRepository<EntryEntity, String>
{
    @Query(value = "SELECT * FROM table_data WHERE table_id = :tableId ORDER BY table_id, entry_id ASC", nativeQuery = true)
    List<EntryEntity> findAllTableEntries(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM table_data WHERE table_id = :tableId AND entry_id = :entryId", nativeQuery = true)
    EntryEntity findTableEntry(@Param("tableId") int tableId, @Param("entryId")  int entryId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE table_data SET data = :entry WHERE table_id = :tableId AND entry_id = :entryId", nativeQuery = true)
    void updateTableEntry(@Param("tableId") int tableId, @Param("entryId") int entryId, @Param("entry") String data);
}
