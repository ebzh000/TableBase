package com.ez.tablebase.rest.model.operations;
/*
 * Created by ErikZ on 5/06/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.PathEntity;
import com.ez.tablebase.rest.model.dao.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BaseOperations
{
    TableDao tableDao = new TableDaoImpl();
    CategoryDao categoryDao = new CategoryDaoImpl();
    PathDao pathDao = new PathDaoImpl();
    EntryDao entryDao = new EntryDaoImpl();

    void initialiseLeafCategory(CategoryEntity category, byte type)
    {
        Map<Integer, List<CategoryEntity>> treeMap = categoryDao.buildPathMapForTree(category.getTableId(), category.getTreeId());
        List<Integer> treeIds = categoryDao.getTreeIds(category.getTableId());
        List<List<CategoryEntity>> pathComb = categoryDao.findPathCombinations(category.getTableId(), treeIds);
        Map<String, Byte> typeMap = new HashMap<>();

        // By fetching the parent's children, we are able to get another leaf category that has the same parent
        List<CategoryEntity> parentChildren = categoryDao.findCategoryChildren(category.getParentId());
        parentChildren.remove(category);

        // When we get to this point, we are definite that there are other children present.
        // We don't care which child we choose because we want to find out whether if the new category determines the
        // type of all of its children
        CategoryEntity similarCategory = parentChildren.get(0);
        List<EntryEntity> entries =  pathDao.getPathEntryByCategoryId(similarCategory.getCategoryId());
    }

    void updatePaths(CategoryEntity category, CategoryEntity parentCategory, Integer treeId)
    {
        // Get all DAPs that contains the affected category in its path
        List<List<PathEntity>> daps = pathDao.getPathByCategoryIdAndTreeId(parentCategory, treeId);
        for (List<PathEntity> path : daps)
        {
            Integer entryId = path.get(0).getEntryId();
            pathDao.createPath(category.getTableId(), entryId, category.getCategoryId(), treeId);
        }
    }
}
