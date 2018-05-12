package com.ez.tablebase.rest.service.utils;
/*
 * Created by ErikZ on 19/04/2018.
 */

import com.ez.tablebase.rest.common.ObjectNotFoundException;
import com.ez.tablebase.rest.common.html.Cell;
import com.ez.tablebase.rest.common.html.CellType;
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

public class BaseUtils
{
    private static final String NEW_ENTRY =  "new";
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

        List<CategoryEntity> wetCategories = categoryRepository.findCategoryByName(tableId, attributeName);
        if(wetCategories.size() >= 1)
            attributeName = attributeName + "(" + wetCategories.size() + ")";

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

        List<List<CategoryEntity>> permPaths = generatePermPaths(category.getTableId(), treeIds);
        for (int count = 0; count < numEntries; count++)
        {
            EntryEntity entry = createEntry(category.getTableId(), NEW_ENTRY);
            for (CategoryEntity categoryEntity : currTreeMap.get(category.getCategoryId()))
                createDataAccessPath(category.getTableId(), entry.getEntryId(), categoryEntity.getCategoryId(), category.getTreeId(), (byte) DataType.TEXT.ordinal());

            List<CategoryEntity> dapsToCreate = permPaths.get(count);
            for(CategoryEntity pathEntity : dapsToCreate)
            {
                createDataAccessPath(pathEntity.getTableId(), entry.getEntryId(), pathEntity.getCategoryId(), pathEntity.getTreeId(), (byte) DataType.TEXT.ordinal());
            }
        }
    }

    /*
     * This function will create a list of permutations of Data Access Paths for a given list of treeIds
     */
    private List<List<CategoryEntity>> generatePermPaths(Integer tableId, List<Integer> treeIds)
    {
        List<List<List<CategoryEntity>>> treePaths = new LinkedList<>();
        for (Integer treeId : treeIds)
        {
            CategoryEntity rootNode = categoryRepository.getRootCategoryByTreeId(tableId, treeId);
            treePaths.add(buildPaths(rootNode));
        }

        List<List<CategoryEntity>> permPaths = new ArrayList<>();
        generatePermutations(treePaths, permPaths, 0, new LinkedList<>());
        return permPaths;
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

    private Map<Integer, List<Integer>> constructTreePaths(CategoryEntity rootNode)
    {
        Map<Integer, List<Integer>> treeMap = new HashMap<>();
        List<List<CategoryEntity>> paths = buildPaths(rootNode);

        for (List<CategoryEntity> path : paths)
        {
            List<Integer> dap = new LinkedList<>();
            for(CategoryEntity dapEntity : path)
                dap.add(dapEntity.getCategoryId());

            treeMap.put(path.get(path.size() - 1).getCategoryId(), dap);
        }

        return treeMap;
    }

    List<List<CategoryEntity>> buildPaths(CategoryEntity rootNode)
    {
        if (rootNode == null)
            return new LinkedList<>();
        else
            return findPaths(rootNode);
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

    // This is for debugging
    private void printPathList(List<List<CategoryEntity>> paths)
    {
        System.out.println("Printing Path");
        for (List<CategoryEntity> path : paths)
        {
            for (int count = 0; count < path.size(); count++)
            {
                System.out.print(path.get(count).getCategoryId());
                if (count != path.size() - 1)
                    System.out.print(" - ");
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
        System.out.println(daps);
        for (List<DataAccessPathEntity> path : daps)
        {
            Integer entryId = path.get(0).getEntryId();
            byte type = dataAccessPathRepository.getTypeByEntryId(newLeaf.getTableId(), entryId);
            createDataAccessPath(newLeaf.getTableId(), entryId, newLeaf.getCategoryId(), treeId, type);
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

    public String convertTableToTHtml(int tableId)
    {
        Table htmlTable = new Table(tableId);

        List<Integer> treeIds = getTreeIds(tableId);
        Integer deepestTreeId = getDeepestTree(tableId, treeIds);
        CategoryEntity deepestRootNode = findRootNodeByTreeId(tableId, deepestTreeId);

        // We want to have all other trees to be in the access categories and the deepest tree to be in the header categories (treeIds will be in asc order)
        treeIds.remove(deepestTreeId);

        // Save the total number of columns that will be created
        htmlTable.setHeaderGroupWidth(getNumberOfPaths(deepestRootNode));

        // Save the depth of the header category group
        htmlTable.setHeaderGroupDepth(getTreeDepth(tableId, deepestTreeId));

        // This is going to add all top level categories to the first row of the table
        htmlTable = createTopLevelCategories(htmlTable, tableId, treeIds, deepestRootNode);

        // Time to populate the deeper layers of the deepest tree
        if(findChildren(tableId, deepestRootNode.getCategoryId()).size() != 0)
            htmlTable = createChildCategories(htmlTable, tableId, deepestRootNode);

        htmlTable.printTable();

        // Time to populate the rows of the table (This function also includes the creation of access categories)
        htmlTable = createRows(htmlTable, tableId, treeIds);

        return htmlTable.toHtml();
    }

    private Table createTopLevelCategories(Table htmlTable, Integer tableId, List<Integer> treeIds, CategoryEntity deepestRootNode)
    {
        htmlTable.addNewRow();
        for(Integer treeId : treeIds)
        {
            CategoryEntity rootNode = findRootNodeByTreeId(tableId, treeId);
            htmlTable.addCell(htmlTable.getLatestRowIndex(), new Cell("CategoryId:" + rootNode.getCategoryId(), rootNode.getAttributeName(), CellType.ACCESS_CATEGORY, getTreeDepth(tableId, treeId) - 1, htmlTable.getHeaderGroupDepth()));
        }

        // The VH Category is an internal Category. Users do not need to see this.
        if (!deepestRootNode.getAttributeName().matches("VH"))
        {
            htmlTable.addCell(htmlTable.getLatestRowIndex(), new Cell("CategoryId:" + deepestRootNode.getCategoryId(), deepestRootNode.getAttributeName(), CellType.HEADER_CATEGORY, htmlTable.getHeaderGroupWidth(), 1));
            if(findChildren(tableId, deepestRootNode.getCategoryId()).size() == 0)
            {
                List<Integer> dap = new LinkedList<>();
                dap.add(deepestRootNode.getCategoryId());
                htmlTable.saveColDAP(htmlTable.getTable().get(htmlTable.getLatestRowIndex()).size() - 1, dap);
            }
        }
        return htmlTable;
    }

    private Table createChildCategories(Table htmlTable, int tableId, CategoryEntity rootNode)
    {
        Map<Integer, List<CategoryEntity>> treeByDepth = new HashMap<>();
        for (int depth = 0; depth < htmlTable.getHeaderGroupDepth(); depth++)
            dls(tableId, rootNode, depth + 1, depth, treeByDepth, null);

        // We already added the root node
        treeByDepth.remove(1);

        System.out.println("START - Child Categories");
        printMap(treeByDepth);

        // Initialise map to store paths to each leaf node in deepest tree
        Map<Integer, List<Integer>> treePaths = constructTreePaths(rootNode);

        // Time to complete the header groups for the html table
        for (Integer depth : treeByDepth.keySet())
        {
            htmlTable.addNewRow();

            List<CategoryEntity> categoryList = treeByDepth.get(depth);
            for (CategoryEntity category : categoryList)
            {
                if(htmlTable.getColDAPs().size() != 0)
                {
                    Set<Integer> leafColOffsetSet = htmlTable.getColDAPs().keySet();
                    Integer currRowCellOffset = htmlTable.getTable().get(htmlTable.getLatestRowIndex()).size();
                    while (leafColOffsetSet.contains(currRowCellOffset))
                    {
                        htmlTable.addCell(htmlTable.getLatestRowIndex(), null);
                        currRowCellOffset = htmlTable.getTable().get(htmlTable.getLatestRowIndex()).size();
                    }
                }

                List<CategoryEntity> children = findChildren(tableId, category.getCategoryId());
                int rowSpan;
                int colSpan;

                // The current node is a leaf node
                if (children.size() == 0)
                {
                    // If the current depth is less than maxDepth, then we currently have a leaf node
                    rowSpan = (depth < htmlTable.getHeaderGroupDepth()) ? depth : 1;
                    colSpan = 1;
                    htmlTable.addCell(htmlTable.getLatestRowIndex(), new Cell("CategoryId:" + category.getCategoryId(), category.getAttributeName(), CellType.HEADER_CATEGORY, colSpan, rowSpan));

                    // Save current column's data access path to the leaf node
                    htmlTable.saveColDAP(htmlTable.getTable().get(htmlTable.getLatestRowIndex()).size() - 1, treePaths.get(category.getCategoryId()));
                }

                // The current node still has children. Add the current node with rowSpan = 1 and colSpan = <number of children>
                else
                {
                    rowSpan = 1;
                    colSpan = children.size();
                    htmlTable.addCell(htmlTable.getLatestRowIndex(), new Cell("CategoryId:" + category.getCategoryId(), category.getAttributeName(), CellType.HEADER_CATEGORY, colSpan, rowSpan));

                    // We need to inject some null cells to imitate colspan, so we can correctly save the data access paths of leaf nodes later on
                    for(int count = 0; count < children.size() - 1; count++)
                        htmlTable.addCell(htmlTable.getLatestRowIndex(), null);
                }
            }
        }
        return htmlTable;
    }

    private void dls(int tableId, CategoryEntity rootNode, int currDepth, int depth, Map<Integer, List<CategoryEntity>> treeByDepth, Map<Integer, List<CategoryEntity>> parentChildMap)
    {
        if(depth == 0)
        {
            if(treeByDepth.containsKey(currDepth))
            {
                boolean exists = false;
                for(Integer key : treeByDepth.keySet())
                {
                    if(key != currDepth)
                       exists = treeByDepth.get(key).contains(rootNode);
                }

                if(!exists)
                    treeByDepth.get(currDepth).add(rootNode);
            }
            else
            {
                List<CategoryEntity> categoryList = new LinkedList<>();
                categoryList.add(rootNode);
                treeByDepth.put(currDepth, categoryList);
            }
        }

        if(depth > 0)
        {
            List<CategoryEntity> children;
            if(parentChildMap == null)
                children = findChildren(tableId, rootNode.getCategoryId());
            else
                children = parentChildMap.get(rootNode.getCategoryId());

            if(children.size() != 0)
            {
                for (CategoryEntity child : children)
                    dls(tableId, child, currDepth,depth - 1, treeByDepth, parentChildMap);
            }
        }
    }

    private Table createRows(Table htmlTable, int tableId, List<Integer> treeIds)
    {
        List<List<Cell>> accessCells = createAccessCells(htmlTable, tableId, treeIds);
        Map<HashSet<Integer>, EntryEntity> entryDAPMap = new HashMap<>();
        List<EntryEntity> entries = tableEntryRepository.findAllTableEntries(tableId);

        for(EntryEntity entry : entries)
        {
            List<Integer> path = dataAccessPathRepository.getDapCategoriesByEntry(tableId, entry.getEntryId());
            entryDAPMap.put(new HashSet<>(path), entry);
        }

        for(List<Cell> cellList : accessCells)
        {
            htmlTable.addNewRow();
            for(Cell cell : cellList)
            {
                if(cell == null)
                    htmlTable.addCell(htmlTable.getLatestRowIndex(),null);
                else if(!cell.getLabel().matches("VA"))
                    htmlTable.addCell(htmlTable.getLatestRowIndex(), cell);
            }

            System.out.println(htmlTable.getColDAPs());
            System.out.println(htmlTable.getRowDAPs());
            int dataRowIndex = htmlTable.getLatestRowIndex() - htmlTable.getHeaderGroupDepth();
            for(int dataColIndex = 0; dataColIndex < htmlTable.getHeaderGroupWidth(); dataColIndex++)
            {
                List<Integer> colDAP = htmlTable.getColDAPs().get(dataColIndex);
                List<Integer> rowDAP;
                HashSet<Integer> dap = new HashSet<>();
                dap.addAll(colDAP);

                if(!htmlTable.getRowDAPs().isEmpty())
                {
//                    System.out.println(dataRowIndex);
                    rowDAP = htmlTable.getRowDAPs().get(dataRowIndex);
                    dap.addAll(rowDAP);
                }

                if(entryDAPMap.containsKey(dap))
                {
                    EntryEntity entry = entryDAPMap.get(dap);
                    Cell cell = new Cell(entry.getEntryId().toString(), entry.getData(), CellType.DATA, 1, 1);
                    htmlTable.addCell(htmlTable.getLatestRowIndex(), cell);
                }
            }
        }

        return htmlTable;
    }

    private List<List<Cell>> createAccessCells(Table htmlTable, int tableId, List<Integer> treeIds)
    {
        List<List<CategoryEntity>> permPaths = generatePermPaths(tableId, treeIds);

        // Since we now have all possible data access paths for each row, we can just add it to the html table's cache of rowDAPs
        htmlTable.saveRowDAPS(permPaths);
        htmlTable.setRowCount(permPaths.size());
        List<List<Cell>> accessCells = new LinkedList<>();
        for(int count = 0; count < htmlTable.getRowCount(); count++)
            accessCells.add(new LinkedList<>());

        // We need to determine the total depth of the combined trees
        Integer rowDAPDepth = 1;
        for(Integer treeId : treeIds)
            rowDAPDepth = rowDAPDepth + (getTreeDepth(tableId, treeId) - 1);
        htmlTable.setAccessTreeDepth(rowDAPDepth);

        // Now to apply rowspan and colspan on access cells
        List<List<CategoryEntity>> permPathsNoRoots = removeRootNodes(tableId, treeIds, permPaths);
        Map<Integer, List<CategoryEntity>> accessCategoryListByDepth = constructTreeMapByDepth(htmlTable, permPathsNoRoots);
        System.out.println("Start - Access List By Depth");
        printMap(accessCategoryListByDepth);

        Map<Integer, List<CategoryEntity>> parentChildMap = constructParentChildMap(permPathsNoRoots, accessCategoryListByDepth);
        Map<Integer, List<CategoryEntity>> treeByDepth = new HashMap<>();

        // The parent child map is being modified by the DLS algorithm so we need to make a deep copy
        Map<Integer, List<CategoryEntity>> copyParentChildMap = copy(parentChildMap);

        // Create a tree represented by a map of nodes by depth
        for (int depth = 0; depth < htmlTable.getHeaderGroupDepth(); depth++)
            dls(tableId, accessCategoryListByDepth.get(1).get(0), depth + 1, depth, treeByDepth, copyParentChildMap);

        // We already added the root node
        treeByDepth.remove(1);

        System.out.println("START - Access Tree");
        printMap(treeByDepth);

        // Time to complete the access tree for the html table
        for (Integer depth: treeByDepth.keySet())
        {
            int rowCount = 0;
            List<CategoryEntity> categoryList = treeByDepth.get(depth);
            for (CategoryEntity category : categoryList)
            {
//                System.out.println(category);
                List<CategoryEntity> children = parentChildMap.get(category.getCategoryId());
//                System.out.println(children);
                int rowSpan;
                int colSpan;

                // The current node is a leaf node
                if (children.size() == 0)
                {
                    // If the current depth is less than maxDepth, then we currently have a leaf node
                    colSpan = (depth < htmlTable.getAccessTreeDepth()) ? depth : 1;
                    rowSpan = 1;
                    accessCells.get(rowCount).add(new Cell("CategoryId:" + category.getCategoryId(), category.getAttributeName(), CellType.ACCESS_CATEGORY, colSpan, rowSpan));
                    rowCount++;
                }

                // The current node still has children. Add the current node with rowSpan = 1 and colSpan = <number of children>
                else
                {
                    rowSpan = children.size();
                    colSpan = 1;
                    accessCells.get(rowCount).add(new Cell("CategoryId:" + category.getCategoryId(), category.getAttributeName(), CellType.ACCESS_CATEGORY, colSpan, rowSpan));
                    rowCount++;

                    for(int offset = 0; offset < children.size() - 1; offset++)
                    {
                        accessCells.get(rowCount).add(null);
                        rowCount++;
                    }
                }
            }
        }

        return accessCells;
    }

    private static Map<Integer, List<CategoryEntity>> copy(Map<Integer, List<CategoryEntity>> original) {
        Map<Integer, List<CategoryEntity>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<CategoryEntity>> entry : original.entrySet())
            copy.put(entry.getKey(), new LinkedList<>(entry.getValue()));
        return copy;
    }

    private Map<Integer, List<CategoryEntity>> constructTreeMapByDepth(Table htmlTable, List<List<CategoryEntity>> permPathsNoRoots)
    {
        Map<Integer, List<CategoryEntity>> accessTreeByDepth = new HashMap<>();

        for(int depth = 0; depth < htmlTable.getAccessTreeDepth(); depth++)
        {
            List<CategoryEntity> categoryList = new LinkedList<>();
            for(List<CategoryEntity> path : permPathsNoRoots)
            {
                CategoryEntity category = path.get(depth);
                boolean doesExist = categoryList.stream().anyMatch(categoryEntity -> categoryEntity.getCategoryId().equals(category.getCategoryId()));
                if(!doesExist)
                    categoryList.add(category);
            }
            accessTreeByDepth.put(depth + 1, categoryList);
        }

        return accessTreeByDepth;
    }

    private Map<Integer, List<CategoryEntity>> constructParentChildMap(List<List<CategoryEntity>> permPathsNoRoots, Map<Integer, List<CategoryEntity>> accessCategoryListByDepth)
    {
        Map<Integer,List<CategoryEntity>> parentChildMap = new HashMap<>();
        for(Integer depth : accessCategoryListByDepth.keySet())
        {
            for(CategoryEntity parent : accessCategoryListByDepth.get(depth))
            {
                List<CategoryEntity> children = new LinkedList<>();
                for(List<CategoryEntity> path : permPathsNoRoots)
                {
                    int indexOfParent = -1;
                    for(int index = 0; index < path.size(); index++)
                    {
                        if(path.get(index).getCategoryId().equals(parent.getCategoryId()))
                        {
                            indexOfParent = index;
                            break;
                        }
                    }

                    int indexOfChild = (indexOfParent + 1);
                    if(indexOfParent >= 0 && indexOfChild < path.size())
                    {
                        CategoryEntity child = path.get(indexOfChild);
                        boolean exist = children.stream().anyMatch(categoryEntity -> categoryEntity.getCategoryId().equals(child.getCategoryId()));
                        if (!exist)
                            children.add(child);
                    }
                }
                if(!children.isEmpty())
                    parentChildMap.put(parent.getCategoryId(), children);
                else
                    parentChildMap.put(parent.getCategoryId(), new LinkedList<>());
            }
        }

        return parentChildMap;
    }

    private List<List<CategoryEntity>> removeRootNodes(int tableId, List<Integer> treeIds, List<List<CategoryEntity>> permPaths)
    {
        List<List<CategoryEntity>> permPathsNoRoots = new LinkedList<>();
        List<Integer> rootNodes = new LinkedList<>();
        for (Integer treeId : treeIds)
            rootNodes.add(findRootNodeByTreeId(tableId, treeId).getCategoryId());

        for(List<CategoryEntity> path : permPaths)
        {
            List<CategoryEntity> pathWithNoRoot = new LinkedList<>();
            for (CategoryEntity currCategory : path)
            {
                if(!rootNodes.contains(currCategory.getCategoryId()))
                    pathWithNoRoot.add(currCategory);
            }
            permPathsNoRoots.add(pathWithNoRoot);
        }

        for (List<CategoryEntity> path : permPathsNoRoots)
        {
            CategoryEntity dummyRoot = new CategoryEntity();
            dummyRoot.setCategoryId(-1);
            dummyRoot.setAttributeName("VA");
            path.add(0, dummyRoot);
        }

        return permPathsNoRoots;
    }

    // This is for debugging
    private void printMap(Map<Integer, List<CategoryEntity>> map)
    {
        for (Integer key: map.keySet()){
            List<CategoryEntity> value = map.get(key);
            System.out.print(key + " ");
            value.forEach(category -> System.out.print(category.getAttributeName() + " "));
            System.out.println();
        }
        System.out.println("END");
        System.out.println();
    }

    private Integer getNumberOfPaths(CategoryEntity rootNode)
    {
        return buildPaths(rootNode).size();
    }

    private Integer getDeepestTree(Integer tableId, List<Integer> treeIds)
    {
        Map<Integer, Integer> depthStore = new HashMap<>();
        for(Integer treeId : treeIds)
        {
            Integer depth = getTreeDepth(tableId, treeId);
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

    private Integer getTreeDepth(Integer tableId, Integer treeId)
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
