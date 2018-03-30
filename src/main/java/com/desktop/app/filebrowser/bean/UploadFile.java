package com.desktop.app.filebrowser.bean;
import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
 
@Entity
@Table(name = "FILES_UPLOAD")
public class UploadFile implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1200000L;
	private long id;
    private String fileName;
    private Blob  data;
    private int userId;
 
    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Id
    @GeneratedValue
    @Column(name = "FILE_ID")
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    @Column(name = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    @Column(name = "FILE_DATA" ,length=2000000)
    @Lob
    public Blob  getData() {
        return data;
    }
 
    public void setData(Blob data) {
        this.data = data;
    }
}
