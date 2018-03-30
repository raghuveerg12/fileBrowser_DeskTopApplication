package com.desktop.app.filebrowser.helper;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.desktop.app.filebrowser.bean.BackUpIndex;
import com.desktop.app.filebrowser.bean.BasicFolderParamsBean;
import com.desktop.app.filebrowser.bean.SaveImageAsBean;
import com.desktop.app.filebrowser.bean.UploadFile;
import com.desktop.app.filebrowser.bean.UserRegistrationBean;
import com.desktop.app.filebrowser.bean.Users;
import com.desktop.app.filebrowser.utils.HibernateUtil;
import com.google.gson.Gson;

public class StoreFileAsJson {
	private static final Logger LOGGER = Logger.getLogger(StoreFileAsJson.class
			.getName());
	
	private static final String FAILURE_MSG="failure";

	public static void storeFileInCurrentDir(BasicFolderParamsBean basic)
			throws IOException {

		File jsonFileName = null;
		String path = System.getProperty("user.dir");
		File dir = null;

		dir = new File(path);
		System.out.println("dir--"+dir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		// create file based
		jsonFileName = new File(dir.toString() + File.separatorChar
				+ "index.json");
		LOGGER.info("file name --" + basic.getFileName());
		try (BufferedWriter output = new BufferedWriter(new FileWriter(
				jsonFileName))) {

			LOGGER.info("path of the JsonFile :"+jsonFileName.getPath());

			String jsonFormat = new Gson().toJson(basic);
			output.write(jsonFormat);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("error into storefile as json :" + e);

		}
		LOGGER.info("successfully  storefile as json :" + jsonFileName.getPath());
		/**
		 * 
		 *  String message=downloadFile();
		 */
		String message = uploadFile(jsonFileName, basic.getUserName());
		LOGGER.info("Message from the UploadFile :"+ message);

	}
	@SuppressWarnings("unchecked")

	public static String uploadFile(File jsonFileName, String userName) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		
			LOGGER.info("Path of the file inside the Uploadfile folder:"+jsonFileName.getPath());
			session.beginTransaction();
			Query query = session
					.createQuery("from Users where userName = :code ");
			query.setParameter("code", "raghu");
			
			List<Users> list = query.list();
			Users user = list.get(0);
			LOGGER.info("user.getId()--" + user.getId());

			BackUpIndex bkIndex = new BackUpIndex();
			bkIndex.setUserId(user.getId());
			bkIndex.setCreatedDate(new Date(System.currentTimeMillis()));
			bkIndex.setUpdatedDate(new Date(System.currentTimeMillis()));

			if (jsonFileName.exists()) {
				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileName(jsonFileName.getName());
				byte[] bFile = new byte[(int) jsonFileName.length()];
				try (FileInputStream fos = new FileInputStream(jsonFileName)) {

					int readLines = fos.read(bFile);
					if (readLines <= 0) {
						LOGGER.info("@@@@@@@@@@@@@@@THERE IS NO DATA IN THE JSON FILE@@@@@@@@@@@@@@@@@@@ ");
					}
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.info("THERE IS NO DATA IN THE JSON FILE--EXCEPTION "+ e.toString());
					return FAILURE_MSG;
				}
				@SuppressWarnings("deprecation")
				Blob blob = Hibernate.createBlob(bFile);
				uploadFile.setUserId(user.getId());
				uploadFile.setData(blob);
				session.save(bkIndex);
				session.save(uploadFile);
				session.beginTransaction().commit();
				return "success";
			}
		
			return FAILURE_MSG;

	}

	public static String downloadFile() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			UploadFile uploadFile = new UploadFile();
			uploadFile.setId(1);
			UploadFile uFile = (UploadFile) session.load(UploadFile.class,
					uploadFile.getId());
			LOGGER.info("file name----" + uFile.getFileName());
			
