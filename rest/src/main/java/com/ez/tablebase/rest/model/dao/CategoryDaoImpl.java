package com.ez.tablebase.rest.model.dao;

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.CategoryEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

public class CategoryDaoImpl implements CategoryDao
{
    @Override
    public CategoryEntity createCategory(Integer tableId, String name, Integer parentId, Integer treeId)
    {
        Session session = getCurrentSession();
        CategoryEntity category = new CategoryEntity();
        category.setTableId(tableId);
        category.setName(name);
        category.setParentId(parentId);
        category.setTreeId(treeId);

        Integer categoryId = (Integer) session.save(category);
        category.setCategoryId(categoryId);

        return category;
    }

    @Override
    public CategoryEntity getCategory(Integer categoryId)
    {
        return getCurrentSession().get(CategoryEntity.class, categoryId);
    }

    @Override
    public CategoryEntity getRootCategoryByTreeId(Integer tableId, Integer treeId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create new query
        CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);

        // Build query
        Root<CategoryEntity> root = query.from(CategoryEntity.class);
        query.select(root).where(builder.equal(root.get("table_id"), tableId), builder.equal(root.get("parent_id"), null), builder.equal(root.get("tree_id"), treeId));

        // Get the result
        CategoryEntity result = session.createQuery(query).getSingleResult();
        transaction.commit();

        return result;
    }

    @Override
    public List<Integer> getTreeIds(Integer tableId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Create Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create new query
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);

        // Build query
        Root<CategoryEntity> root = query.from(CategoryEntity.class);
        query.select(root.get("tree_id")).where(builder.equal(root.get("table_id"), tableId));
        query.groupBy(root.get("tree_id")).orderBy(builder.asc(root.get("tree_id")));

        List<Integer> treeIds = session.createQuery(query).getResultList();
        transaction.commit();

        return treeIds;
    }

    @Override
    public List<Integer> findPathToCategory(CategoryEntity category)
    {
        List<Integer> pathToCategory = new LinkedList<>();
        List<List<CategoryEntity>> pathList = buildPathListForTree(category.getTableId(), category.getTreeId());

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

    @Override
    public List<CategoryEntity> findCategoryChildren(Integer categoryId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // Creating criteria builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Creating query
        CriteriaQuery<CategoryEntity> query = builder.createQuery(CategoryEntity.class);

        Root<CategoryEntity> parentRoot = query.from(CategoryEntity.class);
        Root<CategoryEntity> childRoot = query.from(CategoryEntity.class);
        query.select(childRoot).where(builder.equal(parentRoot.get("category_id"), childRoot.get("parent_id")), builder.equal(parentRoot.get("category_id"), categoryId));

        List<CategoryEntity> children = session.createQuery(query).getResultList();
        transaction.commit();

        // Remove any null elements (this will indicate that the given categoryId is a leaf node)
        children.removeIf(Objects::isNull);
        return children;
    }

    @Override
    public List findAllCategoryChildren(Integer categoryId)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String sql = "SELECT table_id, category_id, tree_id, name, parent_id FROM " +
                "(SELECT * FROM categories ORDER BY parent_id, category_id) categories_sorted, " +
                "(SELECT @pv := :categoryId) initialisation WHERE find_in_set(parent_id, @pv) AND length(@pv := concat(@pv, ',', category_id))";

        Query query = session.createNativeQuery(sql, CategoryEntity.class);
        query.setParameter("categoryId", categoryId);

        List allChildren = query.getResultList();
        transaction.commit();

        return allChildren;
    }

    @Override
    public void updateCategoryParent(Integer categoryId, Integer parentId)
    {
        Session session = getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        CategoryEntity category = session.load(CategoryEntity.class, categoryId);
        tx1.commit();

        category.setParentId(parentId);
        Transaction tx2 = session.beginTransaction();
        session.update(category);
        tx2.commit();
    }

    @Override
    public void updateCategoryLabel(Integer categoryId, String name)
    {
        Session session = getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        CategoryEntity category = session.load(CategoryEntity.class, categoryId);
        tx1.commit();

        category.setName(name);
        Transaction tx2 = session.beginTransaction();
        session.update(category);
        tx2.commit();
    }

    /**
     * This is a wrapper function for a recursive function call that will build a complete list of paths
     * for the specified category tree
     *
     * @param tableId The identifier of the table of interest
     * @param treeId The identifier for the category tree of which we want to generate a complete path list
     * @return A List of unique paths in the specified category tree
     */
    @Override
    public List<List<CategoryEntity>> buildPathListForTree(Integer tableId, Integer treeId)
    {
        CategoryEntity rootNode = getRootCategoryByTreeId(tableId, treeId);
        if(rootNode == null)
            return new LinkedList<>();
        else
            return buildPathList(rootNode);
    }

    private List<List<CategoryEntity>> buildPathList(CategoryEntity node)
    {
        List<List<CategoryEntity>> retLists = new LinkedList<>();

        List<CategoryEntity> children = findCategoryChildren(node.getCategoryId());
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
                List<List<CategoryEntity>> nodeLists = buildPathList(child);
                for (List<CategoryEntity> nodeList : nodeLists)
                {
                    nodeList.add(0, node);
                    retLists.add(nodeList);
                }
            }
        }

        return retLists;
    }

    @Override
    public Map<Integer, List<CategoryEntity>> buildPathMapForTree(Integer tableId, Integer treeId)
    {
        Map<Integer, List<CategoryEntity>> treeMap = new HashMap<>();
        List<List<CategoryEntity>> paths = buildPathListForTree(tableId, treeId);

        for (List<CategoryEntity> path : paths)
            treeMap.put(path.get(path.size() - 1).getCategoryId(), path);

        return treeMap;
    }

    /**
     * This wrapper function will create a list of path combinations for the given list of treeIds
     *
     * @param tableId The table of interest
     * @param treeIds The list of treeIds to find all path combinations
     * @return A list of resulting path combinations
     */
    @Override
    public List<List<CategoryEntity>> findPathCombinations(Integer tableId, List<Integer> treeIds)
    {
        List<List<List<CategoryEntity>>> treePaths = new LinkedList<>();
        for (Integer treeId : treeIds)
            treePaths.add(buildPathListForTree(tableId, treeId));

        List<List<CategoryEntity>> paths = new ArrayList<>();
        findCombinations(treePaths, paths, 0, new LinkedList<>());
        return null;
    }

    private void findCombinations(List<List<List<CategoryEntity>>> paths, List<List<CategoryEntity>> res, int d, List<CategoryEntity> current)
    {
        // If depth equals number of original collections, final reached, add and return
        if (d == paths.size()) {
            res.add(current);
            return;
        }

        // Iterate from current collection and copy 'current' element N times, one for each element
        List<List<CategoryEntity>> currentCollection = paths.get(d);
        for (List<CategoryEntity> element : currentCollection) {
            List<CategoryEntity> copy = new LinkedList<>(current);
            copy.addAll(element);

            findCombinations(paths, res, d + 1, copy);
        }
    }

    @Override
    public void deleteCategory(CategoryEntity category)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.delete(category);

        transaction.commit();
    }

    @Override
    public void deleteCategoryList(List<CategoryEntity> categories)
    {
        Session session = getCurrentSession();
        Transaction transaction = session.beginTransaction();

        for(CategoryEntity category : categories)
            session.delete(category);

        transaction.commit();
    }

    private Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
