package com.ez.tablebase.rest.model.dao;
/*

 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.database.TableEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TableDaoImpl implements TableDao
{
    @Autowired
    private SessionFactory sessionFactory;

    public Integer createTable(String name, String tags, boolean isPublic)
    {
        TableEntity tableEntityDao = new TableEntity();
        tableEntityDao.setTableName(name);
        tableEntityDao.setTags(tags);
        tableEntityDao.setPublic(isPublic);
        return (Integer) getCurrentSession().save(tableEntityDao);
    }

    @Override
    public TableEntity getTable(Integer tableId)
    {
        return null;
    }

    @Override
    public TableEntity updateTable(Integer tableId, String name, String tags, boolean isPublic)
    {
        return null;
    }

    @Override
    public void deleteTable(Integer tableId)
    {

    }

    protected Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }
}
