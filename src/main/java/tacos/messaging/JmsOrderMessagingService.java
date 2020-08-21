package tacos.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;

import tacos.Order;

public class JmsOrderMessagingService implements OrderMessagingService {

	private JmsTemplate jms;
	
	private Destination orderQueue;
	
	public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
		this.jms = jms;
		this.orderQueue = orderQueue;
	}
	
	@Override
	public void sendOrder(Order order) {
		
		/* 1번째 방법 
		   jms.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(order);
			}
		});
		*/
		/* 1번째 방법 ( using Lambda )*/
		//jms.send(session -> session.createObjectMessage(order));
		
		/* 2번째 방법 (기본 도착지가 아닌 다른 곳에 전송) */
		//jms.send(orderQueue, session -> session.createObjectMessage(order));
		
		/* 2번째 방법 (목적지 직접 설정) */
		//jms.send("tacocloud.order.queue", session->session.createObjectMessage(order));
		
		/* 3번째 방법 ( using convertAndSend() ) */
		//jms.convertAndSend("tacocloud.order.queue", order);
		
		/* 3번째 방법 ( 메시지 변환기 사용하여 커스텀 헤더를 메시지에 추가 ) */
		/*
		jms.convertAndSend("tacocloud.order.queue", order, new MessagePostProcessor() {
			
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("X_ORDER_SOURCE", "WEB");
				return message;
			}
		});
		*/
		
		/* 3번째 방법 ( using Lambda ) */
		/*
		jms.convertAndSend("tacocloud.order.queue", order, message -> {
			message.setStringProperty("X_ORDER_SOURCE", "WEB");
			return message;
		});
		*/
		
		/* 3번째 방법 (메소드 참조) */
		jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
		
	}
	
	private Message addOrderSource(Message message) throws JMSException {
		message.setStringProperty("X_ORDER_SOURCE", "WEB");
		return message;
	}
}
