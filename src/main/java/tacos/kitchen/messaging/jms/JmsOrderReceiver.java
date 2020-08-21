package tacos.kitchen.messaging.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import tacos.Order;

@Component
public class JmsOrderReceiver implements OrderReceiver {

	private JmsTemplate jms;
	private MessageConverter converter;

	@Autowired
	public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
		this.jms = jms;
		this.converter = converter;
	}

	/* 원시 데이터를 그대로 변환 (헤더와 페이로드 모두 볼 경우)
	@Override
	public Order receiveOrder() throws JMSException{
		Message message = jms.receive("tacocloud.order.queue");
		return (Order) converter.fromMessage(message);
	}
	 */
	
	/* receiveAndConvert 함수를 이용하여 페이로드만 추출 */
	public Order receiveOrder() throws JMSException {
		return (Order) jms.receiveAndConvert("tacocloud.order.queue");
	}
}
