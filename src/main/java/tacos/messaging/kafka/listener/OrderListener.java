package tacos.messaging.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.kitchen.KitchenUI;

@Slf4j
@Component
public class OrderListener {

	private KitchenUI ui;

	@Autowired
	public OrderListener(KitchenUI ui) {
		this.ui = ui;
	}
	
	/*
	@KafkaListener(topics = "tacocloud.orders.topic")
	public void handle(Order order) {
		ui.displayOrder(order);
	}
	*/
	
	@KafkaListener(topics = "tacocloud.orders.topic")
	public void handle(Order order, ConsumerRecord<String, Order> record) {
		log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());
		ui.displayOrder(order);
	}
}
