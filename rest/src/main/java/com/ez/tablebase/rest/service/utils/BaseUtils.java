package com.ez.tablebase.rest.service.utils;
/*
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.DataAccessPathEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.TableEntity;
import com.ez.tablebase.rest.model.DataType;
import com.ez.tablebase.rest.repository.CategoryRepository;
import com.ez.tablebase.rest.repository.DataAccessPathRepository;
import com.ez.tablebase.rest.repository.TableEntryRepository;
import com.ez.tablebase.rest.repository.TableRepository;

import java.util.*;

public class BaseUtils
{
    private static final String EMPTY_STRING = "";
    final CategoryRepository categoryRepository;
    final TableRepository tableRepository;
    final DataAccessPathRepository dataAccessPathRepository;
    final TableEntryRepository tableEntryRepository;

    BaseUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
    {
        this.categoryRepository = categoryRepository;
        this.tableRepository = tableRepository;
        this.dataAccessPathRepository = dataAccessPathRepository;
        this.tableEntryRepository = tableEntryRepository;
    }

    public TableEntity createTable(Integer userId, String tableName, String tags, boolean isPublic)
    {
        TableEntity newTable = new TableEntity();
        newTable.setUserId(userId);
        newTable.setTableName(tableName);
        newTable.setTags(tags);
        newTable.setPublic(isPublic);
        return tableRepository.save(newTable);
    }

    CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, DataType type)
    {
        CategoryEntity category = new CategoryEntity();
        category.setTableId(tableId);
        category.setAttributeName(attributeName);
        category.setParentId(parentId);
        category.setType((byte) type.ordinal());
        return categoryRepository.save(category);
    }

    public CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, byte type)
    {
        TableEntity table = validateTable(tableId);
        CategoryEntity parent = validateCategory(table.getTableId(), parentId);

        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(table.getTableId());
        entity.setAttributeName(attributeName);
        entity.setParentId(parent.getCategoryId());
        entity.setType(type);
        categoryRepository.save(entity);
        entity = categoryRepository.findCategory(entity.getTableId(), entity.getCategoryId());

        return entity;
    }

    DataAccessPathEntity createDataAccessPath(Integer tableId, Integer entryId, Integer categoryId)
    {
        DataAccessPathEntity dap = new DataAccessPathEntity();
        dap.setTableId(tableId);
        dap.setEntryId(entryId);
        dap.setCategoryId(categoryId);
        dataAccessPathRepository.save(dap);
        return dap;
    }

    public EntryEntity createEntry(Integer tableId, String data)
    {
        EntryEntity entry = new EntryEntity();
        entry.setTableId(tableId);
        entry.setData(data);
        return tableEntryRepository.save(entry);
    }

    void initialiseEntries(CategoryEntity entity, List<CategoryEntity> categoryPath, Map<Integer, List<CategoryEntity>> accessMap)
    {
        Set<Integer> accessKeys = accessMap.keySet();
        for (Integer key : accessKeys)
        {
            EntryEntity entry = createEntry(entity.getTableId(), EMPTY_STRING);

            // Create rows for path in access categories
            for (CategoryEntity accessCategory : accessMap.get(key))
                createDataAccessPath(entity.getTableId(), entry.getEntryId(), accessCategory.getCategoryId());

            // Create rows for path in categories
            for (CategoryEntity categoryEntity : categoryPath)
                createDataAccessPath(entity.getTableId(), entry.getEntryId(), categoryEntity.getCategoryId());
        }
    }


    EntryEntity duplicateEntry (Integer tableId, Integer entryId)
    {
        EntryEntity entity = validateEntry(tableId, entryId);
        EntryEntity newEntity = new EntryEntity();
        newEntity.setTableId(entity.getTableId());
        newEntity.setData(entity.getData());
        return tableEntryRepository.save(newEntity);
    }

    Map<Integer, List<CategoryEntity>> constructTreeMap(CategoryEntity node)
    {
        Map<Integer, List<CategoryEntity>> treeMap = new HashMap<>();
        List<List<CategoryEntity>> paths = buildPaths(node);
        printPathList(paths);

        for(List<CategoryEntity> path : paths)
            treeMap.put(path.get(path.size()-1).getCategoryId(), path);

        return treeMap;
    }

    private List<List<CategoryEntity>> buildPaths(CategoryEntity node)
    {
        if (node == null)
            return new LinkedList<>();
        else
            return findPaths(node);
    }

    private List<List<CategoryEntity>> findPaths (CategoryEntity node)
    {
        List<List<CategoryEntity>> retLists = new LinkedList<>();

        List<CategoryEntity> children = findChildren(node.getTableId(), node.getCategoryId());
        if (children.size() == 0)
        {
            List<CategoryEntity> leafList = new LinkedList<>();
            leafList.add(node);
            retLists.add(leafList);
        }
        else
        {
            for (CategoryEntity child : children)
            {
                List<List<CategoryEntity>> nodeLists = findPaths(child);
                for (List<CategoryEntity> nodeList : nodeLists)
                {
                    nodeList.add(0, node);
                    retLists.add(nodeList);
                }
            }
        }

        return retLists;
    }

    private void printPathList (List<List<CategoryEntity>> paths)
    {
        System.out.println("Printing Path");
        for(List<CategoryEntity> path : paths)
        {
            for(int count = 0; count < path.size(); count ++)
            {
                System.out.print(path.get(count).getCategoryId());
                if(count != path.size() -1 )
                    System.out.print('-');
            }
            System.out.println();
        }
        System.out.println();
    }

    void updateDataAccessPaths(CategoryEntity newLeaf, CategoryEntity oldLeaf)
    {
        // Get all DAPs that contains the affected category in its path
        List<List<DataAccessPathEntity>> daps = findPathsByCategory(oldLeaf);
        for (List<DataAccessPathEntity> path : daps)
        {
            Integer entryId = path.get(0).getEntryId();
            DataAccessPathEntity dapEntity = createDataAccessPath(newLeaf.getTableId(), entryId, newLeaf.getCategoryId());
            path.add(dapEntity);
        }
    }

    private List<List<DataAccessPathEntity>> findPathsByCategory(CategoryEntity category)
    {
        // We need to create the entries and data access paths related to the new category
        List<CategoryEntity> rootCategories = categoryRepository.findRootNodes(category.getTableId());
        CategoryEntity category1 = rootCategories.get(0);
        CategoryEntity category2 = rootCategories.get(1);

        // By retrieving all children of the given root node, we are able to determine which tree the new category is located in
        List<Integer> categoryList1 = getAllCategoryChildren(category1.getTableId(), category1.getCategoryId());
        List<Integer> categoryList2 = getAllCategoryChildren(category2.getTableId(), category2.getCategoryId());

        List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(category.getTableId(), category.getCategoryId());
        List<List<DataAccessPathEntity>> daps = new LinkedList<>();
        for (Integer entry : entries)
        {
            List<DataAccessPathEntity> dap = dataAccessPathRepository.getEntryAccessPath(category.getTableId(), entry);

            // We need to remove any categories that are not part of the affected tree
            for (Iterator<DataAccessPathEntity> iter = dap.listIterator(); iter.hasNext(); )
            {
                DataAccessPathEntity dapEntity = iter.next();
                if (categoryList1.contains(category.getCategoryId()))
                {
                    if (categoryList2.contains(dapEntity.getCategoryId()))
                        iter.remove();
                }
                else if (categoryList2.contains(category.getCategoryId()))
                {
                    if (categoryList1.contains(dapEntity.getCategoryId()))
                        iter.remove();
                }
            }

            daps.add(dap);
        }

        return daps;
    }

    List<Integer> getAllCategoryChildren(Integer tableId, Integer categoryId)
    {
        TableEntity table = validateTable(tableId);
        CategoryEntity category = validateCategory(table.getTableId(), categoryId);
        List<Integer> children = categoryRepository.getAllChildren(table.getTableId(), category.getCategoryId());
        children.add(0, category.getCategoryId());
        return children;
    }

    public void updateDataAccessPaths(CategoryEntity category, CategoryEntity oldParent, CategoryEntity newParent)
    {
        // We must
    }

    public List<CategoryEntity> findChildren(Integer tableId, Integer categoryId)
    {
        List<CategoryEntity> children = categoryRepository.findChildren(tableId, categoryId);
        // MySQL will return a list with a null element if there's no children
        children.removeIf(Objects::isNull);

        return children;
    }

    public TableEntity validateTable (int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }

    public CategoryEntity validateCategory (int tableId, int categoryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        CategoryEntity entity = categoryRepository.findCategory(tableEntity.getTableId(), categoryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Category found for category id: " + categoryId + ", in table id: " + tableId);

        return entity;
    }

    public EntryEntity validateEntry (int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        EntryEntity entity = tableEntryRepository.findTableEntry(tableEntity.getTableId(), entryId);

        if(entity == null)
            throw new ObjectNotFoundException("No Entry found for entry id: " + entryId + ", in table id: " + tableId);

        List<DataAccessPathEntity> path = dataAccessPathRepository.getEntryAccessPath(tableEntity.getTableId(), entity.getEntryId());
        if(path.isEmpty() || path.get(0) == null)
            throw new ObjectNotFoundException("No Data Access Paths found for entry id: " + entryId + ", in table id: " + tableId);

        return entity;
    }
}
