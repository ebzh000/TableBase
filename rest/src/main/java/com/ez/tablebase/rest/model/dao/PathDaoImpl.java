package com.ez.tablebase.rest.model.dao;
/*

 * Created by ErikZ on 27/11/2017.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.PathEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PathDaoImpl implements PathDao
{
    @Override
    public void createPath(Integer tableId, Integer entryId, Integer categoryId, Integer treeId)
    {
        PathEntity path = new PathEntity();
        path.setTableId(tableId);
        path.setEntryId(entryId);
        path.setCategoryId(categoryId);
        path.setTreeId(treeId);
        getCurrentSession().save(path);
    }

    @Override
    public List<PathEntity> getPathByEntryId(Integer entryId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create Query
        CriteriaQuery<PathEntity> query = builder.createQuery(PathEntity.class);

        Root<PathEntity> root = query.from(PathEntity.class);
        query.select(root).where(builder.equal(root.get("entry_id"), entryId));
        query.orderBy(builder.asc(root.get("entry_id")), builder.asc(root.get("tree_id")), builder.asc(root.get("category_id")));

        List<PathEntity> paths = session.createQuery(query).getResultList();
        transaction.commit();

        return paths;
    }

    @Override
    public List<PathEntity> getPathsByEntryIdExcludingTreeId(Integer entryId, Integer treeId)
    {
        return null;
    }

    @Override
    public List<PathEntity> getPathByCategoryId(Integer categoryId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create Query
        CriteriaQuery<PathEntity> query = builder.createQuery(PathEntity.class);

        Root<PathEntity> root = query.from(PathEntity.class);
        query.select(root).where(builder.equal(root.get("category_id"), categoryId));
        query.groupBy(root.get("entry_id"));

        List<PathEntity> paths = session.createQuery(query).getResultList();

        // TODO: Need to do a join here to grab full paths. Currently its just a single path entity per entry
        transaction.commit();

        return paths;
    }

    @Override
    public List<PathEntity> getPathByTreeId(Integer tableId, Integer treeId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create Query
        CriteriaQuery<PathEntity> query = builder.createQuery(PathEntity.class);

        Root<PathEntity> root = query.from(PathEntity.class);
        query.select(root).where();
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