			writeDataToFile(uFile.getData());
			return "success";

		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.info(e.getMessage());
			return FAILURE_MSG;
		}
		
		

	}

	private static void writeDataToFile(Blob blob) {
		try (FileOutputStream fos = new FileOutputStream("D:\\wallet_tx\\desktopFileBrowser\\target\\classes\\2018-03-19\\test.json");){
			
			 fos.write(blob.getBytes(1, (int)blob.length()));
			
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	private static String findTodaysDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();

		return dtf.format(localDate);
	}

	public static void main(String[] args) throws IOException {
		
		saveImageFileToDB();
		/*BasicFolderParamsBean basic = new BasicFolderParamsBean();
		basic.setFileName("raghu");
		Users user = new Users();
		user.setEmailId("chal@123.com");
		UserRegistrationBean ureg=new UserRegistrationBean();
		ureg.setFirstName("raghu");
		ureg.setRememberMe("YES");*/
		
	//	StoreFileAsJson.storeFileInCurrentDir(basic);
	}

	public static void storeDataToDB(Users userBean) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		try {
			
			tx.begin();
			
			userBean.setCreatedDate(new Date(System.currentTimeMillis()));
			userBean.setLastUpdated(new Date(System.currentTimeMillis()));
			session.save(userBean);
			tx.commit();
		} catch (Exception e) {
			e.getStackTrace();

			LOGGER.info("error in the save date to DB :" + e.getMessage());
			tx.rollback();
		} finally {
			session.close();
		}
	}

	public static void storeUserRemMeDetails(UserRegistrationBean userBean,
			String fileName) throws IOException {
		
		String path = System.getProperty("user.dir");
		
		System.out.println("path inside the store--"+path);
		try( ObjectOutputStream oos = new ObjectOutputStream( 
                // By using "FileOutputStream" we will 
                // Write it to a File in the file system
                // It could have been a Socket to another 
                // machine, a database, an in memory array, etc.
                new FileOutputStream(new File(path+File.separator+fileName+".ser")));) {
			
			// do the magic  
	        oos.writeObject( userBean );
	       
	       
		} catch (Exception e) {

			e.printStackTrace();

		} 
	}
	
	@SuppressWarnings("unchecked")
	public static boolean checkAlreadyInDB(String name,String selectQuery) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		tx.begin();
		try{
		 
		org.hibernate.Query query =  session.createQuery(selectQuery);
		LOGGER.info(name);
		query.setParameter("code", name);
		List<Users> list = query.list();
		
		if(list.isEmpty()){
			return true;
		}

		}catch(Exception e){
			LOGGER.info("error in the save date to DB "+e);
			tx.rollback();
			return false;
		}finally{
			session.close();
		}
		return false;
	}
	
	
	public static void saveDataToJson(String password, String displayName, String firstName,
			String mailID) {
		Users userDetails=new Users();
		userDetails.setUserName(firstName);
		userDetails.setPassword(password);
		userDetails.setDisplayName(displayName);
		userDetails.setEmailId(mailID);
		userDetails.setCreatedDate(new Date(System.currentTimeMillis()));
		userDetails.setLastUpdated(new Date(System.currentTimeMillis()));
		
		try {
			StoreFileAsJson.storeDataToDB(userDetails);
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.info("exception saveDataToJson "+e);
		}
		

		
	}
	
	public static boolean validateUserLogin(String userName, String passWord) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		try{
		
		tx.begin();
		org.hibernate.Query query =  session.createQuery("from Users where userName = :code ");
		query.setParameter("code", userName);
		@SuppressWarnings("unchecked")
		List<Users> list = query.list();
		LOGGER.info( list.get(0).getUserName());  
		Users user=list.get(0);
		if(user.getUserName().equals(userName)&&user.getPassword().equals(passWord)&&user.getActivated().equalsIgnoreCase("YES")){
			return true;
		}

		}catch(Exception e){
			LOGGER.info("error in the save date to DB "+e);
			tx.rollback();
			return false;
		}finally{
			session.close();
		}
		return false;
	}
	
	  public static  void saveImageFileToDB(){
		   String pathOne=System.getProperty("user.dir")+File.separatorChar+"images"+File.separatorChar+"fb-icon-32x32.png";
		   String pathTwo=System.getProperty("user.dir")+File.separatorChar+"images"+File.separatorChar+"fb-icon-32x32.png";
		   Session session=HibernateUtil.getSessionFactory().openSession();
		   try{		   
		   session.beginTransaction().begin();
		   SaveImageAsBean saveImage= new SaveImageAsBean();
		   byte[] imageInByte = convertToBytes(pathOne);
		   byte[] imageInByteTwo = convertToBytes(pathTwo);
		   
		   saveImage.setImageOne(imageInByte);
		   saveImage.setImageTwo(imageInByteTwo);
		   session.save(saveImage);
		   session.beginTransaction().commit();
		   }catch(Exception e){
			   e.getLocalizedMessage();
		   }finally{
			   session.close();
		   }
		   
	    	
	    }
	  

private static byte[] convertToBytes(String pathOne) throws IOException {
	byte[] imageInByte;
       BufferedImage originalImage = ImageIO.read(new File(pathOne));

	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ImageIO.write(originalImage, "png", baos);
       baos.flush();
       imageInByte = baos.toByteArray();
       baos.close();
	return imageInByte;
}

}
