package com.pmh.fakestore.dto;

public class UpdateCartDTO {
	
	private Long id;
	
	private int quantity;

	public UpdateCartDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
