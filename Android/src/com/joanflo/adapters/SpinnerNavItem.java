package com.joanflo.adapters;

/**
 * Spinner item class
 * @author Joanflo
 * @see SpinnerNavAdapter
 */
public class SpinnerNavItem {

	
	private CharSequence title;
	private int icon;
     
	
	
	/**
	 * Spinner item constructor
	 * @param title
	 * @param icon
	 */
	public SpinnerNavItem(CharSequence title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	

	public CharSequence getTitle() {
		return title;
	}

	public void setTitle(CharSequence title) {
		this.title = title;
	}

	
	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	

}