package tacos.kitchen.messaging.jms;

import javax.jms.JMSException;

import tacos.Order;

public interface OrderReceiver {
	public Order receiveOrder() throws JMSException; 
}
