package com.pmh.fakestore.dto;

public class ProductDTO {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private double price;
	
	private int avaiableQuantity;
	
	private String categoryName;

	public ProductDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
