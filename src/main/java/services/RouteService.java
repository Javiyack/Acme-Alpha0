
package services;

import domain.Actor;
import domain.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.RouteRepository;

import java.util.Collection;

@Service
@Transactional
public class RouteService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RouteRepository routeRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;


	// CRUD Methods

	// Create
	public Route create() {
		return new Route();
	}

	// Save
	public Route save(final Route route) {
		return routeRepository.save(route);
	}

	public Route findOne(Integer id){
		return routeRepository.findOne(id);
	}
	public Collection<Route> findAll(){
		return routeRepository.findAll();
	}

    public Collection<Route> findByLogedActor() {
		Actor actor;
		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor, "msg.not.loged.block");
        return routeRepository.findByOwner(actor.getId());
    }
    public Collection<Route> findByUserId(int id) {
        return routeRepository.findByUserId(id);
    }
    public Collection<Route> findByKeyWord(String keyWord) {
		return routeRepository.findByKeyWord(keyWord);
	}


	public void flush() {
		this.routeRepository.flush();

	}

}
