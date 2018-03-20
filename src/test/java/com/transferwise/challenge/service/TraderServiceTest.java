package com.transferwise.challenge.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.transferwise.challenge.model.Inventory;
import com.transferwise.challenge.model.Quote;
import com.transferwise.challenge.model.Ticker;
import com.transferwise.challenge.model.repository.InventoryRepository;
import com.transferwise.challenge.model.repository.QuoteRepository;

@RunWith(MockitoJUnitRunner.class)
public class TraderServiceTest {

	@InjectMocks
	TraderService trader;
	
	@Mock
	InventoryRepository myInventoryRepository;
	
	@Mock
	QuoteRepository lastClosePricesRepository;
	
	@Mock
	StockMarketService stockMarketService;


	private ArrayList<Inventory> myInventoryList;
	private ArrayList<Quote> lastClosePricesList;

	private Ticker ticker;

	
	@Before
	public void setup() {
		myInventoryList = new ArrayList<Inventory>();
		myInventoryList.add(new Inventory("BUY",1000));
		myInventoryList.add(new Inventory("SELL",1000));
		myInventoryList.add(new Inventory("NOTHING",1000));
		
		lastClosePricesList = new ArrayList<Quote>();
		lastClosePricesList.add(new Quote("BUY",new BigDecimal(100.00)));
		lastClosePricesList.add(new Quote("SELL",new BigDecimal(100.00)));
		lastClosePricesList.add(new Quote("NOTHING",new BigDecimal(100.00)));
		lastClosePricesList.add(new Quote("DDD",new BigDecimal(100.00)));
		lastClosePricesList.add(new Quote("EEE",new BigDecimal(100.00)));
		
		List<Quote> quotes = new ArrayList<Quote>();
		quotes.add(new Quote("BUY",new BigDecimal(80.00)));
		quotes.add(new Quote("SELL",new BigDecimal(111.00)));
		quotes.add(new Quote("NOTHING",new BigDecimal(100.00)));
		quotes.add(new Quote("DDD",new BigDecimal(100.00)));
		quotes.add(new Quote("EEE",new BigDecimal(100.00)));
		
		ticker = new Ticker(quotes);
		
		Mockito.when(myInventoryRepository.findAll()).thenReturn(myInventoryList);
		Mockito.when(lastClosePricesRepository.findAll()).thenReturn(lastClosePricesList);
//		Mockito.when(stockMarketService.sell(Mockito.anyString(),Mockito.anyInt()));

		trader.loadData();
	}
	
	@Test
	public void tradeTickerTest() {
		trader.tradeTicker(ticker);
		assertEquals(new Integer(2000),trader.getMyInventory("BUY").getQuantity());
		assertEquals(new Integer(500),trader.getMyInventory("SELL").getQuantity());
		assertEquals(new Integer(1000),trader.getMyInventory("NOTHING").getQuantity());
	}
	
	@Test
	public void tradeTickerMultiplyTradingTest() {
		for(int i=0;i<11;i++) {
			trader.tradeTicker(ticker);	
		}
		assertEquals(new Integer(12000),trader.getMyInventory("BUY").getQuantity());
		assertEquals(new Integer(0),trader.getMyInventory("SELL").getQuantity());
		assertEquals(new Integer(1000),trader.getMyInventory("NOTHING").getQuantity());
	}
	
	@Test
	public void tradeTickerWithoutInventory() {
		Mockito.when(myInventoryRepository.findAll()).thenReturn( new ArrayList<Inventory>());
		trader.loadData();
		trader.tradeTicker(ticker);
	}
	
	@Test
	public void tradeTickerWithoutLastClosePrice() {
		Mockito.when(lastClosePricesRepository.findAll()).thenReturn( new ArrayList<Quote>());
		trader.loadData();
		trader.tradeTicker(ticker);
	}

}
