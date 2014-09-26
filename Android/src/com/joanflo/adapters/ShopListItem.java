package com.joanflo.adapters;

/**
 * Shop item class
 * @author Joanflo
 * @see ShopListAdapter
 */
public class ShopListItem {

	private CharSequence title;
    private int distance;
    
    
    
    /**
     * Shop list item constructor
     */
    public ShopListItem(){
    	distance = 0;
    }
    
    
    /**
     * Shop list item constructor
     * @param title
     * @param distance
     */
    public ShopListItem(CharSequence title, int distance){
        this.title = title;
        this.distance = distance;
    }
    
    

	public CharSequence getTitle() {
		return title;
	}

	public void setTitle(CharSequence title) {
		this.title = title;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
    
}
