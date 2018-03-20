package com.transferwise.challenge.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.transferwise.challenge.model.Inventory;
import com.transferwise.challenge.model.Quote;
import com.transferwise.challenge.model.Ticker;
import com.transferwise.challenge.model.repository.InventoryRepository;
import com.transferwise.challenge.model.repository.QuoteRepository;
import com.transferwise.challenge.service.util.ActionStockService;

@Component
public class TraderService {
	
	private static final Logger log = LoggerFactory.getLogger(TraderService.class);	
	
	@Autowired
	private InventoryRepository myInventoryRepository;
	
	@Autowired
	private QuoteRepository lastClosePricesRepository;
	
	@Autowired
	private StockMarketService stockMarketService;
	
	private Map<String, Inventory> myInventoryMap = new HashMap<String, Inventory>();
	private Map<String, Quote> lastClosePricesMap = new HashMap<String, Quote>();

	@PostConstruct
	public void loadData() {
		for (Inventory inventory : myInventoryRepository.findAll()) {
			myInventoryMap.put(inventory.getStock(), inventory);
		}
		for (Quote quote : lastClosePricesRepository.findAll()) {
			lastClosePricesMap.put(quote.getStock(), quote);
		}
	}
	
	public Quote getLastClosingPrice(String stock) {
		return lastClosePricesMap.get(stock);
	}

	public Inventory getMyInventory(String stock) {
		return myInventoryMap.get(stock);
	}
	
    public void tradeTicker(Ticker tickerFromMarket) {
    	
    	if (tickerFromMarket == null) return;
    	
        for(Inventory inventory:myInventoryMap.values()) {
        	
        	Quote lastDayStock = lastClosePricesMap.get(inventory.getStock());
			Quote currentStock = tickerFromMarket.getQuoteByStock(inventory.getStock());
			
			if(lastDayStock == null || currentStock == null) return;
			
			switch (ActionStockService.evaluateStocks(lastDayStock , currentStock)) {
			case BUY:
				buy(inventory,1000);
				break;
			case SELL:
				sell(inventory,0.5);
				break;
			default:
				break;
			}
        }
    }

    @Transactional
	private void sell(Inventory inventory, double percentToSell) {
    	
    	if ((inventory == null) || (percentToSell < 0) || (percentToSell >1)){
    		return;
    	}
    	
		try {
			Integer currentlyQuantity = inventory.getQuantity();
			int sellQuantity = (int) Math.ceil(percentToSell * currentlyQuantity);
			inventory.setQuantity(currentlyQuantity-sellQuantity);
			if(sellQuantity > 0) {
				myInventoryRepository.save(inventory);
				log.info("Selling {} , qty {}, rem {}",inventory.getStock(),sellQuantity,inventory.getQuantity());
				stockMarketService.sendSellSign(inventory.getStock(),sellQuantity);
			}
		}catch (RuntimeException e) {
            log.error(e.getMessage());
        }
	}

    @Transactional
	private void buy(Inventory inventory, int quantityToBuy) {

    	if ((inventory == null) || (quantityToBuy < 0)){
    		return;
    	}

		try {
			if(quantityToBuy > 0) {
				Integer currentlyQuantity = inventory.getQuantity();
				inventory.setQuantity(currentlyQuantity+quantityToBuy);
				myInventoryRepository.save(inventory);
				log.info("Buying {} , qty {}, total {}",inventory.getStock(),quantityToBuy,inventory.getQuantity());
				stockMarketService.sendBuySign(inventory.getStock(),quantityToBuy);
			}
		}catch (RuntimeException e) {
            log.error(e.getMessage());
        }
	}

}
