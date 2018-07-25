package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 17/05/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.TableDao;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import com.ez.tablebase.rest.model.requests.CreateTableRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CreateTable extends Operation<TableEntity>
{
    private CreateTableRequest request;
    private TableDao tableDao = new TableDaoImpl();
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private static final String ATTRIBUTE_NAME = "New Category";

    public CreateTable(CreateTableRequest request)
    {
        this.request = request;
    }

    @Override
    public TableEntity exec()
    {
        Transaction tx = session.beginTransaction();
        TableEntity table = tableDao.createTable(request.getTableName(), request.getTags(), request.getPublic());

        // Create a category tree (dimension) for the new table
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(table.getTableId(), ATTRIBUTE_NAME, ATTRIBUTE_NAME, request.getType());
        new CreateTopLevelCategory(categoryCreateRequest).exec();

        tx.commit();
        return table;
    }
}
