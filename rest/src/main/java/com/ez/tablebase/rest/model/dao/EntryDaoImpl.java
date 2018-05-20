package com.ez.tablebase.rest.model.dao;
/*

 * Created by ErikZ on 19/09/2017.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.EntryEntity;
import org.hibernate.Session;

import java.util.List;

public class EntryDaoImpl implements EntryDao
{

    @Override
    public EntryEntity createEntry(Integer tableId, String label, byte type)
    {
        return null;
    }

    @Override
    public List<EntryEntity> getEntryByTableId(Integer tableId)
    {
        return null;
    }

    @Override
    public List<EntryEntity> getEntryContainingCategoryId(Integer categoryId)
    {
        return null;
    }

    @Override
    public EntryEntity duplicateEntity(EntryEntity entry)
    {
        return null;
    }

    @Override
    public void deleteEntry(EntryEntity entry)
    {

    }

    @Override
    public void deleteEntryList(List<EntryEntity> entryList)
    {

    }

    public Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
