package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;

public class CreateTable extends Operation<TableDaoImpl>
{
    private CreateTableRequest request;
    private TableDaoImpl tableDaoImpl;

    public CreateTable(CreateTableRequest request)
    {
        this.request = request;
    }

    @Override
    public TableDaoImpl exec()
    {
        Integer tableId = tableDaoImpl.createTable(request.getTableName(), request.getTags(), request.getPublic());
        System.out.println(tableId);
        return null;
    }
}
