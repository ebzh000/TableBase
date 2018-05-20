package com.ez.tablebase.rest;
/*
 * Created by ErikZ on 18/05/2018.
 */

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
                // Create registry
                registry = new StandardServiceRegistryBuilder().configure().build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create Session Factory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
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
