package com.transferwise.challenge.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.transferwise.challenge.enums.TradeAction;
import com.transferwise.challenge.model.Quote;

public class ActionStockService {

	public static TradeAction evaluateStocks(Quote lastDayStock, Quote currentStock) {
		
		if((lastDayStock == null) || (currentStock == null)) {
			return TradeAction.NOTHING;
		}
		
		if(lastDayStock.getStock().equals(currentStock.getStock())) {
			BigDecimal percentageDifference = currentStock.getPrice().subtract(lastDayStock.getPrice())
					.divide(lastDayStock.getPrice(),2,RoundingMode.UP);
			
			if(percentageDifference.doubleValue() > 0.1) {
				return TradeAction.SELL;
			}
			if(percentageDifference.doubleValue() < -0.1) {
				return TradeAction.BUY;
			}
		}
		return TradeAction.NOTHING;
	}

}
