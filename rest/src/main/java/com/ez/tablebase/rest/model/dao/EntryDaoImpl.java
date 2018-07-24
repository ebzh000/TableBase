package com.ez.tablebase.rest.model.dao;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.EntryEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EntryDaoImpl implements EntryDao
{

    @Override
    public EntryEntity createEntry(Integer tableId, String label, byte type)
    {
        Session session = getCurrentSession();
        EntryEntity entry = new EntryEntity();
        entry.setTableId(tableId);
        entry.setData(label);
        entry.setType(type);

        Integer entryId = (Integer) session.save(entry);
        entry.setEntryId(entryId);

        return entry;
    }

    @Override
    public List<EntryEntity> getEntryByTableId(Integer tableId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create new query
        CriteriaQuery<EntryEntity> query = builder.createQuery(EntryEntity.class);

        // Build query
        Root<EntryEntity> root = query.from(EntryEntity.class);
        query.select(root).where(builder.equal(root.get("table_id"), tableId));

        List<EntryEntity> entries = session.createQuery(query).getResultList();
        transaction.commit();

        return entries;
    }

    @Override
    public List<EntryEntity> getEntriesForCategoryId(Integer categoryId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Easier to use HQL here
        Query query = session.createQuery("from entries where entry_id IN (select entry_id from paths where category_id = :categoryId)");
        query.setParameter("categoryId", categoryId);

        List<EntryEntity> entries = query.getResultList();
        transaction.commit();

        return entries;
    }

    @Override
    public EntryEntity getEntry(Integer entryId)
    {
        return getCurrentSession().get(EntryEntity.class, entryId);
    }

    @Override
    public void deleteEntry(EntryEntity entry)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.delete(entry);

        transaction.commit();
    }

    @Override
    public void deleteEntryList(List<EntryEntity> entryList)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        for (EntryEntity entryEntity : entryList)
            session.delete(entryEntity);

        transaction.commit();
    }

    public Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
