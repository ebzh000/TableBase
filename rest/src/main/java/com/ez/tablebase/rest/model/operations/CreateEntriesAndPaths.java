package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 2/07/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.PathEntity;
import com.ez.tablebase.rest.model.DataConstants;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.model.dao.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateEntriesAndPaths extends Operation
{
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private PathDao pathDao = new PathDaoImpl();
    private EntryDao entryDao = new EntryDaoImpl();
    private List<List<CategoryEntity>> pathComb;
    private CategoryEntity category;
    private CategoryEntity similarCategory;
    private List<EntryEntity> entries;

    public CreateEntriesAndPaths(CategoryEntity category, List<List<CategoryEntity>> pathComb, CategoryEntity similarCategory, List<EntryEntity> entries)
    {
        this.category = category;
        this.pathComb = pathComb;
        this.similarCategory = similarCategory;
        this.entries = entries;
    }

    @Override
    public Object exec()
    {
        Transaction tx = session.beginTransaction();

        Map<Integer, List<CategoryEntity>> treeMap = categoryDao.buildPathMapForTree(category.getTableId(), category.getTreeId());
        Map<String, Byte> typeMap = null;
        if(!isSameType(entries))
            typeMap = determineEntryTypes(entries, similarCategory);


        int numEntries = calculateNumberOfEntries(similarCategory.getTableId()) / treeMap.values().size();
        for (int count = 0; count < numEntries; count++)
        {
            List<CategoryEntity> accessDap = pathComb.get(count);
            byte type;

            if(typeMap == null)
                type = entries.get(0).getType();
            else
            {
                StringBuilder dapString = new StringBuilder();
                for(CategoryEntity accessEntity : accessDap)
                    dapString.append(accessEntity.getCategoryId()).append("-");

                dapString.deleteCharAt(dapString.length() - 1);
                type = typeMap.get(dapString.toString());
            }

            String data = determineData(type);
            EntryEntity entry = entryDao.createEntry(similarCategory.getTableId(), data, type);

            for (CategoryEntity categoryEntity : treeMap.get(category.getCategoryId()))
                pathDao.createPath(category.getTableId(), entry.getEntryId(), categoryEntity.getCategoryId(), category.getTreeId());

            for(CategoryEntity pathEntity : accessDap)
                pathDao.createPath(pathEntity.getTableId(), entry.getEntryId(), pathEntity.getCategoryId(), pathEntity.getTreeId());
        }

        tx.commit();
        return null;
    }

    private Map<String, Byte> determineEntryTypes(List<EntryEntity> entries, CategoryEntity similarCategory)
    {
        Map<String, Byte> typeMap = new HashMap<>();
        for (EntryEntity entry : entries)
        {
            List<PathEntity> daps = pathDao.getPathsByEntryIdExcludingTreeId(entry.getEntryId(), similarCategory.getTreeId());
            StringBuilder dapString = new StringBuilder();
            for(PathEntity dapEntity : daps)
                dapString.append(dapEntity.getCategoryId()).append("-");

            dapString.deleteCharAt(dapString.length() - 1);
            typeMap.put(dapString.toString(), entry.getType());
        }

        return typeMap;
    }

    private String determineData(byte type)
    {
        String data;
        switch(DataType.values()[type])
        {
            case INTEGER:
                data = DataConstants.NEW_INTEGER;
                break;
            case PERCENT:
                data = DataConstants.NEW_PERCENTAGE;
                break;
            case DECIMAL:
                data = DataConstants.NEW_DECIMAL;
                break;
            case TEXT:
                data = DataConstants.NEW_TEXT;
                break;
            default:
                data = DataConstants.NEW_TEXT;
                break;
        }

        return data;
    }

    private boolean isSameType(List<EntryEntity> entries)
    {
        int type = entries.get(0).getType();
        for (EntryEntity entry : entries)
            if(entry.getType() != type)
                return false;

        return true;
    }

    private Integer calculateNumberOfEntries(Integer tableId)
    {
        Integer numEntries = 1;

        List<Integer> treeIds = categoryDao.getTreeIds(tableId);
        for (Integer treeId : treeIds)
        {
            Map<Integer, List<CategoryEntity>> map = categoryDao.buildPathMapForTree(tableId, treeId);
            numEntries = numEntries * map.keySet().size();
        }

        return numEntries;
    }
}
