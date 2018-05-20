package com.ez.tablebase.rest.model.dao;
/*

 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.TableEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TableDaoImpl implements TableDao
{
    public TableEntity createTable(String name, String tags, boolean isPublic)
    {
        Session session = getCurrentSession();
        TableEntity tableEntityDao = new TableEntity();
        tableEntityDao.setTableName(name);
        tableEntityDao.setTags(tags);
        tableEntityDao.setPublic(isPublic);
        Integer id = (Integer) session.save(tableEntityDao);
        tableEntityDao.setTableId(id);
        return tableEntityDao;
    }

    @Override
    public TableEntity getTable(Integer tableId)
    {
        return getCurrentSession().get(TableEntity.class, tableId);
    }

    @Override
    public void updateTable(Integer tableId, String name, String tags, boolean isPublic)
    {
        Session session = getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        TableEntity table = session.load(TableEntity.class, tableId);
        tx1.commit();

        table.setTableName(name);
        table.setTags(tags);
        table.setPublic(isPublic);
        Transaction tx2 = session.beginTransaction();
        session.update(table);
        tx2.commit();
    }

    @Override
    public void deleteTable(Integer tableId)
    {
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        TableEntity table = session.load(TableEntity.class, tableId);
        session.delete(table);
        tx.commit();
    }

    private Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
