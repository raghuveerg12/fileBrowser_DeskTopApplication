package com.desktop.app.filebrowser.main;

import java.io.File;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileNode2 {
protected File mFile;
private static final File[] NO_FILES = {};

public FileNode2(File file)
{
    mFile = file;
}

public File getFile() 
{ 
    return mFile;
}

public String toString() 
{ 
    return mFile.getName().length() > 0 ? mFile.getName() : 
        mFile.getPath();
}

public boolean expand(DefaultMutableTreeNode parent){
    DefaultMutableTreeNode flag = (DefaultMutableTreeNode)parent.getFirstChild();
    if (flag==null)    // No flag
        return false;
    Object obj = flag.getUserObject();
    if (!(obj instanceof Boolean))
        return false;      // Already expanded

    parent.removeAllChildren();  // Remove Flag

    File[] files = listFiles();
    
    if (files == null)
        return true;

    Vector<FileNode2> v = new Vector<>();

    for (int k=0; k<files.length; k++){
        File f = files[k];
      /**
       * 
       * if (!(f.isDirectory()))
            continue;
       * 
       */

        FileNode2 newNode = new FileNode2(f);

        boolean isAdded = false;
        for (int i=0; i<v.size(); i++)
        {
            FileNode2 nd = v.elementAt(i);
            if (newNode.compareTo(nd) < 0)
            {
                v.insertElementAt(newNode, i);
                isAdded = true;
                break;
            }
        }
        if (!isAdded)
            v.addElement(newNode);
    }

    for (int i=0; i<v.size(); i++){
        FileNode2 nd = v.elementAt(i);
        IconData2 idata = new IconData2(FileBrowser.ICON_FOLDER, FileBrowser.ICON_EXPANDEDFOLDER, nd);
        DefaultMutableTreeNode node = new 
                DefaultMutableTreeNode(idata);
        parent.add(node);

        if (nd.hasSubDirs())
            node.add(new DefaultMutableTreeNode( true ));
    }

    return true;
}

public boolean hasSubDirs(){
    File[] files = listFiles();
    if (files == null)
        return false;
    for (int k=0; k<files.length; k++)
    {
        if (files[k].isDirectory())
            return true;
    }
    return false;
}

public int compareTo(FileNode2 toCompare){ 
    return  mFile.getName().compareToIgnoreCase(
            toCompare.mFile.getName() ); 
}

protected File[] listFiles(){
    if (!mFile.isDirectory())
        return NO_FILES ;
    try
    {
        return mFile.listFiles();
    }
    catch (Exception ex)
    {
        JOptionPane.showMessageDialog(null, "Error reading directory "+mFile.getAbsolutePath(),"Warning", JOptionPane.WARNING_MESSAGE);
        return NO_FILES ;
    }
}
}

