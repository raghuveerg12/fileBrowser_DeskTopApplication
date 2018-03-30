package com.desktop.app.filebrowser.bean;

import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Map;

public class BasicFolderParamsBean {
	
	private String fileName;
	private String fileSize;
	private FileTime fileCreationDate;
	private FileTime fileLastModified;
	private FileTime fileLastOpened;
	private String fileExtension;
	private String filePath;
	private String freeSpaceOnDrive;
	private List<Map<String, String>> listOfParamsForSubFiles;
	private String userName;
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Map<String, String>> getListOfParamsForSubFiles() {
		return listOfParamsForSubFiles;
	}
	public void setListOfParamsForSubFiles(
			List<Map<String, String>> listOfParamsForSubFiles) {
		this.listOfParamsForSubFiles = listOfParamsForSubFiles;
	}
	public FileTime getFileCreationDate() {
		return fileCreationDate;
	}
	public void setFileCreationDate(FileTime fileTime) {
		this.fileCreationDate = fileTime;
	}
	public FileTime getFileLastModified() {
		return fileLastModified;
	}
	public void setFileLastModified(FileTime fileTime) {
		this.fileLastModified = fileTime;
	}
	public FileTime getFileLastOpened() {
		return fileLastOpened;
	}
	public void setFileLastOpened(FileTime fileTime) {
		this.fileLastOpened = fileTime;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFreeSpaceOnDrive() {
		return freeSpaceOnDrive;
	}
	public void setFreeSpaceOnDrive(String freeSpaceOnDrive) {
		this.freeSpaceOnDrive = freeSpaceOnDrive;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	

}

