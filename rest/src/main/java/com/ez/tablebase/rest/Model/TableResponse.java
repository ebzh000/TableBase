package com.ez.tablebase.rest.Model;

/**
 * Created by Erik Zhong on 9/6/2017.
 */

public class TableResponse<E>
{
    private TableRequest request;
    private E entity;

    public TableResponse(TableRequest request, E entity)
    {
        this.request = request;
        this.entity = entity;
    }

    public TableRequest getRequest()
    {
        return this.request;
    }

    public E getEntity()
    {
        return this.entity;
    }
}
