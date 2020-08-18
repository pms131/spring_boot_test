package tacos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@WebMvcTest
class TacoCloudApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testHomepage() throws Exception {
		mockMvc.perform(get("/"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("home"))
			   .andExpect(content().string(containsString("Welcome to...")));
		
	}
	
	/*
	 * 여기에 넣는 것도 가능 (@WebMvcConfigurer 추가시) 
	 @Override 
	 public void addViewControllers(ViewControllerRegistry registry) {
	 registry.addViewController("/").setViewName("home"); }
	 */

}
