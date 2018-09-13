
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

import java.util.Collection;

@Service
@Transactional
public class HikeService {

    // Managed repository -----------------------------------------------------


    // Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private HikeRepository hikeRepository;
    @Autowired
    private RouteService routeService;


    // CRUD Methods

    // Create
    public Hike create(Route route) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Hike result= new Hike();
        result.setRoute(route);
        return result;
    }

    // Save
    public Hike save( Hike hike) {
        Assert.notNull(hike, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Assert.isTrue(hike.getRoute().getUser().equals(actor), "msg.not.owned.block");
        Hike result = hikeRepository.saveAndFlush(hike);
        return result;
    }

    public Hike findOne(Integer id) {
        return hikeRepository.findOne(id);
    }

    public Collection<Hike> findAll() {
        Collection<Hike> hikes =  hikeRepository.findAll();
        return hikes;
    }

    public Collection<Hike> findByLogedActor() {
        Actor actor;
        actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return hikeRepository.findByUserId((actor.getId()));
    }

    public Collection<Hike> findByUserId(int id) {
        return hikeRepository.findByUserId(id);
    }

    public Collection<Hike> findByKeyWord(String keyWord) {
        return hikeRepository.findByIndexedKeyWord(keyWord);
    }


    public void flush() {
        this.hikeRepository.flush();

    }

    public void delete(Hike hike) {
        hikeRepository.delete(hike);
    }

    public void delete(int hikeId) {
        hikeRepository.delete(hikeId);
    }
    public void deleteInBatch(Collection<Hike> hikes) {
        hikeRepository.deleteInBatch(hikes);
    }

    public Collection<Hike> findByRoute(Route route) {
        return this.findByRouteId(route.getId());
    }
    public Collection<Hike> findByRouteId(Integer routeId) {

        return hikeRepository.findByRouteId(routeId);
    }
}
