package com.transferwise.challenge.services.util;

import static com.transferwise.challenge.service.util.ActionStockService.evaluateStocks;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.transferwise.challenge.enums.TradeAction;
import com.transferwise.challenge.model.Quote;

public class ActionStockServiceTest {

	@Test
	public void testEvaluateStocksToSell() {
		Quote lastDayStock = new Quote("AAA",new BigDecimal("100"));
		Quote currentStock = new Quote("AAA",new BigDecimal("110.1"));
		TradeAction action = evaluateStocks(lastDayStock, currentStock);
		assertEquals(TradeAction.SELL, action);
	}
	
	@Test
	public void testEvaluateStocksToBuy() {
		Quote lastDayStock = new Quote("AAA",new BigDecimal("100"));
		Quote currentStock = new Quote("AAA",new BigDecimal("89.9"));
		TradeAction action = evaluateStocks(lastDayStock, currentStock);
		assertEquals(TradeAction.BUY, action);
	}
	
	@Test
	public void testEvaluateStocksToNothing() {
		Quote lastDayStock = new Quote("AAA",new BigDecimal("100"));
		Quote currentStock = new Quote("AAA",new BigDecimal("102"));
		TradeAction action = evaluateStocks(lastDayStock, currentStock);
		assertEquals(TradeAction.NOTHING, action);
	}
	
	@Test
	public void testEvaluateDifferentsStocksToNothing() {
		Quote lastDayStock = new Quote("AAA",new BigDecimal("100"));
		Quote currentStock = new Quote("BBB",new BigDecimal("200"));
		TradeAction action = evaluateStocks(lastDayStock, currentStock);
		assertEquals(TradeAction.NOTHING, action);
	}
	
	@Test
	public void testNullStocksToNothing() {
		TradeAction action = evaluateStocks(null, null);
		assertEquals(TradeAction.NOTHING, action);
	}
	
	@Test
	public void testEvaluateStocksSpecialCase() {
		Quote lastDayStock = new Quote("AAA",new BigDecimal("179.20"));
		Quote currentStock = new Quote("AAA",new BigDecimal("179.70"));
		TradeAction action = evaluateStocks(lastDayStock, currentStock);
		assertEquals(TradeAction.NOTHING, action);
	}

}
