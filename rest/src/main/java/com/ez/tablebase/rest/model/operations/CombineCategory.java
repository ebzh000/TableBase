package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 1/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.common.IncompatibleCategoryTypeException;
import com.ez.tablebase.rest.common.InvalidOperationException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.PathEntity;
import com.ez.tablebase.rest.model.dao.*;
import com.ez.tablebase.rest.model.requests.CategoryCombineRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CombineCategory extends Operation<CategoryEntity>
{
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private final CategoryCombineRequest request;
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private PathDao pathDao = new PathDaoImpl();
    private EntryDao entryDao = new EntryDaoImpl();

    public CombineCategory(CategoryCombineRequest request)
    {
        this.request = request;
    }

    @Override
    public CategoryEntity exec()
    {
        //TODO: CombineCategory
        Transaction tx = session.beginTransaction();

        // Retrieve provided Category Identifiers
        CategoryEntity category1 = categoryDao.getCategory(request.getCategoryId1());
        CategoryEntity category2 = categoryDao.getCategory(request.getCategoryId2());

        // Retrieve the children of each category
        List<CategoryEntity> children1 = categoryDao.findCategoryChildren(category1.getCategoryId());
        List<CategoryEntity> children2 = categoryDao.findCategoryChildren(category2.getCategoryId());

        // Both Categories must not have any children
        if (children1.size() != 0 || children2.size() != 0)
            throw new InvalidOperationException("Invalid Operation! Selected categories must not have any subcategories");

        // Rename Category 1
        new RenameCategory(category1, request.getNewCategoryName()).exec();

        // Create a map of entries keyed by path
        Map<String, List<EntryEntity>> entriesByPath = new HashMap<>();
        entriesByPath = mapEntriesByPath(entriesByPath, category1);
        entriesByPath = mapEntriesByPath(entriesByPath, category2);

        for (String path : entriesByPath.keySet())
        {
            EntryEntity entry1 = entriesByPath.get(path).get(0);
            EntryEntity entry2 = entriesByPath.get(path).get(1);

            if(entry1.getType() != entry2.getType())
                throw new IncompatibleCategoryTypeException("Specified categories have entries of different types");



        }
        tx.commit();
        return null;
    }

    private Map<String, List<EntryEntity>> mapEntriesByPath(Map<String, List<EntryEntity>> entriesByPath, CategoryEntity category)
    {
        List<EntryEntity> entries = entryDao.getEntriesForCategoryId(category.getCategoryId());
        for (EntryEntity entry : entries)
        {
            List<PathEntity> paths = pathDao.getPathsByEntryIdExcludingTreeId(entry.getEntryId(), category.getTreeId());
            StringBuilder dapString = new StringBuilder();
            for(PathEntity dapEntity : paths)
                dapString.append(dapEntity.getCategoryId()).append("-");

            dapString.deleteCharAt(dapString.length() - 1);
            if(entriesByPath.containsKey(dapString.toString()))
                entriesByPath.get(dapString.toString()).add(entry);
            else
            {
                List<EntryEntity> entryList = new LinkedList<>();
                entryList.add(entry);
                entriesByPath.put(dapString.toString(), entryList);
            }
        }

        return entriesByPath;
    }


}
