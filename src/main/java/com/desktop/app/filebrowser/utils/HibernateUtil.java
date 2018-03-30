package com.desktop.app.filebrowser.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.desktop.app.filebrowser.bean.BackUpIndex;
import com.desktop.app.filebrowser.bean.SaveImageAsBean;
import com.desktop.app.filebrowser.bean.UploadFile;
import com.desktop.app.filebrowser.bean.Users;

public class HibernateUtil {
	private HibernateUtil(){
		
	}
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static final String resourcess="hiber.cfg.xml";
    private static SessionFactory buildSessionFactory() {
        try {
        	AnnotationConfiguration cfg=new AnnotationConfiguration();
        	cfg.setProperty("hibernate.connection.driver_class","com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	cfg.setProperty("hibernate.connection.password","Isonsoft1234");
        	cfg.setProperty("hibernate.connection.url", "jdbc:sqlserver://isondb1.database.windows.net:1433;databaseName=ISONBackup");
        	cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        	cfg.setProperty("hibernate.connection.username", "AdministratorDB");
        	cfg.setProperty("hibernate.default_schema", "dbo");
        	cfg.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");
        	cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        	
        	cfg.setProperty("show_sql", "true");
        	cfg.addAnnotatedClass(Users.class);
        	cfg.addAnnotatedClass(UploadFile.class);
        	cfg.addAnnotatedClass(BackUpIndex.class);
        	cfg.addAnnotatedClass(SaveImageAsBean.class);
        	
       
       
           
      
                  
        	
            // Create the SessionFactory from hibernate.cfg.xml
            return  cfg.buildSessionFactory();

        }
        catch (Exception ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }

}