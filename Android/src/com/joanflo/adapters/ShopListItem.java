package com.joanflo.adapters;

public class ShopListItem {

	private CharSequence title;
    private int distance;
    
    
    public ShopListItem(){
    	distance = 0;
    }
    
    
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
