package com.ez.tablebase.rest.service.utils;
/*
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.common.html.Cell;
import com.ez.tablebase.rest.common.html.Table;
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
import java.util.concurrent.LinkedBlockingQueue;

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

    public CategoryEntity createCategory(Integer tableId, String attributeName, Integer parentId, Integer treeId)
    {
        TableEntity table = validateTable(tableId);
        CategoryEntity parent = (parentId == null) ? null : categoryRepository.findCategory(table.getTableId(), parentId);

        CategoryEntity entity = new CategoryEntity();
        entity.setTableId(table.getTableId());
        entity.setAttributeName(attributeName);
        entity.setParentId((parent == null) ? null : parent.getCategoryId());
        entity.setTreeId(treeId);
        categoryRepository.save(entity);
        entity = categoryRepository.findCategory(entity.getTableId(), entity.getCategoryId());

        return entity;
    }

    DataAccessPathEntity createDataAccessPath(Integer tableId, Integer entryId, Integer categoryId, Integer treeId, byte type)
    {
        DataAccessPathEntity dap = new DataAccessPathEntity();
        dap.setTableId(tableId);
        dap.setEntryId(entryId);
        dap.setCategoryId(categoryId);
        dap.setTreeId(treeId);
        dap.setType(type);
        dataAccessPathRepository.save(dap);
        return dap;
    }

    void deleteDAPByEntryId(Integer tableId, Integer entryId)
    {
        dataAccessPathRepository.deleteDAPByEntryId(tableId, entryId);
    }

    EntryEntity createEntry(Integer tableId, String data)
    {
        EntryEntity entry = new EntryEntity();
        entry.setTableId(tableId);
        entry.setData(data);
        return tableEntryRepository.save(entry);
    }

    public void updateTableEntry(Integer tableId, Integer entryId, String data)
    {
        tableEntryRepository.updateTableEntry(tableId, entryId, data);
    }

    void deleteTableEntry(Integer tableId, Integer entryId)
    {
        tableEntryRepository.deleteTableEntry(tableId, entryId);
    }

    private Integer calculateNumberOfEntries(Integer tableId)
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

    void initialiseEntries(CategoryEntity category)
    {
        Map<Integer, List<CategoryEntity>> currTreeMap = constructTreeMap(categoryRepository.getRootCategoryByTreeId(category.getTableId(), category.getTreeId()));
        Integer numEntries = calculateNumberOfEntries(category.getTableId()) / currTreeMap.values().size();
        List<Integer> treeIds = getTreeIds(category.getTableId());
        treeIds.remove(category.getTreeId());
        List<List<List<CategoryEntity>>> treePaths = new LinkedList<>();

        for (Integer treeId : treeIds)
            treePaths.add(buildPaths(categoryRepository.getRootCategoryByTreeId(category.getTableId(), treeId)));

        List<List<CategoryEntity>> permPaths = new ArrayList<>();
        generatePermutations(treePaths, permPaths, 0, new LinkedList<>());

        for (int count = 0; count < numEntries; count++)
        {
            EntryEntity entry = createEntry(category.getTableId(), EMPTY_STRING);
            for (CategoryEntity categoryEntity : currTreeMap.get(category.getCategoryId()))
                createDataAccessPath(category.getTableId(), entry.getEntryId(), categoryEntity.getCategoryId(), category.getTreeId(), (byte) DataType.TEXT.ordinal());

            List<CategoryEntity> dapsToCreate = permPaths.get(count);
            for(CategoryEntity pathEntity : dapsToCreate)
            {
                createDataAccessPath(pathEntity.getTableId(), entry.getEntryId(), pathEntity.getCategoryId(), pathEntity.getTreeId(), (byte) DataType.TEXT.ordinal());
            }
        }
    }

    private static void generatePermutations(List<List<List<CategoryEntity>>> paths, List<List<CategoryEntity>> res, int d, List<CategoryEntity> current) {
        // if depth equals number of original collections, final reached, add and return
        if (d == paths.size()) {
            res.add(current);
            return;
        }

        // iterate from current collection and copy 'current' element N times, one for each element
        List<List<CategoryEntity>> currentCollection = paths.get(d);
        for (List<CategoryEntity> element : currentCollection) {
            List<CategoryEntity> copy = new LinkedList<>(current);
            copy.addAll(element);
            generatePermutations(paths, res, d + 1, copy);
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

    private Map<Integer, List<CategoryEntity>> constructTreeMap(CategoryEntity node)
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

    void updateDataAccessPaths(CategoryEntity newLeaf, CategoryEntity oldLeaf, Integer treeId)
    {
        // Get all DAPs that contains the affected category in its path
        List<List<DataAccessPathEntity>> daps = findPathsByCategory(oldLeaf, treeId);
        for (List<DataAccessPathEntity> path : daps)
        {
            Integer entryId = path.get(0).getEntryId();
            byte type = dataAccessPathRepository.getTypeByEntryId(newLeaf.getTableId(), entryId);
            DataAccessPathEntity dapEntity = createDataAccessPath(newLeaf.getTableId(), entryId, newLeaf.getCategoryId(), treeId, type);
            path.add(dapEntity);
        }
    }

    /*
     * 1. Store the path to the oldParent category
     * 2. Store the path to the newParent category
     * 3. Find all DAPs that contain the sub path "oldParent -> category"
     * 4. If the new parent has no children then we must create a new category to store the current entries and update the data access paths
     * 5. Replace the path to the old parent with the path to the new parent for each DAP returned from step 1
     * 6. If the old parent does not have any children then we must create a data access path to the old parent and empty entries
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
        for (Integer entryId : affectedEntries)
            affectedPaths.add(dataAccessPathRepository.getEntryAccessPathByTree(category.getTableId(), entryId, category.getTreeId()));

        // Step 4 - If the new parent has no children then we must create a new category to store the current entries and update the data access paths
        List<CategoryEntity> newParentChildren = findChildren(newParent.getTableId(), newParent.getCategoryId());
        newParentChildren.remove(category);
        if (newParentChildren.size() == 0)
        {
            CategoryEntity newChild = createCategory(newParent.getTableId(), "New Child", newParent.getCategoryId(), newParent.getTreeId());
            List<Integer> newParentEntries = dataAccessPathRepository.getEntryByPathContainingCategory(newParent.getTableId(), newParent.getCategoryId());
            for (Integer entryId : newParentEntries)
                createDataAccessPath(newChild.getTableId(), entryId, newChild.getCategoryId(), newChild.getTreeId(), dataAccessPathRepository.getTypeByEntryId(newChild.getTableId(), entryId));
        }

        // Step 3 - Go through each affected path and replace the path of the old parent with the path of the new parent
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

                dap.add(createDataAccessPath(tableId, entryId, categoryId, category.getTreeId(), dataAccessPathRepository.getTypeByEntryId(tableId, entryId)));
            }
        }

        // Step 6 - Check if the old parent has any children left. If not, create a data access path with the path to the old parent and create new entries
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

    private List<CategoryEntity> findRootNodes(Integer tableId)
    {
        return categoryRepository.findRootNodes(tableId);
    }

    private CategoryEntity findRootNodeByTreeId(Integer tableId, Integer treeId)
    {
        return categoryRepository.getRootCategoryByTreeId(tableId, treeId);
    }

    public String converTableToTHtml(int tableId)
    {
        Table htmlTable = new Table();

        List<Integer> treeIds = getTreeIds(tableId);
        Integer deepestTreeId = getDeepestTree(tableId, treeIds);
        // We want to have all other trees to be in the access categories and the deepest tree to be in the header categories
        treeIds.remove(deepestTreeId);

        CategoryEntity deepestRootNode = findRootNodeByTreeId(tableId, deepestTreeId);
        // We will get the total number of paths that indicate a unique leaf node of the deepest tree
        Integer numHeaderColumns = getNumberOfPaths(deepestRootNode);
        Integer maxDepth = getMaxDepthByTreeId(tableId, deepestTreeId);
        // This is going to add all top level categories to the first row of the table
        htmlTable.addNewRow();
        for(Integer treeId : treeIds)
        {
            CategoryEntity rootNode = findRootNodeByTreeId(tableId, treeId);
            htmlTable.addCell(htmlTable.getRowCount(), new Cell(rootNode.getCategoryId(), rootNode.getAttributeName(), 1, maxDepth));
        }
        htmlTable.addCell(htmlTable.getRowCount(), new Cell(deepestRootNode.getCategoryId(), deepestRootNode.getAttributeName(), numHeaderColumns, 1));

        // Time to populate the deeper layers of the deepest tree
        htmlTable = createChildNodes(tableId, deepestRootNode, maxDepth, htmlTable);
        return htmlTable.toHtml();
    }

    private Table createChildNodes(int tableId, CategoryEntity rootNode, Integer maxDepth, Table htmlTable)
    {
        Map<Integer, List<CategoryEntity>> treeByDepth = new HashMap<>();
        for (int depth = 2; depth < maxDepth; depth++)
        {
            dls(tableId, rootNode, depth, treeByDepth);
        }

        return htmlTable;
    }

    private void dls(int tableId, CategoryEntity rootNode, int depth, Map<Integer, List<CategoryEntity>> treeByDepth)
    {
//        List<CategoryEntity> categoriesForDepth = new LinkedList<>();
//        if(depth == 0)
//        {
//            System.out.println(rootNode.getAttributeName());
//        }

        if(depth > 0)
        {
            List<CategoryEntity> children = findChildren(tableId, rootNode.getCategoryId());
            System.out.println("depth: " + depth);
            children.forEach(data -> System.out.println(data.getAttributeName() + " "));
            System.out.println();
            for (CategoryEntity child : children)
            {
                dls(tableId, child, depth - 1, treeByDepth);
            }
        }
        System.out.println();
    }

//    private Table breadthFirstRecursive(Integer tableId, LinkedBlockingQueue<CategoryEntity> queue, Integer depth, Integer maxDepth, Table htmlTable)
//    {
//        if (queue.isEmpty())
//            return htmlTable;
//
//        CategoryEntity node = queue.poll();
//        List<CategoryEntity> children = findChildren(tableId, node.getCategoryId());
//        int rowSpan;
//        int colSpan;
//
//        // The current node is a leaf node
//        if (children.size() == 0)
//        {
//            colSpan = 1;
//            // if the current depth is less than maxDepth, then we currently have a leaf node
//            rowSpan = (depth < maxDepth) ? depth : 1;
//        }
//
//        // The current node still has children. Add the current node with rowSpan = 1 and colSpan = <number of children>
//        else
//        {
//            rowSpan = 1;
//            colSpan = children.size();
//        }
//
//        htmlTable.addCell
//        return htmlTable;
//    }

    private Integer getNumberOfPaths(CategoryEntity rootNode)
    {
        return buildPaths(rootNode).size();
    }

    private Integer getDeepestTree(Integer tableId, List<Integer> treeIds)
    {
        Map<Integer, Integer> depthStore = new HashMap<>();
        for(Integer treeId : treeIds)
        {
            Integer depth = getMaxDepthByTreeId(tableId, treeId);
            depthStore.put(treeId, depth);
        }

        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : depthStore.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        return maxEntry != null ? maxEntry.getKey() : null;
    }

    private Integer getMaxDepthByTreeId(Integer tableId, Integer treeId)
    {
        Integer depth = 0;

        List<List<CategoryEntity>> paths = buildPaths(findRootNodeByTreeId(tableId, treeId));
        for(List<CategoryEntity> path : paths)
        {
            if(path.size() > depth)
                depth = path.size();
        }

        return depth;
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
