package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import java.text.ParseException;

public abstract class Operation<T>
{
    public abstract T exec() throws ParseException;
}
