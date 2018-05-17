package com.ez.tablebase.rest.model;

import com.ez.tablebase.rest.database.CategoryDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Category extends CategoryDao
{
    @Autowired
    private SessionFactory sessionFactory;

    public static Category buildModel(CategoryDao entity)
    {
        Category model = new Category();

        model.setTableId(entity.getTableId());
        model.setCategoryId(entity.getCategoryId());
        model.setName(entity.getName());
        model.setParentId(entity.getParentId());
        return model;
    }


}
