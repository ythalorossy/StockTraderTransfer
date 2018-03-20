package com.transferwise.challenge.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {

	@Id
	private String stock;
	private Integer quantity = 0;

	public Inventory() {
	}
	
	public Inventory(String stock, Integer quantity) {
		super();
		this.stock = stock;
		this.quantity = quantity;
	}
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		if(quantity != null) {
			this.quantity = quantity;	
		}else {
			this.quantity = 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InventoryStock [code=" + stock + ", quantity=" + quantity + "]";
	}

}
