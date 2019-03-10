package com.example.demo.entity;

public class ItemEntity {
	
	private int id;
	private String name;
	private String imageUrl;
	private int price;

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
