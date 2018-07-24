package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 1/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.PathEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.*;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class CreateCategoryAndLink extends Operation<CategoryEntity>
{
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private TableDao tableDao = new TableDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private PathDao pathDao = new PathDaoImpl();
    private EntryDao entryDao = new EntryDaoImpl();
    private CategoryCreateRequest request;

    public CreateCategoryAndLink(CategoryCreateRequest request)
    {
        this.request = request;
    }

    @Override
    public CategoryEntity exec()
    {
        Transaction tx = session.beginTransaction();
        TableEntity table = tableDao.getTable(request.getTableId());
        if (table == null)
            throw new ObjectNotFoundException("No table with id: " + request.getTableId());

        CategoryEntity parentCategory = categoryDao.getCategory(request.getParentId());
        if (parentCategory == null)
            throw new ObjectNotFoundException("No category with id: " + request.getParentId());

        CategoryEntity category = categoryDao.createCategory(table.getTableId(), request.getAttributeName(), parentCategory.getCategoryId(), parentCategory.getTreeId());
        List<CategoryEntity> parentChildren = categoryDao.findCategoryChildren(parentCategory.getParentId());
        parentChildren.remove(category);

        // If the parentCategory has children, then we must update all current children to now point to the new category
        if (parentChildren.size() != 0)
        {
            for (CategoryEntity child : parentChildren)
            {
                categoryDao.updateCategoryParent(child.getCategoryId(), category.getCategoryId());

                List<EntryEntity> affectedEntries = entryDao.getEntriesForCategoryId(child.getCategoryId());
                List<List<PathEntity>> affectedPaths = new LinkedList<>();

                for (EntryEntity entry : affectedEntries)
                    affectedPaths.add(pathDao.getPathsByEntryIdAndTreeId(entry.getEntryId(), category.getTreeId()));

                for (List<PathEntity> dap : affectedPaths)
                    pathDao.createPath(category.getTableId(), dap.get(0).getEntryId(), category.getCategoryId(), category.getTreeId());
            }
        }

        // If the parentCategory has no children, then we must update all data access paths containing the parent category
        // to now include the new child category.
        else
        {
            UpdatePaths updatePaths = new UpdatePaths(category, parentCategory, category.getTreeId());
            updatePaths.exec();
        }

        tx.commit();
        return null;
    }
}
