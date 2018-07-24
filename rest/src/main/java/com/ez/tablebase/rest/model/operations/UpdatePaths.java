package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 13/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.PathEntity;
import com.ez.tablebase.rest.model.dao.PathDao;
import com.ez.tablebase.rest.model.dao.PathDaoImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UpdatePaths extends Operation
{
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private PathDao pathDao = new PathDaoImpl();
    private CategoryEntity newCategory;
    private CategoryEntity parentCategory;
    private Integer treeId;

    public UpdatePaths(CategoryEntity category, CategoryEntity parentCategory, Integer treeId)
    {
        this.newCategory = category;
        this.parentCategory = parentCategory;
        this.treeId = treeId;
    }

    @Override
    public Object exec()
    {
        Transaction tx = session.beginTransaction();

        // Get all DAPs that contains the affected category in its path
        List<List<PathEntity>> daps = pathDao.getPathByCategoryIdAndTreeId(parentCategory, treeId);
        for (List<PathEntity> path : daps)
        {
            Integer entryId = path.get(0).getEntryId();
            pathDao.createPath(newCategory.getTableId(), entryId, newCategory.getCategoryId(), treeId);
        }

        tx.commit();
        return null;
    }
}
