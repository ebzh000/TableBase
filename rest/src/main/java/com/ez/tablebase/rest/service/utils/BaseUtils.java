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
    public final CategoryRepository categoryRepository;
    public final TableRepository tableRepository;
    public final DataAccessPathRepository dataAccessPathRepository;
    public final TableEntryRepository tableEntryRepository;

    public BaseUtils(CategoryRepository categoryRepository, TableRepository tableRepository, DataAccessPathRepository dataAccessPathRepository, TableEntryRepository tableEntryRepository)
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

    public CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, DataType type)
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

    public DataAccessPathEntity createDataAccessPath(Integer tableId, Integer entryId, Integer categoryId)
    {
        DataAccessPathEntity dap = new DataAccessPathEntity();
        dap.setTableId(tableId);
        dap.setEntryId(entryId);
        dap.setCategoryId(categoryId);
        return dataAccessPathRepository.save(dap);
    }

    public EntryEntity createEntry(Integer tableId, String data)
    {
        EntryEntity entry = new EntryEntity();
        entry.setTableId(tableId);
        entry.setData(data);
        return tableEntryRepository.save(entry);
    }

    EntryEntity duplicateEntry (Integer tableId, Integer entryId)
    {
        EntryEntity entity = validateEntry(tableId, entryId);
        EntryEntity newEntity = new EntryEntity();
        newEntity.setTableId(entity.getTableId());
        newEntity.setData(entity.getData());
        return tableEntryRepository.save(newEntity);
    }

    public Map<Integer, List<CategoryEntity>> constructTreeMap (CategoryEntity node)
    {
        Map<Integer, List<CategoryEntity>> treeMap = new HashMap<>();
        List<List<CategoryEntity>> paths = buildPaths(node);

        for(List<CategoryEntity> path : paths)
            treeMap.put(path.get(path.size()-1).getCategoryId(), path);

        return treeMap;
    }

    public List<List<CategoryEntity>> buildPaths (CategoryEntity node)
    {
        if (node == null)
            return new LinkedList<>();
        else
            return findPaths(node);
    }

    public void printPathList (List<List<CategoryEntity>> paths)
    {
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
    }

    private List<List<CategoryEntity>> findPaths (CategoryEntity node)
    {
        List<List<CategoryEntity>> retLists = new LinkedList<>();

        List<CategoryEntity> children = categoryRepository.findChildren(node.getTableId(), node.getCategoryId());
        // MySQL will return a list with a null element if there's no children
        children.removeAll(Collections.singleton(null));

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

        return entity;
    }

    public List<DataAccessPathEntity> validateAccessPath (int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        List<DataAccessPathEntity> entities = dataAccessPathRepository.getEntryAccessPath(tableEntity.getTableId(), entryId);

        if(entities == null)
            throw new ObjectNotFoundException("No data access path found for the entry id: " + entryId + " in table id: " + tableId);

        return entities;
    }
}
