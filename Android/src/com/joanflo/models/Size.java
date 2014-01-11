package com.joanflo.models;

public class Size {
	
	
	// genre constants
	public static final char GENRE_MALE = 'M';
	public static final char GENRE_FEMALE = 'F';
	public static final char GENRE_UNISEX = 'U';
	
	// type constants
	public static final char TYPE_SHOES = 's';
	public static final char TYPE_CLOTHES = 'c';
	public static final char TYPE_OTHERS = 'o';
	
	
	// primary key
	private int idSize;
	
	// size info
	private int size;
	private char genre;
	private char type;
	
	
	
	public Size(int idSize, int size, char genre, char type) {
		this.idSize = idSize;
		this.size = size;
		this.genre = genre;
		this.type = type;
	}


	
	public int getIdSize() {
		return idSize;
	}


	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}


	public char getGenre() {
		return genre;
	}

	public void setGenre(char genre) {
		this.genre = genre;
	}


	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Color) {
			Size size = (Size) obj;
			return idSize == size.getIdSize(); 
		} else {
			return false;
		}
	}
	
	
}