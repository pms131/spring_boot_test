package tacos.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.TacoRepository;

@Slf4j
@RepositoryRestController
public class RecentTacoController {
	
	private TacoRepository tacoRepo;
	
	@Autowired
	public RecentTacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	@GetMapping(path="/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<CollectionModel<TacoResource>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		
		List<Taco> tacos = tacoRepo.findAll(page).getContent();
		
		CollectionModel<TacoResource> tacoCollectionModel = new TacoResourceAssembler().toCollectionModel(tacos);
		
		tacoCollectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecentTacoController.class, recentTacos())).withRel("recents"));
		
		return new ResponseEntity<>(tacoCollectionModel, HttpStatus.OK);
	}
}
