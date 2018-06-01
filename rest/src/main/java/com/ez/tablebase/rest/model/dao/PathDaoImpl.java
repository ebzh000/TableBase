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
import java.util.ArrayList;
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
    public List<Integer> getPathEntryByCategoryId(Integer categoryId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create Query
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);

        Root<PathEntity> root = query.from(PathEntity.class);
        query.select(root.get("entry_id")).where(builder.equal(root.get("category_id"), categoryId));
        query.groupBy(root.get("entry_id"));

        List<Integer> entries = session.createQuery(query).getResultList();

        transaction.commit();

        return entries;
    }

    @Override
    public List<List<PathEntity>> getPathByCategoryIdAndTreeId(Integer categoryId, Integer treeId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<List<PathEntity>> pathByEntryId = new ArrayList<>();
        List<Integer> entries = getPathEntryByCategoryId(categoryId);

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create Query
        CriteriaQuery<PathEntity> query = builder.createQuery(PathEntity.class);
        Root<PathEntity> root = query.from(PathEntity.class);

        for (Integer entry : entries)
        {
            query.select(root).where(builder.equal(root.get("entry_id"), entry), builder.equal(root.get("tree_id"), treeId));
            query.groupBy(root.get("entry_id"));

            pathByEntryId.add(session.createQuery(query).getResultList());
        }

        transaction.commit();

        return pathByEntryId;
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
        query.select(root).where(builder.equal(root.get("table_id"), tableId), builder.equal(root.get("tree_id"),treeId));

        List<PathEntity> pathsByTreeId = session.createQuery(query).getResultList();

        transaction.commit();

        return pathsByTreeId;
    }

    public Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
