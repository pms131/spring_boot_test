package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import tacos.Order;

@Component
public class RabbitOrderReceiver {
	private RabbitTemplate rabbit;
	private MessageConverter converter;
	
	@Autowired
	public RabbitOrderReceiver(RabbitTemplate rabbit, MessageConverter converter) {
		this.rabbit = rabbit;
		this.converter = converter;
	}
	
	/*
	 * 메시지 변환기를 사용하여 변환	
	public Order receiveOrder() {
		Message message = rabbit.receive("tacocloud.orders", 30000);
		return message != null
				? (Order) converter.fromMessage(message)
				: null;
	}
	*/
	
	/* receiveAndConverter()를 활용하여 변환 
	 * 
	public Order receiveOrder() {
		return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");
	}
	*/
	
	/* ParameterizedTypeReference<T>를 활용한 변환 */
	public Order receiverOrder() {
		return rabbit.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<Order>() {
		});
	}
}
