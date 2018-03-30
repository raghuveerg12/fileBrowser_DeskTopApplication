package com.desktop.app.filebrowser.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.desktop.app.filebrowser.bean.BasicFolderParamsBean;
import com.desktop.app.filebrowser.bean.UserRegistrationBean;
import com.desktop.app.filebrowser.helper.StoreFileAsJson;
import com.desktop.app.filebrowser.utils.AddCheckBoxToTree;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * A basic File Browser. Requires 1.6+ for the Desktop & SwingWorker classes,
 * amongst other minor things.
 * 
 * 
 * 
 * @author raghu
 * 
 * 
 */
public class FileBrowser {

	/** Title of the application */
	public static final String APP_TITLE = "Desktop File Browser";
	/** Used to open/edit/print files. */
	private Desktop desktop;
	/** Provides nice icons and names for files. */
	private FileSystemView fileSystemView;

	/** currently selected File. */
	private File currentFile;
	@SuppressWarnings("unused")
	private TreePath currentFileTreePath;

	/** Main GUI container */
	private JPanel gui;

	/** File-system tree. Built Lazily */
	private JTree tree;
	private DefaultTreeModel treeModel;

	/** Directory listing */
	private JTable table;
	private JProgressBar progressBar;
	/** Table model for File[]. */
	private FileTableModel2 fileTableModel;
	private ListSelectionListener listSelectionListener;
	private boolean cellSizesSet = false;
	private int rowIconPadding = 6;

	/* File controls. */
	private JButton openFile;
	private JButton select;
	private JButton done;

	/* File details. */
	private JLabel fileName;
	private JTextField path;
	private JLabel date;
	private JLabel size;
	/**
	 * 
	 * 
	 * private JCheckBox readable;
	private JCheckBox writable;
	private JCheckBox executable;
	
	*/
	private JRadioButton isDirectory;
	private JRadioButton isFile;

	/* GUI options/containers for new File/Directory creation. Created lazily. */
	@SuppressWarnings("unused")
	private JPanel newFilePanel;
	@SuppressWarnings("unused")
	private JRadioButton newTypeFile;
	@SuppressWarnings("unused")
	private JTextField name;
	List<Map<String, String>> listMapFiles = new LinkedList<Map<String, String>>();
	
	public static final ImageIcon ICON_COMPUTER =  new ImageIcon("");
	public static final ImageIcon ICON_DISK =  new ImageIcon("defaults1.png");
	public static final ImageIcon ICON_FOLDER =   new ImageIcon("fol_orig.png");
	public static final ImageIcon ICON_EXPANDEDFOLDER =  new ImageIcon("folder_open.png");
	AddCheckBoxToTree addCh = new AddCheckBoxToTree();
	private AddCheckBoxToTree.CheckTreeManager checkTreeManager;
	protected TreePath mClickedPath;

