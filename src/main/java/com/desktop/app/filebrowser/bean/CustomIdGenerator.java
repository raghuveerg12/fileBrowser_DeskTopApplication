package com.desktop.app.filebrowser.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.desktop.app.filebrowser.utils.HibernateUtil;

public class CustomIdGenerator implements IdentifierGenerator {
	Logger log=Logger.getLogger(CustomIdGenerator.class.getName());
    @SuppressWarnings("unchecked")
	public int generateCustId() {
    	try{
    	List<Users> listOfUsers ;
    	int userID = 0;
    	log.info("int the generateCustId method");
    	Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
       listOfUsers =  session.createQuery("SELECT count(id) FROM Users").list();
      if(!listOfUsers.isEmpty()){
    	  
    	  log.info("into NON EMPTY Condition");
    	for (Users users : listOfUsers) {
			userID=users.getId();
		}

      }
  	  log.info("OUTSIDE THE LOOPS---"+userID);
    	}catch(Exception e){
    		log.info("into exception"+e.getMessage());
    	}
        return 1000+1;
    }
    @Override
    public Serializable generate(SessionImplementor si, Object o) throws HibernateException {
        
        
        return   this.generateCustId() ;
    }
}
