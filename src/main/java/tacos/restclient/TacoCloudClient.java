package tacos.restclient;

import java.util.Collection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;

@Service
@Slf4j
@Data
public class TacoCloudClient {

	private RestTemplate rest;
	private Traverson traversion;
	
	public TacoCloudClient(RestTemplate rest, Traverson traverson) {
		this.rest = rest;
		this.traversion = traverson;
	}
	
	/*
	public Ingredient getIngredientById(String ingredientId) {
		return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
	}
	*/
	
	/*
	public Ingredient getIngredientById(String ingredientId) {
		Map<String, String> urlVariables = new HashMap<String, String>();
		urlVariables.put("id", ingredientId);
		URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}").build(urlVariables);
		
		return rest.getForObject(url, Ingredient.class);
	}
	*/
	
	public Ingredient getIngredientById(String ingredientId) {
		ResponseEntity<Ingredient> responseEntity = rest.getForEntity("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
		
		log.info("fetched time : " + responseEntity.getHeaders().getDate());
		
		return responseEntity.getBody();
	}
	
	public void updateIngredient(Ingredient ingredient) {
		rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
	}
	
	public void deleteIngredient(Ingredient ingredient) {
		rest.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
	}
	
	/*
	 * 새로 생성된 객체 반환
	public Ingredient createIngredient(Ingredient ingredient) {
		return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
	}
	*/
	
	/*
	 * 새로 생성된 리소스의 URI 반환
	 * public URI createIngredient(Ingredient ingredient) { 
	 * return rest.postForLocation("http://localhost:8080/ingredients", ingredient); 
	 * }
	 */
	
	public Ingredient createIngredient(Ingredient ingredient) {
		ResponseEntity<Ingredient> responseEntity = rest.postForEntity("http://localhost:8080/ingredients", ingredient, Ingredient.class);
		
		log.info("New resource created at : " + responseEntity.getHeaders().getLocation());
		
		return responseEntity.getBody();
	}
	
	//
	// Traverson with RestTemplate examples
	//
	
	
	public Iterable<Ingredient> getAllIngredientsWithTraverson() {
		ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType = new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
		};
		
		CollectionModel<Ingredient> ingredientRes = traversion
															.follow("ingredients")
															.toObject(ingredientType);
		
		Collection<Ingredient> ingredient = ingredientRes.getContent();
		
		return ingredient;
	}
	
	public Iterable<Taco> getRecentTacosWithTraverson() {
		ParameterizedTypeReference<CollectionModel<Taco>> tacoType = new ParameterizedTypeReference<CollectionModel<Taco>>() {
		};
		
		CollectionModel<Taco> tacoCollect = traversion.follow("tacos").follow("recents").toObject(tacoType);
		
		Collection<Taco> tacos = tacoCollect.getContent();
		
		return tacos;
	}
	
	public Ingredient addIngredient(Ingredient ingredient) {
		String ingredientUrl = this.getTraversion().follow("ingredients").asLink().getHref();
		
		return this.getRest().postForObject(ingredientUrl, ingredient, Ingredient.class);
	}
}
