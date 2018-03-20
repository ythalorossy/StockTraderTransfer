package com.transferwise.challenge.service.JMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.transferwise.challenge.model.Ticker;
import com.transferwise.challenge.service.TraderService;

@Component
public class TraderJMSReceiver {
	
	@Autowired
	TraderService trader;
	
    @JmsListener(destination = "trader", containerFactory = "myFactory")
    public void receiveMessage(Ticker tickerFromMarket) {
    	if(tickerFromMarket != null) {
    		trader.tradeTicker(tickerFromMarket);
    	}
    }
}
