package com.ez.tablebase.rest.database;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 8/10/2017.
 */

import java.io.Serializable;

class DataAccessPathKey implements Serializable
{
    int tableId;
    int entryId;
}
