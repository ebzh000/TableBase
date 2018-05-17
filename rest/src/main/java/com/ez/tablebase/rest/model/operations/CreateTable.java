package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import com.ez.tablebase.rest.model.Table;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;

public class CreateTable extends Operation<Table>
{
    private CreateTableRequest request;
    private Table table;

    public CreateTable(CreateTableRequest request)
    {
        this.request = request;
    }

    @Override
    public Table exec()
    {
        Integer tableId = table.createTable(request.getTableName(), request.getTags(), request.getPublic());
        System.out.println(tableId);
        return null;
    }
}
