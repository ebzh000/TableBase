package com.ez.tablebase.rest.model;
/*

 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.database.TableDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Table extends TableDao
{
    @Autowired
    private SessionFactory sessionFactory;

    public Integer createTable(String name, String tags, boolean isPublic)
    {
        TableDao tableDao = new TableDao();
        tableDao.setTableName(name);
        tableDao.setTags(tags);
        tableDao.setPublic(isPublic);
        return (Integer) getCurrentSession().save(tableDao);
    }

    protected Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }
}
