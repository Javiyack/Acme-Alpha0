package services;

import domain.Actor;
import domain.Administrator;
import domain.Follow;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.FollowRepository;
import repositories.UserRepository;
import security.UserAccountService;

import java.util.Collection;

@Service
@Transactional
public class UserService {
    //Repositories
    @Autowired
    private UserRepository userRepository;
    //Services
    @Autowired
    private FollowService followService;
    @Autowired
    private ActorService actorService;

    //Constructor
    public UserService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public User create() {

        final User result = new User();


        return result;
    }

    public User save(User user) {
        return userRepository.save(user);

    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public Collection<User> findAllActive() {
        return userRepository.findAllActive();
    }



    public User findOne(int userId) {
        return userRepository.findOne(userId);
    }

    public Collection<User> findFollowedUsers() {
        return followService.findFollowedUsers();
    }

    public Collection<User> findFolloweds() {
        return followService.findFollowerUsers();
    }


    public Collection<User> findFollowerUsers() {
        return followService.findFollowerUsers();
    }

    public Collection<User> findFollowers() {
        return followService.findFollowerUsers();
    }
}
