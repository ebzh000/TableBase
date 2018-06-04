package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CreateTable extends Operation<TableEntity>
{
    private CreateTableRequest request;
    private TableDaoImpl tableDaoImpl = new TableDaoImpl();
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public CreateTable(CreateTableRequest request)
    {
        this.request = request;
    }

    @Override
    public TableEntity exec()
    {
        Transaction tx = session.beginTransaction();
        TableEntity table = tableDaoImpl.createTable(request.getTableName(), request.getTags(), request.getPublic());


        tx.commit();
        return table;
    }
}
