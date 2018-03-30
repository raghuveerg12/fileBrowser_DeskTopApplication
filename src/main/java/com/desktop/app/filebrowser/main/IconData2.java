package com.desktop.app.filebrowser.main;

import javax.swing.Icon;

class IconData2 {
protected Icon   mIcon;
protected Icon   mExpandedIcon;
protected Object mData;

public IconData2(Icon icon, Object data)
{
    mIcon = icon;
    mExpandedIcon = null;
    mData = data;
}

public IconData2(Icon icon, Icon expandedIcon, Object data)
{
    mIcon = icon;
    mExpandedIcon = expandedIcon;
    mData = data;
}

public Icon getIcon() 
{ 
    return mIcon;
}

public Icon getExpandedIcon() 
{ 
    return mExpandedIcon!=null ? mExpandedIcon : mIcon;
}

public Object getObject() 
{ 
    return mData;
}

public String toString() 
{ 
    return mData.toString();
}
}

