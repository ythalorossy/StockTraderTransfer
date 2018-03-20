package com.transferwise.challenge.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.transferwise.challenge.model.Ticker;
import com.transferwise.challenge.service.ws.client.NyseWSRestClient;

@Component
public class StockMarketService {

	@Autowired
	private NyseWSRestClient nyseWSRestClient;

	@Autowired
	private JmsTemplate jmsTemplate;

	private static final Logger log = LoggerFactory.getLogger(StockMarketService.class);

	@Scheduled(fixedRate = 2000)
	public void sendMessageFromNYSE() {
		log.info("Collecting Data From NYSE at {}", Calendar.getInstance().getTime());

		Ticker ticker = nyseWSRestClient.retrieveDataFromNYSE();

		jmsTemplate.convertAndSend("trader", ticker);
	}

	public void sendSellSign(String stock, int sellQuantity) {
		if((stock == null) || (sellQuantity<0)) {
			return;
		}
		nyseWSRestClient.sendSellSign(stock, sellQuantity);
	}

	public void sendBuySign(String stock, int quantityToBuy) {
		if((stock == null) || (quantityToBuy<0)) {
			return;
		}

		nyseWSRestClient.sendBuySign(stock, quantityToBuy);
	}
}
