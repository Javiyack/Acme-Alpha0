
package services;

import domain.Actor;
import domain.Hike;
import domain.Route;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.HikeRepository;
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
    @Autowired
    private HikeRepository hikeRepository;


    // CRUD Methods

    // Create
    public Route create() {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Route result=new Route();
        result.setUser((User)actor);
        return result;
    }

    // Save
    public Route save( Route route) {
        Assert.notNull(route, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Assert.isTrue(route.getUser().equals(actor), "msg.not.owned.block");
        Route result = routeRepository.saveAndFlush(route);
        return result;
    }

    public Route findOne(Integer id) {
        return routeRepository.findOne(id);
    }

    public Collection<Route> findAll() {
        Collection<Route> routes =  routeRepository.findAll();
        return routes;
    }

    public Collection<Route> findByLogedActor() {
        Actor actor;
        actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
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

    public void delete(Route route) {
        Assert.notNull(route, "msg.not.found.resource");
        Assert.notNull(route.getUser().equals((User)actorService.findByPrincipal()), "msg.not.owned.block");
        Collection<Hike> Hikes = hikeRepository.findByRouteId(route.getId());
        for (Hike hike:Hikes) {
            hikeRepository.delete(hike);
        }
        routeRepository.delete(route);
    }
    public void delete(int routeId) {
        Route route = routeRepository.findOne(routeId);
        Assert.notNull(route, "msg.not.found.resource");
        Assert.isTrue(route.getUser().equals((User)actorService.findByPrincipal()), "msg.not.owned.block");
        Collection<Hike> hikes = hikeRepository.findByRouteId(routeId);
        hikeRepository.deleteInBatch(hikes);
        routeRepository.delete(routeId);
    }
}
