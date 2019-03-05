package com.example.demo;

import java.io.Serializable;

public class ItemEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int id;
	String name;
	String imageUrl;
	int price;

	public ItemEntity(int id, String name, String imageUrl, int price) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getPrice() {
		return price;
	}

}
