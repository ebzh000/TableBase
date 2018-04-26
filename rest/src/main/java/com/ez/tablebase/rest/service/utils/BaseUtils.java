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

    public List<Integer> getTreeIds(Integer tableId)
    {
        return categoryRepository.getTreeIds(tableId);
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

    public CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, byte type, Integer treeId)
    {
        TableEntity table = validateTable(tableId);
        CategoryEntity parent = (parentId == null) ? null : categoryRepository.findCategory(table.getTableId(), parentId);

        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(table.getTableId());
        entity.setAttributeName(attributeName);
        entity.setParentId((parent == null) ? null : parent.getCategoryId());
        entity.setType(type);
        entity.setTreeId(treeId);
        categoryRepository.save(entity);
        entity = categoryRepository.findCategory(entity.getTableId(), entity.getCategoryId());

        return entity;
    }

    DataAccessPathEntity createDataAccessPath(Integer tableId, Integer entryId, Integer categoryId, Integer treeId)
    {
        DataAccessPathEntity dap = new DataAccessPathEntity();
        dap.setTableId(tableId);
        dap.setEntryId(entryId);
        dap.setCategoryId(categoryId);
        dap.setTreeId(treeId);
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

    Integer calculateNumberOfEntries (Integer tableId)
    {
        Integer numEntries = 1;

        List<CategoryEntity> topLevelCategories = findRootNodes(tableId);
        for (CategoryEntity rootNode : topLevelCategories)
        {
            Map<Integer,List<CategoryEntity>> treeMap = constructTreeMap(rootNode);
            numEntries = numEntries * treeMap.keySet().size();
        }

        return numEntries;
    }

    /**
     * Creates all required data access paths and entries
     *
     * @param entity       New category
     * @param entry        The newly created entry
     * @param accessMap    The treemap of the access categories to the new category
     */
    void initialiseDapsForEntries(CategoryEntity entity, EntryEntity entry, Map<Integer, List<CategoryEntity>> accessMap)
    {
        Set<Integer> accessKeys = accessMap.keySet();
        for (Integer key : accessKeys)
        {
            // Create rows for path in access categories
            for (CategoryEntity accessCategory : accessMap.get(key))
                createDataAccessPath(entity.getTableId(), entry.getEntryId(), accessCategory.getCategoryId(), 2);
        }
    }

    void initialiseEntries(CategoryEntity category)
    {
        Map<Integer, List<CategoryEntity>> currTreeMap = constructTreeMap(categoryRepository.getRootCategoryByTreeId(category.getTableId(), category.getTreeId()));
        Integer numEntries = calculateNumberOfEntries(category.getTableId()) / currTreeMap.values().size();

        for (int count = 0; count < numEntries; count++)
        {
            EntryEntity entry = createEntry(category.getTableId(), EMPTY_STRING);

            for (CategoryEntity categoryEntity : currTreeMap.get(category.getCategoryId()))
                createDataAccessPath(category.getTableId(), entry.getEntryId(), categoryEntity.getCategoryId(), category.getTreeId());

            List<Integer> treeIds = getTreeIds(category.getTableId());
            treeIds.remove(category.getTreeId());
            for (Integer treeId : treeIds)
            {
                CategoryEntity rootCategory = categoryRepository.getRootCategoryByTreeId(category.getTableId(), treeId);
                Map<Integer, List<CategoryEntity>> treeMap = constructTreeMap(rootCategory);

                initialiseDapsForEntries(category, entry, treeMap);
            }
        }
    }

    EntryEntity duplicateEntry(Integer tableId, Integer entryId)
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

        for (List<CategoryEntity> path : paths)
            treeMap.put(path.get(path.size() - 1).getCategoryId(), path);

        return treeMap;
    }

    List<List<CategoryEntity>> buildPaths(CategoryEntity node)
    {
        if (node == null)
            return new LinkedList<>();
        else
            return findPaths(node);
    }

    private List<List<CategoryEntity>> findPaths(CategoryEntity node)
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

    private void printPathList(List<List<CategoryEntity>> paths)
    {
        System.out.println("Printing Path");
        for (List<CategoryEntity> path : paths)
        {
            for (int count = 0; count < path.size(); count++)
            {
                System.out.print(path.get(count).getCategoryId());
                if (count != path.size() - 1)
                    System.out.print('-');
            }
            System.out.println();
        }
        System.out.println();
    }

    private List<List<DataAccessPathEntity>> findPathsByCategory(CategoryEntity category, Integer treeId)
    {
        List<List<DataAccessPathEntity>> daps = new LinkedList<>();

        List<Integer> entries = dataAccessPathRepository.getEntriesForCategory(category.getTableId(), category.getCategoryId());
        for (Integer entry : entries)
            daps.add(dataAccessPathRepository.getEntryAccessPathByTree(category.getTableId(), entry, treeId));

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

    public void updateDataAccessPaths(CategoryEntity newLeaf, CategoryEntity oldLeaf, Integer treeId)
    {
        // Get all DAPs that contains the affected category in its path
        List<List<DataAccessPathEntity>> daps = findPathsByCategory(oldLeaf, treeId);
        for (List<DataAccessPathEntity> path : daps)
        {
            Integer entryId = path.get(0).getEntryId();
            DataAccessPathEntity dapEntity = createDataAccessPath(newLeaf.getTableId(), entryId, newLeaf.getCategoryId(), treeId);
            path.add(dapEntity);
        }
    }

    /*
     * 1. Store the path to the oldParent category
     * 2. Store the path to the newParent category
     * 3. Find all DAPs that contain the sub path "oldParent -> category"
     * 4. Replace the path to the old parent with the path to the new parent for each DAP returned from step 1
     * 5. If the old parent does not have any children then we must create a data access path to the old parent and empty entries
     */
    public void updateDataAccessPaths(CategoryEntity category, CategoryEntity oldParent, CategoryEntity newParent)
    {
        CategoryEntity rootEntity = categoryRepository.getRootCategoryByTreeId(category.getTableId(), category.getTreeId());
        List<List<CategoryEntity>> pathList = buildPaths(rootEntity);

        // Step 1 & 2 - Get a the path to the new and old parent category
        List<Integer> pathToOldParent = getPathToCategory(oldParent, pathList);
        List<Integer> pathToNewParent = getPathToCategory(newParent, pathList);

        // Step 3 - Get a list of all data access paths that contains selected category
        List<Integer> affectedEntries = dataAccessPathRepository.getEntryByPathContainingCategory(category.getTableId(), category.getCategoryId());
        List<List<DataAccessPathEntity>> affectedPaths = new LinkedList<>();

        // We need to remove any pathElements that are not contained in the selected category's tree
        for (Integer entryId : affectedEntries)
            affectedPaths.add(dataAccessPathRepository.getEntryAccessPathByTree(category.getTableId(), entryId, category.getTreeId()));

        // Step 4 - Go through each affected path and replace the path of the old parent with the path of the new parent
        for (List<DataAccessPathEntity> dap : affectedPaths)
        {
            for (Iterator<DataAccessPathEntity> iterator = dap.listIterator(); iterator.hasNext(); )
            {
                DataAccessPathEntity pathEntity = iterator.next();
                if (pathToOldParent.contains(pathEntity.getCategoryId()))
                {
                    dataAccessPathRepository.delete(pathEntity);
                    iterator.remove();
                }
            }

            for (Integer categoryId : pathToNewParent)
            {
                Integer tableId = dap.get(0).getTableId();
                Integer entryId = dap.get(0).getEntryId();

                dap.add(createDataAccessPath(tableId, entryId, categoryId, category.getTreeId()));
            }
        }

        // Step 5 - Check if the old parent has any children left. If not, create a data access path with the path to the old parent and create new entries
        List<CategoryEntity> oldParentChildren = findChildren(oldParent.getTableId(), oldParent.getCategoryId());
        if (oldParentChildren.size() == 0)
            initialiseEntries(oldParent);
    }

    List<Integer> getPathToCategory(CategoryEntity category, List<List<CategoryEntity>> pathList)
    {
        List<Integer> pathToCategory = new LinkedList<>();

        for (List<CategoryEntity> path : pathList)
        {
            int index = -1;
            if (!pathToCategory.isEmpty())
                break;

            for (int count = 0; count < path.size(); count++)
            {
                if (Objects.equals(path.get(count).getCategoryId(), category.getCategoryId()))
                {
                    index = count;
                    break;
                }
            }

            for (int count = 0; count <= index; count++)
                pathToCategory.add(path.get(count).getCategoryId());
        }

        return pathToCategory;
    }

    public List<CategoryEntity> findChildren(Integer tableId, Integer categoryId)
    {
        List<CategoryEntity> children = categoryRepository.findChildren(tableId, categoryId);
        // MySQL will return a list with a null element if there's no children
        children.removeIf(Objects::isNull);

        return children;
    }

    List<CategoryEntity> findRootNodes(Integer tableId)
    {
        return categoryRepository.findRootNodes(tableId);
    }


    public TableEntity validateTable(int tableId)
    {
        TableEntity entity = tableRepository.findTable(tableId);

        if (entity == null)
            throw new ObjectNotFoundException("No table found for the id: " + tableId);

        return entity;
    }

    public CategoryEntity validateCategory(int tableId, int categoryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        CategoryEntity entity = categoryRepository.findCategory(tableEntity.getTableId(), categoryId);

        if (entity == null)
            throw new ObjectNotFoundException("No Category found for category id: " + categoryId + ", in table id: " + tableId);

        return entity;
    }

    public EntryEntity validateEntry(int tableId, int entryId)
    {
        TableEntity tableEntity = validateTable(tableId);
        EntryEntity entity = tableEntryRepository.findTableEntry(tableEntity.getTableId(), entryId);

        if (entity == null)
            throw new ObjectNotFoundException("No Entry found for entry id: " + entryId + ", in table id: " + tableId);

        List<DataAccessPathEntity> path = dataAccessPathRepository.getEntryAccessPath(tableEntity.getTableId(), entity.getEntryId());
        if (path.isEmpty() || path.get(0) == null)
            throw new ObjectNotFoundException("No Data Access Paths found for entry id: " + entryId + ", in table id: " + tableId);

        return entity;
    }
}
