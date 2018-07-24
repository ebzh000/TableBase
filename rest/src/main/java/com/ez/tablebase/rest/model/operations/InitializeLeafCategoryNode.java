package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 13/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.model.dao.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class InitializeLeafCategoryNode extends Operation
{
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private EntryDao entryDao = new EntryDaoImpl();
    private CategoryEntity category;

    public InitializeLeafCategoryNode(CategoryEntity category)
    {
        this.category = category;
    }

    @Override
    public Object exec()
    {
        Transaction tx = session.beginTransaction();

        List<Integer> treeIds = categoryDao.getTreeIds(category.getTableId());
        List<List<CategoryEntity>> pathComb = categoryDao.findPathCombinations(category.getTableId(), treeIds);

        // By fetching the parent's children, we are able to get another leaf category that has the same parent
        List<CategoryEntity> parentChildren = categoryDao.findCategoryChildren(category.getParentId());
        parentChildren.remove(category);

        // When we get to this point, we are definite that there are other children present.
        // We don't care which child we choose because we want to find out whether if the new category determines the
        // type of all of its children
        CategoryEntity similarCategory = parentChildren.get(0);
        List<EntryEntity> entries =  entryDao.getEntriesForCategoryId(similarCategory.getCategoryId());
        new CreateEntriesAndPaths(category, pathComb, similarCategory, entries).exec();

        tx.commit();
        return null;
    }
}
