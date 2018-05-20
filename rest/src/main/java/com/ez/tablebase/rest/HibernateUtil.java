package com.ez.tablebase.rest;
/*
 * Created by ErikZ on 18/05/2018.
 */

import com.ez.tablebase.rest.database.CategoryEntity;
import com.ez.tablebase.rest.database.EntryEntity;
import com.ez.tablebase.rest.database.PathEntity;
import com.ez.tablebase.rest.database.TableEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    public static SessionFactory getSessionFactory()
    {
        if(sessionFactory == null)
        {
            try
            {
                // Create the SessionFactory from hibernate.cfg.xml
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(TableEntity.class);
                configuration.addAnnotatedClass(CategoryEntity.class);
                configuration.addAnnotatedClass(PathEntity.class);
                configuration.addAnnotatedClass(EntryEntity.class);

                registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(registry);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                if (registry != null)
                    StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    public static void shutdown()
    {
        if(registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }
}
