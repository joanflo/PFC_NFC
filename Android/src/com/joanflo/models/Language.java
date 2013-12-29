package com.joanflo.models;

public class Language {

	
	// primary key
	private CharSequence languageName;
	
	
	
	public Language(CharSequence languageName) {
		this.languageName = languageName;
	}
	
	
	
	public CharSequence getLanguageName() {
		return languageName;
	}
	
	
}