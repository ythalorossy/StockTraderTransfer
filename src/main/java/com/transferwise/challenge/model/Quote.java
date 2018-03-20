package com.transferwise.challenge.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote implements Serializable{

	private static final long serialVersionUID = 7954982066853584415L;

	@Id
	private String stock;

	private BigDecimal price = BigDecimal.ZERO;

	public Quote() {
		
	}
	
	public Quote(String stock) {
		super();
		this.stock = stock;
	}

	public Quote(String stock, BigDecimal price) {
		super();
		this.stock = stock;
		this.setPrice(price);
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		if((price != null) && (price.doubleValue() >= 0)) {
			this.price = price;	
		}else {
			this.price = BigDecimal.ZERO;
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
		Quote other = (Quote) obj;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stock [id=" + stock + ", price=" + price + "]";
	}
}
