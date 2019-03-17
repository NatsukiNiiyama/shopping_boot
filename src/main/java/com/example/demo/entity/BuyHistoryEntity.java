package com.example.demo.entity;

public class BuyHistoryEntity {

	private Integer historyId;
	private String userId;
	private Integer itemId;
	private Integer count;

	public BuyHistoryEntity() {
	}

	public BuyHistoryEntity(Integer historyId, String userId, Integer itemId, Integer count) {
		this.historyId = historyId;
		this.userId = userId;
		this.itemId = itemId;
		this.count = count;
	}

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
