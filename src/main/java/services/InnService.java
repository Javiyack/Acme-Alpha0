
package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.HikeRepository;

import java.util.Collection;

@Service
@Transactional
public class InnService {

    // Managed repository -----------------------------------------------------


    // Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private HikeRepository hikeRepository;
    @Autowired
    private InnRepository innRepository;


    // CRUD Methods

    // Create
    public Inn create(Inn inn) {
        Actor actor =actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof InnKeeper, "msg.not.owned.block");
        Inn result= new Inn();
        result.setInnKeeper((InnKeeper) actor);
        return result;
    }

    // Save
    public Inn save( Inn inn) {
        Assert.notNull(inn, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof InnKeeper, "msg.not.owned.block");
        Assert.isTrue(inn.getInnKeeper().equals(actor), "msg.not.owned.block");
        Inn result = innRepository.saveAndFlush(inn);
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
