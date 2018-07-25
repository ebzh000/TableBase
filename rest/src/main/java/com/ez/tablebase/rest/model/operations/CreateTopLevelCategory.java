package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 1/06/2018.
 */

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.DataConstants;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.model.dao.*;
import com.ez.tablebase.rest.model.requests.CategoryCreateRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CreateTopLevelCategory extends Operation<CategoryEntity>
{
    private CategoryCreateRequest request;
    private TableDao tableDao = new TableDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private PathDao pathDao = new PathDaoImpl();
    private EntryDao entryDao = new EntryDaoImpl();
    private static final String NEW_ATTRIBUTE_NAME = "New Category";
    ;
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
        TableEntity table = tableDao.getTable(request.getTableId());

        // Check if the returned table is null
        if (table == null)
            throw new ObjectNotFoundException("No table with id of " + request.getTableId());

        // A new dimension ies a new category tree, so get a new tree id
        Integer treeId = categoryDao.getTreeIds(table.getTableId()).size() + 1;

        // Create root category
        CategoryEntity root = categoryDao.createCategory(table.getTableId(), request.getAttributeName(), null, treeId);

        // Create subcategory for new root node
        CategoryEntity child = categoryDao.createCategory(table.getTableId(), (request.getAttributeName2().isEmpty() ? NEW_ATTRIBUTE_NAME : request.getAttributeName2()), root.getCategoryId(), treeId);

        // Get all entries for the specified table
        List<EntryEntity> entries = entryDao.getEntryByTableId(table.getTableId());

        // Insert new Top Level Categories to existing Data Access Paths
        if (!entries.isEmpty())
        {
            // Add new root and child node to all data access paths
            for (EntryEntity entry : entries)
            {
                pathDao.createPath(table.getTableId(), entry.getEntryId(), root.getCategoryId(), treeId);
                pathDao.createPath(table.getTableId(), entry.getEntryId(), child.getCategoryId(), treeId);
            }
        }

        // There are currently no entries at the moment, we need to create an Entry and its Data Access Path
        else
        {
            EntryEntity entry = entryDao.createEntry(request.getTableId(), determineData(request.getEntryType()), request.getEntryType());
            pathDao.createPath(table.getTableId(), entry.getEntryId(), root.getCategoryId(), treeId);
            pathDao.createPath(table.getTableId(), entry.getEntryId(), child.getCategoryId(), treeId);
        }

        tx.commit();

        return root;
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
}
