package com.ez.tablebase.rest.repository;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.database.TableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TableRepository extends PagingAndSortingRepository<TableEntity, String>
{

    @Query(value = "SELECT * FROM tablelist WHERE table_id = :tableId", nativeQuery = true)
    TableEntity findTable(@Param("tableId") int tableId);

    @Query(value = "SELECT * FROM tablelist t WHERE t.table_name LIKE %:keyword% OR t.tags LIKE %:keyword%", nativeQuery = true)
    List<TableEntity> searchTable(@Param("keyword") String keyword);
}
