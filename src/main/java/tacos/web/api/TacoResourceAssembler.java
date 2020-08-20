package tacos.web.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import tacos.Taco;
import tacos.web.DesignTacoController;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource>{

	public TacoResourceAssembler() {
		super(DesignTacoController.class, TacoResource.class);
	}
	
	@Override
	protected TacoResource instantiateModel(Taco taco) {
		return new TacoResource(taco);
	}
	
	@Override
	public TacoResource toModel(Taco taco) {
		return createModelWithId(taco.getId(), taco);
	}
	
	@Override
	public CollectionModel<TacoResource> toCollectionModel(Iterable<? extends Taco> tacos) {
		return super.toCollectionModel(tacos).add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos()).withSelfRel());
	}
}
