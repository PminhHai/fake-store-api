package com.pmh.fakestore.dto;

public class AddProductDTO {
	
	private String name;
	
	private String description;
	
	private double price;
	
	private int avaiableQuantity;
	
	private Long categoryId;

	public AddProductDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvaiableQuantity() {
		return avaiableQuantity;
	}

	public void setAvaiableQuantity(int avaiableQuantity) {
		this.avaiableQuantity = avaiableQuantity;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
