package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

public abstract class Operation<T> extends BaseOperations
{
    public abstract T exec();
}
