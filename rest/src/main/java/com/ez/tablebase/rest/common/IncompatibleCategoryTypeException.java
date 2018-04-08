package com.ez.tablebase.rest.common;
/*
 * Copyright (C) 2018 Symbio Networks Pty Ltd. All Rights Reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Symbio Networks.
 * The copyright notice above does not evidence any actual or intended
 * publication of such source code.
 * 
 * Created by ErikZ on 7/04/2018.
 */

public class IncompatibleCategoryTypeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public IncompatibleCategoryTypeException(String msg)
    {
        super(msg);
    }
}
