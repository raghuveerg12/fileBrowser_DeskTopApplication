package com.desktop.app.filebrowser.helper;

import org.hibernate.SessionFactory;

import com.desktop.app.filebrowser.bean.UploadFile;
import com.desktop.app.filebrowser.utils.HibernateUtil;

public class FileUploadDAOImpl implements FileUploadDAO {
	
	SessionFactory sessionFactory=HibernateUtil.getSessionFactory();

	
	public FileUploadDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	@Override
	public void save(UploadFile uploadFile) {
		sessionFactory.getCurrentSession().save(uploadFile);		
	}

}
