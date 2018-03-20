package com.transferwise.challenge.service.ws.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.transferwise.challenge.enums.TradeAction;
import com.transferwise.challenge.model.Quote;
import com.transferwise.challenge.model.Ticker;
import com.transferwise.challenge.model.dto.OperationDTO;

@Component
public class NyseWSRestClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(NyseWSRestClient.class);
	
		
	public NyseWSRestClient() {
	}
	
	public Ticker retrieveDataFromNYSE() {
		Ticker ticker = restTemplate.getForObject("http://localhost:8080/nyse", Ticker.class);
		log.info(ticker.toString());
		for (Quote stock : ticker.getQuotes()) {
			log.info("{}", stock);
		}
		return ticker;
	}
	
	public void sendSellSign(String stock, int sellQuantity) {
		if((stock == null) || (sellQuantity<0)) {
			return;
		}
		
		OperationDTO operationDTO = new OperationDTO(stock, TradeAction.SELL.toString(), sellQuantity);

		HttpEntity<OperationDTO> request = new HttpEntity<OperationDTO>(operationDTO);
		restTemplate.postForObject("http://localhost:8080/sell", request, String.class);
		log.info("POSTED {} - {} , amount {}", operationDTO.getOperation(), operationDTO.getCode(),
				operationDTO.getAmount());
	}
	
	public void sendBuySign(String stock, int quantityToBuy) {
		if((stock == null) || (quantityToBuy<0)) {
			return;
		}

		OperationDTO operationDTO = new OperationDTO(stock, TradeAction.BUY.toString(), quantityToBuy);

		HttpEntity<OperationDTO> request = new HttpEntity<OperationDTO>(operationDTO);
		restTemplate.postForObject("http://localhost:8080/buy", request, String.class);
		log.info("POSTED {} - {} , amount {}", operationDTO.getOperation(), operationDTO.getCode(),
				operationDTO.getAmount());
	}
}