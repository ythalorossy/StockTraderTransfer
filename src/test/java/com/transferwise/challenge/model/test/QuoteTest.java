package com.transferwise.challenge.model.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.transferwise.challenge.model.Quote;

public class QuoteTest {
	
	@Test
	public void testConstructorWithNegativePrice() {
		Quote quote = new Quote("AAA",new BigDecimal(-0.01));
		assertEquals(BigDecimal.ZERO, quote.getPrice());
	}
	
	@Test
	public void testConstructorDefaultWithNegativePrice() {
		Quote quote = new Quote();
		quote.setPrice(new BigDecimal(-0.01));
		assertEquals(BigDecimal.ZERO, quote.getPrice());
	}
	
	@Test
	public void testNormalPrice() {
		Quote quote = new Quote();
		quote.setPrice(new BigDecimal(0.01));
		assertEquals(new BigDecimal(0.01), quote.getPrice());
	}
}
