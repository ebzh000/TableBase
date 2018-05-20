package com.ez.tablebase.rest.model.dao;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.PathEntity;
import org.hibernate.Session;

public class PathDaoImpl implements PathDao
{

    @Override
    public void createPath(Integer tableId, Integer entryId, Integer categoryId, Integer treeId)
    {

    }

    @Override
    public PathEntity getPathByEntryId(Integer entryId)
    {
        return null;
    }

    @Override
    public PathEntity getPathByCategoryId(Integer categoryId)
    {
        return null;
    }

    @Override
    public PathEntity getPathByTreeId(Integer tableId, Integer treeId)
    {
        return null;
    }

    @Override
    public void deletePath(PathEntity path)
    {

    }

    public Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
