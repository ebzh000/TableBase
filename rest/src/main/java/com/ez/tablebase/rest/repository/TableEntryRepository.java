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
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TableEntryRepository extends PagingAndSortingRepository<TableDataEntity, String>
{
}
