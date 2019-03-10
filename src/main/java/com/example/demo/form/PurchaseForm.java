package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.Max;

public class PurchaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int itemId;

	@Max(value = 999)
	int count;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
