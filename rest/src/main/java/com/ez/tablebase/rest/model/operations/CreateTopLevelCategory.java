package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 1/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.dao.CategoryDaoImpl;
import com.ez.tablebase.rest.model.dao.EntryDaoImpl;
import com.ez.tablebase.rest.model.dao.TableDaoImpl;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CreateTopLevelCategory extends Operation<CategoryEntity>
{
    private CategoryCreateRequest request;
    private static final String NEW_CATEGORY_LABEL = "---";
    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();

    public CreateTopLevelCategory (CategoryCreateRequest request)
    {
        this.request = request;
    }


    /**
     * This operation will create a new dimension for a given table.
     * Adding a new dimension will also mean that we need to find all entries and update their paths.
     * @return The root node of the new category tree
     */
    @Override
    public CategoryEntity exec()
    {
        Transaction tx = session.beginTransaction();
        TableEntity table = tableDaoImpl.getTable(request.getTableId());

        // Check if the returned table is null
        if (table == null)
            throw new ObjectNotFoundException("No table with id of " + request.getTableId());

        // A new dimension implies a new category tree, so get a new tree id
        Integer treeId = categoryDaoImpl.getTreeIds(table.getTableId()).size() + 1;

        // Create root category
        CategoryEntity root = categoryDaoImpl.createCategory(table.getTableId(), request.getAttributeName(), null, treeId);

        // Create subcategory for new root node
        CategoryEntity child = categoryDaoImpl.createCategory(table.getTableId(), (request.getAttributeName2().isEmpty() ? NEW_CATEGORY_LABEL : request.getAttributeName2()), root.getCategoryId(), treeId);

        // Add new root and child node to all data access paths
        List<EntryEntity> entries = entryDaoImpl.getEntryByTableId(table.getTableId());
        for (EntryEntity entry : entries)
        {
            pathDaoImpl.createPath(table.getTableId(), entry.getEntryId(), root.getCategoryId(), treeId);
            pathDaoImpl.createPath(table.getTableId(), entry.getEntryId(), child.getCategoryId(), treeId);
        }

        tx.commit();

        return root;
    }
}
