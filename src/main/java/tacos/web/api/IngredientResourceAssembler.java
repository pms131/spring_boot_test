package tacos.web.api;

import java.util.Iterator;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import tacos.Ingredient;
import tacos.web.DesignTacoController;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource>{

	public IngredientResourceAssembler() {
		super(IngredientController.class, IngredientResource.class);
	}
	
	@Override
	protected IngredientResource instantiateModel(Ingredient ingredient) {
		return new IngredientResource(ingredient);
	}
	
	@Override
	public IngredientResource toModel(Ingredient ingredient) {
		return createModelWithId(ingredient.getId(), ingredient);
	}
	
	@Override
	public CollectionModel<IngredientResource> toCollectionModel(Iterable<? extends Ingredient> ingredients) {		
		return super.toCollectionModel(ingredients).add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos()).withSelfRel());
	}
}
