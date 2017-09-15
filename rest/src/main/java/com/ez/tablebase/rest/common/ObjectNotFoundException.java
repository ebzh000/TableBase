package com.ez.tablebase.rest.common;
/*
 * Copyright (C) 2017 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by erikz on 15/09/2017.
 */

public class ObjectNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String msg)
    {
        super(msg);
    }
}