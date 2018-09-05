package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.User;
import repositories.UserRepository;
import security.UserAccountService;

@Service
@Transactional
public class UserService {
	//Repositories
	@Autowired
	private UserRepository userRepository;
	//Services
	@Autowired
	private UserAccountService userAccountService;
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
		
		//Save
		
//		//Other
//		public Manager reconstruct(final UserRegisterForm userForm) {
//			Manager res;
//			res = this.create();
//
//			res.setName(userForm.getName());
//			res.setPhone(userForm.getPhone());
//			res.setSurname(userForm.getSurname());
//			res.setEmail(userForm.getEmail());
//			res.setAddress(userForm.getAddress());
//			res.getUserAccount().setUsername(userForm.getUsername());
//			res.getUserAccount().setPassword(userForm.getPassword());
//			res.setAdult(userForm.isAdult());
//
//			return res;
//		}


		public User save(User user) {
			return userRepository.save(user);
			
		}

		public Collection<User> findAll() {
			return userRepository.findAll();
		}

		


		
		
	
	

}
