package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;
import org.hibernate.Session;

public class CreateTable extends Operation<TableEntity>
{
    private CreateTableRequest request;
    private TableDaoImpl tableDaoImpl;
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public CreateTable(CreateTableRequest request)
    {
        this.request = request;
        this.tableDaoImpl = new TableDaoImpl();
    }

    @Override
    public TableEntity exec()
    {
        session.beginTransaction();
        TableEntity table = tableDaoImpl.createTable(request.getTableName(), request.getTags(), request.getPublic());

        session.flush();
        session.getTransaction().commit();
        return table;
    }
}
