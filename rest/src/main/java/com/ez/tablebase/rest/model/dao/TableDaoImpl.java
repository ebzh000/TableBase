package com.ez.tablebase.rest.model.dao;
/*

 * Created by erikz on 15/09/2017.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.TableEntity;
import org.hibernate.Session;

public class TableDaoImpl implements TableDao
{
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

    private Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
