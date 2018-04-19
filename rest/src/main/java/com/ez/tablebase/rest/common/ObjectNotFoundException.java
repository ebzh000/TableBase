package com.ez.tablebase.rest.common;
/*

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