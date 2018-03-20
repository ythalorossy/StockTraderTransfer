package com.transferwise.challenge.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import com.transferwise.challenge.service.ws.client.NyseWSRestClient;

@RunWith(MockitoJUnitRunner.class)
public class StockMarketServiceTest {
	
	@InjectMocks
	StockMarketService stockMarketService;
	
	@Mock
	private NyseWSRestClient nyseWSRestClient;

	@Mock
	private JmsTemplate jmsTemplate;
	


}
