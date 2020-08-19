package tacos.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
public class OrderProps {
	
	@Min(value = 5, message = "must be betwwen 5 and 25")
	@Max(value = 25, message = "must be betwwen 5 and 25")
	private int pageSize = 20;
}
