package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import com.ez.tablebase.rest.model.dao.CategoryDaoImpl;
import com.ez.tablebase.rest.model.dao.EntryDaoImpl;
import com.ez.tablebase.rest.model.dao.PathDaoImpl;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;

public abstract class Operation<T>
{
    TableDaoImpl tableDaoImpl = new TableDaoImpl();
    CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
    PathDaoImpl pathDaoImpl = new PathDaoImpl();
    EntryDaoImpl entryDaoImpl = new EntryDaoImpl();

    public abstract T exec();
}
