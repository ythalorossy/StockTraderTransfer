package com.transferwise.challenge.fake;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transferwise.challenge.model.Quote;
import com.transferwise.challenge.model.Ticker;
import com.transferwise.challenge.model.dto.OperationDTO;

@RestController
public class NYSEFakeRestController {

	private Ticker ticker;
	private ArrayList<Double> percentRandomValues;

	@PostConstruct
	public void initializeTickerAndPercentRandomValues() {
    	ArrayList<Quote> stocks = new ArrayList<Quote>();
    	stocks.add(new Quote("AAPL", new BigDecimal("179.70")));
    	stocks.add(new Quote("GOOGL", new BigDecimal("1160.55")));
    	stocks.add(new Quote("TSLA", new BigDecimal("325.28")));
    	ticker = new Ticker(stocks);
    	
    	percentRandomValues = new ArrayList<Double>();
    	
    	for(double percent=0.5;percent<=1.6;percent=percent+0.05) {
    		percentRandomValues.add(percent);	
    	}
	}
	
    @GetMapping("/nyse")
    public Ticker ticker() {
    	randomize();
    	return ticker;
    }
    
    private void randomize() {
    	Collections.shuffle(percentRandomValues);
    	int index = 0;
    	for(Quote quote:ticker.getQuotes()) {
    		double price = quote.getPrice().doubleValue() * (percentRandomValues.get(index++));
    		BigDecimal newPrice = new BigDecimal(price);
    		newPrice = newPrice.setScale(2, BigDecimal.ROUND_UP);
    		quote.setPrice(newPrice);
    	}
	}

	@PostMapping("/buy")
    public String buy(OperationDTO operation) {
    	return "OK";
    }
    
    @PostMapping("/sell")
    public String sell(OperationDTO operation) {
    	return "OK";
    }
}
