package com.ez.tablebase.rest.model.dao;

import com.ez.tablebase.rest.database.CategoryEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryDaoImpl extends CategoryEntity
{
    @Autowired
    private SessionFactory sessionFactory;

}
