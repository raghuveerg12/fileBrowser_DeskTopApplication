package com.desktop.app.filebrowser.main;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

/** A TableModel to hold File[]. */
@SuppressWarnings("serial")
class FileTableModel2 extends AbstractTableModel {
	private static final  Logger LOGGER = Logger.getLogger(FileTableModel2.class.getName());

	private File[] files;
	private transient FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	private String[] columns = { "Icon", "File", "Path/name", "Size",
			"Last Modified","R","W","E","D"

	};

	FileTableModel2() {
		this(new File[0]);
	}

	FileTableModel2(File[] files) {
		this.files = files;
	}

	public Object getValueAt(int row, int column) {
		File file = files[row];
		switch (column) {
		case 0:
			return fileSystemView.getSystemIcon(file);
		case 1:
			return fileSystemView.getSystemDisplayName(file);
		case 2:
			return file.getPath();
		case 3:
			return file.length();
		case 4:
			return file.lastModified();
		case 5:
			return file.canRead();
		case 6:
			return file.canWrite();
		case 7:
			return file.canExecute();
		case 8:
			return file.isDirectory();
		case 9:
			return file.isFile();
		default:
			LOGGER.info("Logic Error in FireTableModel at getValueAt()");
		}
		return "";
	}

	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return ImageIcon.class;
		case 3:
			return Long.class;
		case 4:
			return Date.class;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return Boolean.class;
		

		}
		return String.class;
	}
@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	public int getRowCount() {
		return files.length;
	}

	public File getFile(int row) {
		return files[row];
	}

	public void setFiles(File[] files) {
		this.files = files;
		fireTableDataChanged();
	}
}
