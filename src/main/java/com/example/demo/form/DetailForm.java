package com.example.demo.form;

import java.io.Serializable;

public class DetailForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int itemId;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
