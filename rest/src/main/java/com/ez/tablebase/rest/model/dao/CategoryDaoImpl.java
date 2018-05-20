package com.ez.tablebase.rest.model.dao;

import com.ez.tablebase.rest.HibernateUtil;
import com.ez.tablebase.rest.database.CategoryEntity;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

public class CategoryDaoImpl implements CategoryDao
{
    @Override
    public CategoryEntity createCategory(Integer tableId, String name, Integer parentId, Integer treeId)
    {
        return null;
    }

    @Override
    public CategoryEntity getCategory(Integer categoryId)
    {
        return null;
    }

    @Override
    public CategoryEntity getRootCategoryByTreeId(Integer tableId, Integer treeId)
    {
        return null;
    }

    @Override
    public List<Integer> getTreeIds(Integer tableId)
    {
        return null;
    }

    @Override
    public List<CategoryEntity> findPathToCategory(Integer tableId, Integer categoryId)
    {
        return null;
    }

    @Override
    public List<CategoryEntity> findCategoryChildren(Integer categoryId)
    {
        return null;
    }

    @Override
    public List<CategoryEntity> findAllCategoryChildren(Integer categoryId)
    {
        return null;
    }

    @Override
    public void updateCategoryParent(Integer categoryId, Integer parentId)
    {

    }

    @Override
    public void updateCategoryLabel(Integer categoryId, String name)
    {

    }

    @Override
    public List<List<CategoryEntity>> buildPathListForTree(Integer tableId, Integer treeId)
    {
        return null;
    }

    @Override
    public Map<Integer, List<CategoryEntity>> buildPathMapForTree(Integer tableId, Integer treeId)
    {
        return null;
    }

    @Override
    public List<List<CategoryEntity>> findPathCombinations(Integer tableId, List<Integer> treeIds)
    {
        return null;
    }

    @Override
    public CategoryEntity duplicateCategory(CategoryEntity selectedCategory)
    {
        return null;
    }

    @Override
    public void deleteCategory(CategoryEntity category)
    {

    }

    @Override
    public void deleteCategoryList(List<CategoryEntity> categories)
    {

    }

    public Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
}
