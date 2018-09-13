package services;

import domain.Actor;
import domain.Follow;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.FollowRepository;
import repositories.UserRepository;

import java.util.Collection;

@Service
@Transactional
public class FollowService {
    //Repositories
    @Autowired
    private UserRepository userRepository;
    //Services
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private ActorService actorService;

    //Constructor
    public FollowService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Follow create() {
        final Follow result = new Follow();
        return result;
    }

    public Follow save(Follow foolow) {
        return followRepository.save(foolow);

    }

    public Collection<Follow> findAll() {
        return followRepository.findAll();
    }


    public Collection<Follow> findFollowers() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.notNull(actor instanceof User, "msg.not.user.block");
        return followRepository.findByFollowed(actor.getId());
    }

    public Follow findOne(int followId) {
        return followRepository.findOne(followId);
    }

    public Collection<Follow> findFolloweds() {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.notNull(actor instanceof User, "msg.not.user.block");
        return followRepository.findByFollower(actor.getId());
    }

    public void follow(int userId) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.notNull(actor instanceof User, "msg.not.user.block");
        int follower = actor.getId();
        User followed = userRepository.findOne(userId);
        Follow follow = followRepository.find(follower, followed.getId());
        if(follow!=null)
            followRepository.delete(follow);
        else{
            follow = this.create();
            follow.setFollower((User)actor);
            follow.setFollowed(followed);
            followRepository.save(follow);
        }
    }
}
