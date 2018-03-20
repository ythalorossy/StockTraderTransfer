package com.transferwise.challenge.service.JMS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.transferwise.challenge.model.Ticker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraderJMSReceiverTest {

	@Autowired
	TraderJMSReceiver traderJMSReceiver;
	
	@Test
	public void testExecutionReceivingNull(){
		traderJMSReceiver.receiveMessage(null);
	}
	
	@Test
	public void testExecutionWithoutException(){
		traderJMSReceiver.receiveMessage(new Ticker());
	}
}
