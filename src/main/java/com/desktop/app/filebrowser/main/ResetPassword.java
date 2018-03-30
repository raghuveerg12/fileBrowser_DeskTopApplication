package com.desktop.app.filebrowser.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.hibernate.Query;
import org.hibernate.Session;

import com.desktop.app.filebrowser.bean.Users;
import com.desktop.app.filebrowser.utils.HibernateUtil;


public class ResetPassword extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000000L;

	private static final  Logger LOGGER = Logger.getLogger(RegisterForm.class.getName());
	private static final String FONT_LABEL = "Tahoma";

	 // Variables declaration - do not modify//GEN-BEGIN:variables
    
    private JTextField jTextField1;
    private JTextField textField;
    
    // End of variables declaration//GEN-END:variables
	public ResetPassword(){
		initComponents();
        this.setLocationRelativeTo(null);
	}

	private void initComponents() {
		 JButton jButton1;
	     JLabel jLabel2;
	     JLabel jLabel4;
	     JLabel jLabelClose;
	     JLabel jLabelMin;
	     JLabel jLabelRegister;
	     JPanel jPanel1;
	     JPanel jPanel2;

		 jPanel1 = new JPanel();
	        jLabelClose = new JLabel();
	        jLabel2 = new JLabel();
	        jLabelMin = new JLabel();
	        jPanel2 = new JPanel();
	        jLabel4 = new JLabel();
	        jTextField1 = new JTextField();
	        jButton1 = new JButton();
	        jLabelRegister = new JLabel();
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        setUndecorated(true);
	        
	        jPanel1.setBackground(new Color(248, 148, 6));
	        
	        jLabel2.setFont(new Font(FONT_LABEL, 1, 24)); // NOI18N
	        jLabel2.setForeground(new Color(255, 255, 255));
	        jLabel2.setText("Reset Password");
	        
	        jLabelClose.setFont(new Font(FONT_LABEL, 1, 24)); // NOI18N
	        jLabelClose.setForeground(new Color(255, 255, 255));
	        jLabelClose.setText("X");
	        jLabelClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        jLabelClose.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent evt) {
	                System.exit(0);
	            }
	        });
	        
	        jLabelMin.setFont(new Font(FONT_LABEL, 1, 24)); // NOI18N
	        jLabelMin.setForeground(new Color(255, 255, 255));
	        jLabelMin.setText("-");
	        jLabelMin.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        jLabelMin.addMouseListener(new MouseAdapter() {
	        	@Override
	            public void mouseClicked(MouseEvent evt) {
	            	setState(JFrame.ICONIFIED);
	            	}
	        });
	        
	        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(jLabelMin)
	                    .addGap(18, 18, 18)
	                    .addComponent(jLabelClose)
	                    .addGap(21, 21, 21))
	                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addGap(29, 29, 29)
	                        .addComponent(jLabel2)
	                        .addContainerGap(236, Short.MAX_VALUE)))
	            );
	            jPanel1Layout.setVerticalGroup(
	                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel1Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                        .addComponent(jLabelMin, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
	                        .addComponent(jLabelClose))
	                    .addContainerGap())
	                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
	                        .addContainerGap()))
	            );
	            
	            jPanel2.setBackground(new Color(153, 153, 153));
	            
	            jLabel4.setFont(new Font(FONT_LABEL, 0, 18)); // NOI18N
	            jLabel4.setForeground(new Color(0, 0, 0));
	            jLabel4.setText("Email-ID:");
	            
	            
	            jTextField1.setBackground(new Color(255, 255, 255));
	            jTextField1.setFont(new Font(FONT_LABEL, 0, 14)); // NOI18N
	            jTextField1.setForeground(new Color(51, 51, 51));
	            
	            jButton1.setBackground(new Color(34, 167, 240));
	            jButton1.setFont(new Font(FONT_LABEL, 1, 14)); // NOI18N
	            jButton1.setForeground(new Color(0, 0, 0));
	            jButton1.setText("Submit");
	            jButton1.addMouseListener(new MouseAdapter() {
	            	@Override
	            	public void mouseClicked(MouseEvent evt) {
	                    jLabelSubmitMouseClicked();
	                }
	            });
	            
	            
	            jLabelRegister.setFont(new Font(FONT_LABEL, 0, 14)); // NOI18N
	            jLabelRegister.setForeground(new Color(255, 255, 255));
	            jLabelRegister.setText("click here to create a new account");
	            jLabelRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
	            jLabelRegister.addMouseListener(new MouseAdapter() {
	            	@Override
	                public void mouseClicked(MouseEvent evt) {
	            		 RegisterForm rgf = new RegisterForm();
	            	        rgf.setVisible(true);
	            	        rgf.setIconImages(LoginForm.getImages());
	            	        rgf.pack();
	            	        rgf.setLocationRelativeTo(null);
	            	        rgf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            	       dispose();
	                }
	            });
	            
	            textField = new JTextField();
	            textField.setForeground(new Color(204, 51, 51));
	            textField.setBorder(null);
	            textField.setBackground(new Color(153, 153, 153));
	            textField.setColumns(10);
	            
	            
	            GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
	            jPanel2Layout.setHorizontalGroup(
	            	jPanel2Layout.createParallelGroup(Alignment.LEADING)
	            		.addGroup(jPanel2Layout.createSequentialGroup()
	            			.addGap(31)
	            			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
	            				.addGroup(jPanel2Layout.createSequentialGroup()
	            					.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
	            						.addGroup(Alignment.LEADING, jPanel2Layout.createSequentialGroup()
	            							.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
	            							.addPreferredGap(ComponentPlacement.RELATED)
	            							.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
	            						.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
	            					.addGap(18)
	            					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	            				.addGroup(jPanel2Layout.createSequentialGroup()
	            					.addGap(76)
	            					.addComponent(jLabelRegister)))
	            			.addContainerGap(35, Short.MAX_VALUE))
	            );
	            jPanel2Layout.setVerticalGroup(
	            	jPanel2Layout.createParallelGroup(Alignment.LEADING)
	            		.addGroup(jPanel2Layout.createSequentialGroup()
	            			.addGap(68)
	            			.addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
	            				.addComponent(jLabel4)
	            				.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	            				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	            			.addGap(18)
	            			.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
	            			.addGap(18)
	            			.addComponent(jLabelRegister)
	            			.addContainerGap(22, Short.MAX_VALUE))
	            );
	            jPanel2.setLayout(jPanel2Layout);
	            
	            GroupLayout layout = new GroupLayout(getContentPane());
	            getContentPane().setLayout(layout);
	            layout.setHorizontalGroup(
	                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            );
	            layout.setVerticalGroup(
	                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	                    .addGap(0, 0, 0)
	                    .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	            );

	            pack();
		
	}
	
	protected void jLabelSubmitMouseClicked() {
		LOGGER.info("INTO THE RESET PWD FLOW FOR SUBMIT");
		boolean validationFlag=false;
		if(!nameNEmailValidation(jTextField1.getText(),EMAIL_PATTERN)){
    		
			textField.setText("Please enter correct");
			
   		 
		}else{
			validationFlag=validationEmailId(jTextField1.getText());
			
			textField.setText("");
			
		}
		if(validationFlag){
			Object[] options = { "OK" };

		       
		    	  int choice= JOptionPane.showOptionDialog(null, 
		    			      "An Email sent   with password ", 
		    			      "Message", 
		    			      JOptionPane.YES_OPTION, 
		    			      JOptionPane.QUESTION_MESSAGE, 
		    			      null, options, options[0]);
		    	  if (choice == JOptionPane.OK_OPTION)
		          {
		            System.exit(0);
		          }
		       
			
		}else{
			
			Object[] options = { "OK" };

		       
	    	  int choice= JOptionPane.showOptionDialog(null, 
	    			      "EMail used is incorrect one,Please register to ensure correct emailID", 
	    			      "Message", 
	    			      JOptionPane.YES_OPTION, 
	    			      JOptionPane.QUESTION_MESSAGE, 
	    			      null, options, options[0]);
	    	  if (choice == JOptionPane.OK_OPTION)
	          {
	            System.exit(0);
	          }
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean validationEmailId(String emailId) {
		LOGGER.info("into the validationEmailId method");
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			Query query=session.createQuery("from Users where emailId = :code");
			query.setParameter("code", emailId);
			List<Users> list = (List<Users>)query.list();
			LOGGER.info("user.getId()--" + list.get(0).getEmailId());
			String pwd=newPassword();
			if(!list.isEmpty()){
				
				String validationMessage=sendMail(emailId,pwd,"NEW PASSWORD FOR ACTVIATION","Please find below  updated password "+ '\n' + '\n' + '\n' + "");
				if(validationMessage.equals("success")){
					for (Users users : list) {
						users.setPassword(pwd);
						session.saveOrUpdate(users);
					}
					
					session.beginTransaction().commit();
					return true;
				}
				
			}else{
				
				return false;
			}
			
			
			
			
		}catch(Exception e){
			session.beginTransaction().rollback();
			session.close();
			return false;
		}
		return false;
	}
	private String newPassword() {
		        return  UUID.randomUUID().toString();
		    
	}

	private boolean nameNEmailValidation(String text, String patternToValidate) {
		 Pattern pattern;
	      Matcher matcher;
   	 pattern = Pattern.compile(patternToValidate);
   	 matcher = pattern.matcher(text);
   	
		return matcher.matches();
	}

	

	public static void main(String[] args) {
		/* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //</editor-fold>

        /* Create and display the form */
         Runnable tastk=()->new ResetPassword().setVisible(true);
        EventQueue.invokeLater(tastk);
	}
	
	public static String sendMail(String emailId, String pwd,String subject,String body) {
		// Recipient's email ID needs to be mentioned.
		String to = emailId;// change accordingly

		// Sender's email ID needs to be mentioned
		String from = "raghuveerg@isonsoft.cz";// change accordingly
		final String username = "raghuveerg@isonsoft.cz";// change accordingly
		final String pdForEN = "wwwsdrftweetech@121swqawsawdfffc";// change
																	// accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		// Get the Session object.
		javax.mail.Session session = javax.mail.Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, pdForEN
								.substring(11, 19));
					}
				});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			message.addRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
		
			// Set To: header field of the header.

			// Set Subject: header field
			message.setSubject(subject  + emailId);

			// Now set the actual message
			message.setText(body + pwd);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {

		e.printStackTrace();
		return "failure";

		}
		return "success";
	}
	
	
    private static  final String EMAIL_PATTERN = 
		        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

}
