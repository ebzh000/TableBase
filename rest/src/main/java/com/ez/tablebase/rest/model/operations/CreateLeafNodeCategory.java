package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 1/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CreateLeafNodeCategory extends Operation<CategoryEntity>
{
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private CategoryCreateRequest request;

    public CreateLeafNodeCategory(CategoryCreateRequest request)
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

        // If the parentCategory has children, then we must initialise the new child category as a leaf node
        if (parentChildren.size() != 0)
            initialiseLeafCategory(category, request.getEntryType());

        // If the parentCategory has no children, then we must update all data access paths containing the parent category
        // to now include the new child category.
        else
            updatePaths(category, parentCategory, category.getTreeId());

        tx.commit();

        return category;
    }
}
