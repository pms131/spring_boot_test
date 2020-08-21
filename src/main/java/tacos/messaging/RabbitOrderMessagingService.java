package tacos.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import tacos.Order;

public class RabbitOrderMessagingService implements OrderMessagingService{

	private RabbitTemplate rabbit;

	public RabbitOrderMessagingService(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}
	
	/*
	 * send() method 사용
	public void sendOrder(Order order) {
		MessageConverter converter = rabbit.getMessageConverter();
		MessageProperties props = new MessageProperties();
		props.setHeader("X_ORDDER_SOURCE", "WEB");
		Message message = converter.toMessage(order, props);
		rabbit.send("tacocloud.order", message);
	}
	*/
	
	/* convertAndSend Method 사용 */
	@Override
	public void sendOrder(Order order) {
		rabbit.convertAndSend("tacocloud.order.queue", order, new MessagePostProcessor() {
			
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				MessageProperties props = message.getMessageProperties();
				props.setHeader("X_ORDDER_SOURCE", "WEB");
				return message;
			}
		});
	}
}
