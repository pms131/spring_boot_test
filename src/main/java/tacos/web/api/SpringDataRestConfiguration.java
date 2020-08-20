package tacos.web.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;

import tacos.Taco;

@Configuration
public class SpringDataRestConfiguration {
/*
	public ResourceProcessor<PagedModel<EntityModel<Taco>>> tacoProcessor(EntityLinks links) {

	    return new ResourceProcessor<PagedModel<EntityModel<Taco>>>() {
	      @Override
	      public PagedResources<EntityModel<Taco>> process(
	    		  PagedModel<EntityModel<Taco>> resource) {
	        resource.add(
	            links.linkFor(Taco.class)
	                 .slash("recent")
	                 .withRel("recents"));
	        return resource;
	      }
	    };
	  }
*/
}