	public Container getGui(String name) {
		if (gui == null) {
			gui = new JPanel(new BorderLayout(3, 3));
			gui.setBorder(new EmptyBorder(5, 5, 5, 5));

			fileSystemView = FileSystemView.getFileSystemView();
			desktop = Desktop.getDesktop();

			JPanel detailView = new JPanel(new BorderLayout(3, 3));

			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoCreateRowSorter(true);
			table.setShowVerticalLines(false);

			
			JScrollPane tableScroll = new JScrollPane(table);
			Dimension d = tableScroll.getPreferredSize();
			tableScroll.setPreferredSize(new Dimension((int) d.getWidth(),
					(int) d.getHeight() / 2));
			detailView.add(tableScroll, BorderLayout.CENTER);

			// the File tree
			DefaultMutableTreeNode top = new DefaultMutableTreeNode(
		            new IconData2(ICON_COMPUTER, null, "Computer"));
			DefaultMutableTreeNode node;
			
		    File[] roots = File.listRoots();
		    for (int k=0; k<roots.length; k++)
		    {
		        node = new DefaultMutableTreeNode(new IconData2(ICON_DISK, null, new FileNode2(roots[k])));
		        top.add(node);
		        node.add(new DefaultMutableTreeNode( true ));
		    }

		    treeModel = new DefaultTreeModel(top);
		   
			tree = new JTree(treeModel);
		    tree.putClientProperty("JTree.lineStyle", "Angled");
			tree.setCellRenderer(new IconCellRenderer2());
			tree.expandRow(0);
			tree.addTreeExpansionListener(new  DirExpansionListener());

		    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); 
		  
		    tree.setShowsRootHandles(true); 
		    tree.setEditable(false);


		    checkTreeManager = addCh.new CheckTreeManager(tree, null);
			JScrollPane treeScroll = new JScrollPane();
			treeScroll.setViewportView(tree);
			// as per trashgod tip
			tree.setVisibleRowCount(15);

			Dimension preferredSize = treeScroll.getPreferredSize();
			Dimension widePreferred = new Dimension(200,
					(int) preferredSize.getHeight());
			treeScroll.setPreferredSize(widePreferred);

			// details for a File
			JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
			fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

			JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
			fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

			JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
			fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

			fileDetailsLabels.add(new JLabel("File", JLabel.TRAILING));
			fileName = new JLabel();
			fileDetailsValues.add(fileName);
			fileDetailsLabels.add(new JLabel("Path/name", JLabel.TRAILING));
			path = new JTextField(5);
			path.setEditable(false);
			fileDetailsValues.add(path);
			fileDetailsLabels.add(new JLabel("Last Modified", JLabel.TRAILING));
			date = new JLabel();
			fileDetailsValues.add(date);
			fileDetailsLabels.add(new JLabel("File size", JLabel.TRAILING));
			size = new JLabel();
			fileDetailsValues.add(size);
			fileDetailsLabels.add(new JLabel("Type", JLabel.TRAILING));

			JPanel flags = new JPanel(new FlowLayout(FlowLayout.LEADING, 4, 0));

			isDirectory = new JRadioButton("Directory");
			flags.add(isDirectory);

			isFile = new JRadioButton("File");
			flags.add(isFile);
			fileDetailsValues.add(flags);

			JToolBar toolBar = new JToolBar();
			// mnemonics stop working in a floated toolbar
			toolBar.setFloatable(false);

			JButton locateFile = new JButton("Locate");
			locateFile.setMnemonic('l');

			locateFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						
						desktop.open(currentFile.getParentFile());
					} catch (Exception t) {
						showThrowable(t);
					}
					gui.repaint();
				}
			});
			// toolBar.add(locateFile);

			openFile = new JButton("Open");
			openFile.setMnemonic('o');

			openFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						System.out.println("Open: " + currentFile);
						desktop.open(currentFile);
					} catch (Throwable t) {
						showThrowable(t);
					}
					gui.repaint();
				}
			});
			// toolBar.add(openFile);

			select = new JButton("Select");
			select.setMnemonic('S');
			select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						TreePath[] treePaths=checkTreeManager.getSelectionModel().getSelectionPaths();
						for (int i = 0; i < treePaths.length; i++) {
							showProgessForFiles(new File( createFilePath(treePaths[i])),name);

						}


					} catch (Exception t) {
						showThrowable(t);
					}
				}
			});
			toolBar.add(select);

			done = new JButton("Logout");
			done.setMnemonic('L');
			done.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					UserRegistrationBean userRegbean=new UserRegistrationBean();
					userRegbean.setRememberMe("NO");
					userRegbean.setFirstName(name);
					

					try {
						String rem="Remember";
						StoreFileAsJson.storeUserRemMeDetails(userRegbean, rem);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				System.exit(0);
					
				}
			});
			toolBar.add(done);
