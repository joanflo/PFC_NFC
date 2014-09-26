package com.joanflo.adapters;

/**
 * Navigation item class
 * @author Joanflo
 * @see NavigationDrawerListAdapter
 */
public class NavigationDrawerListItem {

	private String title;
    private int icon;
    private String count = "0";
    // boolean to set visibility of the counter
    private boolean isCounterVisible;
    
    
    
    /**
     * Navigation drawer list item constructor
     */
    public NavigationDrawerListItem(){
    	isCounterVisible = false;
    }
    
    
    /**
     * Navigation drawer list item constructor
     * @param title
     * @param icon
     */
    public NavigationDrawerListItem(String title, int icon){
    	isCounterVisible = false;
        this.title = title;
        this.icon = icon;
    }
    
    
    /**
     * Navigation drawer list item constructor
     * @param title
     * @param icon
     * @param isCounterVisible
     * @param count
     */
    public NavigationDrawerListItem(String title, int icon, boolean isCounterVisible, String count){
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }
    
    
    public String getTitle(){
        return this.title;
    }
     
    public int getIcon(){
        return this.icon;
    }
     
    public String getCount(){
        return this.count;
    }
     
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
     
    public void setIcon(int icon){
        this.icon = icon;
    }
     
    public void setCount(String count){
        this.count = count;
    }
     
    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
	
}
