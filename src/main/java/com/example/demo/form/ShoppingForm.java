package com.example.demo.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class ShoppingForm {

	String userId;
	
	int itemId;

	@NotEmpty
	@Max(value = 999)
	String count;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