/**
 * 
 * // Check the actions are supported on this platform!
			// openFile.setEnabled(desktop.isSupported(Desktop.Action.OPEN));
			// editFile.setEnabled(desktop.isSupported(Desktop.Action.EDIT));
 * 
 */
			
			select.setEnabled(desktop.isSupported(Desktop.Action.BROWSE));

			int count = fileDetailsLabels.getComponentCount();
			for (int ii = 0; ii < count; ii++) {
				fileDetailsLabels.getComponent(ii).setEnabled(false);
			}

			count = flags.getComponentCount();
			for (int ii = 0; ii < count; ii++) {
				flags.getComponent(ii).setEnabled(false);
			}

			JPanel fileView = new JPanel(new BorderLayout(3, 3));
			progressBar = new JProgressBar();

			fileView.add(progressBar, BorderLayout.PAGE_END);
			progressBar.setVisible(false);

			fileView.add(toolBar, BorderLayout.NORTH);
			fileView.add(fileMainDetails, BorderLayout.CENTER);

			detailView.add(fileView, BorderLayout.SOUTH);

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					treeScroll, detailView);
			gui.add(splitPane, BorderLayout.CENTER);

		}
		return gui;
	}
	
	DefaultMutableTreeNode getTreeNode(TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	FileNode2 getFileNode(DefaultMutableTreeNode node) {
		if (node == null)
			return null;
		Object obj = node.getUserObject();
		if (obj instanceof IconData2)
			obj = ((IconData2) obj).getObject();
		if (obj instanceof FileNode2)
			return (FileNode2) obj;
		else
			return null;
	}

	public AddCheckBoxToTree.CheckTreeManager getCheckTreeManager() {
		return checkTreeManager;
	}

	protected synchronized void  convertToJsonFile(File currentFile2,String userName) throws IOException {
		BasicFolderParamsBean basicFolderParamBean = new BasicFolderParamsBean();

		System.out.println("into convertToJsonFile--"+currentFile2);
		basicFolderParamBean = someParameterForFile(currentFile2,
				basicFolderParamBean);
basicFolderParamBean.setUserName(userName);
		StoreFileAsJson.storeFileInCurrentDir(basicFolderParamBean);
	}

	public BasicFolderParamsBean someParameterForFile(File file2,
			BasicFolderParamsBean basicFolderParamBean)  {
		Path file = file2.toPath();
		try{
		BasicFileAttributes attr = Files.readAttributes(file,
				BasicFileAttributes.class);

		basicFolderParamBean.setFileLastModified(attr.lastModifiedTime());
		basicFolderParamBean.setFileLastOpened(attr.lastAccessTime());
		basicFolderParamBean.setFileCreationDate(attr.creationTime());
		basicFolderParamBean.setFileName(file2.getName());
		basicFolderParamBean.setFileSize(convertBytesToGB(file2.length()));
		basicFolderParamBean.setFilePath(file2.getAbsolutePath());
		basicFolderParamBean.setFreeSpaceOnDrive(convertBytesToGB(file2
				.getTotalSpace()));
		if(file2.isDirectory()){
			
			basicFolderParamBean
			.setListOfParamsForSubFiles(findSubFilesAndDirectoryInMap(
					file2, basicFolderParamBean));	
		}
		
		}catch(IOException e){
			e.printStackTrace();
		}
		return basicFolderParamBean;
	}

	public List<Map<String, String>> findSubFilesAndDirectoryInMap(File file2,
			BasicFolderParamsBean basicFolderParamBean) throws IOException {
		
		for (File fileInside : file2.listFiles()) {

			if (fileInside.isDirectory() && !fileInside.isHidden()) {

				findSubFilesAndDirectoryInMap(fileInside, basicFolderParamBean);
			} else {
				listMapFiles.add(someParameterForSubFile(fileInside,
						basicFolderParamBean));
			}

		}
		

		return listMapFiles;
		
		
	}

	public Map<String, String> someParameterForSubFile(File file2,
			BasicFolderParamsBean basicFolderParamBean) throws IOException {
		Path file = file2.toPath();
		Map<String, String> listMap = new HashMap<String, String>();

		BasicFileAttributes attr = Files.readAttributes(file,
				BasicFileAttributes.class);

		listMap.put("fileName", file2.getName());
		listMap.put("LastOpened", attr.lastAccessTime().toString());
		listMap.put("AbsolutePath", file2.getAbsolutePath());
		listMap.put("creationTime", attr.creationTime().toString());
		listMap.put("lastModifiedTime", attr.lastModifiedTime().toString());

		return listMap;
	}

	public static String convertBytesToGB(Long bytes) {
		Long bytesConverted = (bytes / (1024 * 1024 * 1024));
		String convertedToGb = bytesConverted.toString() + "-GB";
		if (bytesConverted <= 0) {
			bytesConverted = (bytes / (1024 * 1024));
			convertedToGb = bytesConverted.toString() + "-MB";
		}
		return convertedToGb;

	}

	private void showProgessForFiles(File currentFile2,String userName) {
		/**
		 * Add the files that are contained within the directory of this node.
		 * Thanks to Hovercraft Full Of Eels for the SwingWorker fix.
		 */
		tree.setEnabled(true);
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);

		SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
			@Override
			public Void doInBackground() throws IOException {
				convertToJsonFile(currentFile2,userName);

				return null;
			}

			@Override
			protected void done() {
				progressBar.setIndeterminate(false);
				progressBar.setVisible(false);
				done.setEnabled(true);

			}
		};
		worker.execute();

	}

	public void showRootFile() {
		// ensure the main files are displayed
		tree.setSelectionInterval(0, 0);
	}
	
	public static String createFilePath(TreePath treePath) {
	    StringBuilder sb = new StringBuilder();
	    Object[] nodes = treePath.getPath();
	    
	    for(int i=1;i<nodes.length;i++) {
	    	if(i==1){
	    		sb.append(nodes[i].toString()); 
	    	}else{
	    		
		        sb.append(nodes[i].toString());
		        if(i<nodes.length-1){
		        	sb.append(File.separatorChar);
		        }

	    	}

	    	
	    } 
	    System.out.println(sb.toString());
	    return sb.toString();
	}

	@SuppressWarnings("unused")
	private TreePath findTreePath(File find) {
		for (int ii = 0; ii < tree.getRowCount(); ii++) {
			TreePath treePath = tree.getPathForRow(ii);
			Object object = treePath.getLastPathComponent();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
			File nodeFile = (File) node.getUserObject();

			if (nodeFile == find) {
				return treePath;
			}
		}
		// not found!
		return null;
	}

	
	@SuppressWarnings("unused")
	private void showErrorMessage(String errorMessage, String errorTitle) {
		JOptionPane.showMessageDialog(gui, errorMessage, errorTitle,
				JOptionPane.ERROR_MESSAGE);
	}

	private void showThrowable(Throwable t) {
		JOptionPane.showMessageDialog(gui, t.toString(), t.getMessage(),
				JOptionPane.ERROR_MESSAGE);
		gui.repaint();
	}

	/** Update the table on the EDT */
	private void setTableData(final File[] files) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (fileTableModel == null) {
					fileTableModel = new FileTableModel2();
					table.setModel(fileTableModel);
				}
				table.getSelectionModel().removeListSelectionListener(
						listSelectionListener);
				fileTableModel.setFiles(files);
				table.getSelectionModel().addListSelectionListener(
						listSelectionListener);
				if (!cellSizesSet) {
					Icon icon = fileSystemView.getSystemIcon(files[0]);

					// size adjustment to better account for icons
					table.setRowHeight(icon.getIconHeight() + rowIconPadding);

					setColumnWidth(0, -1);
					setColumnWidth(3, 60);
					table.getColumnModel().getColumn(3).setMaxWidth(120);
					setColumnWidth(4, -1);
					
					  setColumnWidth(5,-1); 
					  setColumnWidth(6,-1);
					  setColumnWidth(7,-1); 
					  setColumnWidth(8,-1);
					 

					cellSizesSet = true;
				}
			}
		});
	}

	private void setColumnWidth(int column, int width) {
		TableColumn tableColumn = table.getColumnModel().getColumn(column);
		if (width < 0) {
			// use the preferred width of the header..
			JLabel label = new JLabel((String) tableColumn.getHeaderValue());
			Dimension preferred = label.getPreferredSize();
			// altered 10->14 as per camickr comment.
			width = (int) preferred.getWidth() + 14;
		}
		tableColumn.setPreferredWidth(width);
		tableColumn.setMaxWidth(width);
		tableColumn.setMinWidth(width);
	}

	/**
	 * Add the files that are contained within the directory of this node.
	 * Thanks to Hovercraft Full Of Eels for the SwingWorker fix.
	 */
	@SuppressWarnings("unused")
	private void showChildren(final DefaultMutableTreeNode node) {
		tree.setEnabled(false);
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);

		SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
			@Override
			public Void doInBackground() {
				File file = (File) node.getUserObject();
				if (file.isDirectory()) {
					File[] files = fileSystemView.getFiles(file, true); // !!
					if (node.isLeaf()) {
						for (File child : files) {
							if (child.isDirectory()) {
								publish(child);
							}
						}
					}
					setTableData(files);
				}
				return null;
			}

			@Override
			protected void process(List<File> chunks) {
				for (File child : chunks) {
					node.add(new DefaultMutableTreeNode(child));
				}
			}

			@Override
			protected void done() {
				progressBar.setIndeterminate(false);
				progressBar.setVisible(false);
				tree.setEnabled(true);
			}
		};
		worker.execute();
	}

	/** Update the File details view with the details of this File. 
	 * @param treePath */
	private void setFileDetails(File file, TreePath treePath) {
		System.out.println(file.getName());
		currentFile = file;
		currentFileTreePath=treePath;
		Icon icon = fileSystemView.getSystemIcon(file);
		fileName.setIcon(icon);
		fileName.setText(fileSystemView.getSystemDisplayName(file));
		path.setText(file.getPath());
		date.setText(new Date(file.lastModified()).toString());
		size.setText(file.length() + " bytes");
		
		/**
		 * 
		 * 
		 * /* readable.setSelected(file.canRead());
		 writable.setSelected(file.canWrite());
		 executable.setSelected(file.canExecute());
		 isDirectory.setSelected(file.isDirectory());
		 
		 * 
		 */


		JFrame f = (JFrame) gui.getTopLevelAncestor();
		if (f != null) {
			f.setTitle(APP_TITLE + " :: "
					+ fileSystemView.getSystemDisplayName(file));
		}

		gui.repaint();
	}
	
	public class DirExpansionListener implements TreeExpansionListener
	{
		
	    public void treeExpanded(TreeExpansionEvent event)
	    {
	    	
	        final DefaultMutableTreeNode node = getTreeNode(
	                event.getPath());
	        
	        final FileNode2 fnode = getFileNode(node);
	       setFileDetails(fnode.mFile,event.getPath());
	       setTableData(fnode.listFiles());
	        Thread runner = new Thread() 
	        {
	        	@Override
	            public void run() 
	            {
	                if (fnode != null && fnode.expand(node)) 
	                {
	                    Runnable runnable =  ()->treeModel.reload(node);
	                    
	                    

	                    SwingUtilities.invokeLater(runnable);
	                }
	            }
	        };
	        runner.start();
	    }

	    public void treeCollapsed(TreeExpansionEvent event) {
	    	
	    	/**
	    	 * 
	    	 * 
	    	 * 
	    	 */
	    	
	    }
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					// Significantly improves the look of the output in
					// terms of the file names returned by FileSystemView!
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception weTried) {
					/**
					 * 
					 * 
					 */
				}
			
	            		if(getNameFromJson()!=null){
	            			
	            		JFrame f = new JFrame(FileBrowser.APP_TITLE);
	                	FileBrowser fileBrowser = new FileBrowser();
	             		f.setContentPane(fileBrowser.getGui(getNameFromJson()));
	             		f.setVisible(true);

	             		
	             		try {
	            			
	            			ArrayList<Image> images = new ArrayList<>();
	            			images.add(ImageIO.read(new FileInputStream(System.getProperty("user.dir")+File.separatorChar+"images"+File.separatorChar+"fb-icon-32x32.png")));
	            			images.add(ImageIO.read(new FileInputStream(System.getProperty("user.dir")+File.separatorChar+"images"+File.separatorChar+"fb-icon-16x16.png")));
	            			f.setIconImages(images);
	            		} catch (Exception weTried) {
	            			weTried.getStackTrace();
	            		}
	             		f.pack();
	             		f.setMinimumSize(f.getSize());
	             		f.setVisible(true);
	                    f.setLocationRelativeTo(null);
	                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                  
	             		fileBrowser.showRootFile();
	             		
	            	}
			}
			
		});
	}

	protected static String getNameFromJson() {
		String user;
		try {
			String path = System.getProperty("user.dir");
			System.out.println("inside the getNameFromJson method"+path); 
			UserRegistrationBean staff;
			
			Gson gson = new Gson();
			
					
					
				staff = gson.fromJson(new FileReader(path+File.separatorChar+"Remember.json"), UserRegistrationBean.class);
				user=	staff.getFirstName();
				System.out.println("inside the getNameFromJson user name"+user); 

			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				e.printStackTrace();;
				return null;
			}
    	
		return user;
	}
}




   

