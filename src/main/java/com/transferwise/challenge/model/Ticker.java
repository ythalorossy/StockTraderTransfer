package com.transferwise.challenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker implements Serializable{

	private static final long serialVersionUID = 3158195725055440865L;

	private List<Quote> quotes = new ArrayList<Quote>();
	
	public Ticker() {
	}
	
	public Ticker(List<Quote> quotes) {
		 this.quotes = quotes;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	public Quote getQuoteByStock(String stock) {
		for(Quote quote:quotes) {
			if(quote.getStock().equals(stock)) {
				return quote;	
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quotes == null) ? 0 : quotes.hashCode());
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
		Ticker other = (Ticker) obj;
		if (quotes == null) {
			if (other.quotes != null)
				return false;
		} else if (!quotes.equals(other.quotes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticker [quotes=" + quotes + "]";
	}
}
