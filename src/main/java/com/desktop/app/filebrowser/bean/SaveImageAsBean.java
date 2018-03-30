package com.desktop.app.filebrowser.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ImageTables")
public class SaveImageAsBean {
	@Column(name = "imageOne" ,length=2000000)
	private byte[] imageOne;
	@Column(name = "imageTwo" ,length=2000000)
	private byte[] imageTwo;
	
	@Id
    @GeneratedValue
	private int id;

	public byte[] getImageOne() {
		return imageOne;
	}

	public void setImageOne(byte[] imageOne) {
		this.imageOne = imageOne;
	}

	public byte[] getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(byte[] imageTwo) {
		this.imageTwo = imageTwo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
