
package controllers;

import domain.Actor;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private UserService userService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;

		final Collection<User> users = this.userService.findAllActive();
		final Collection<User> followeds = this.userService.findFollowedUsers();
		Map<User,Boolean> userIsFollowedMap = new HashMap<>();
		for (User user:users) {
			userIsFollowedMap.put(user,followeds.contains(user));
		}
		result = new ModelAndView("actor/list");
		result.addObject("actors", users);
		result.addObject("userIsFollowedMap", userIsFollowedMap);
		result.addObject("requestUri", "user/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView listAll(final Integer pageSize) {
		ModelAndView result;

		final Collection<Actor> users = this.actorService.findAll();
		result = new ModelAndView("actor/list");
		result.addObject("actors", users);
		result.addObject("requestUri", "user/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Display user -----------------------------------------------------------
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int userId) {
			ModelAndView result;

			final User user = this.userService.findOne(userId);
			result = new ModelAndView("actor/display");
			result.addObject("actorForm", user);
			result.addObject("display", true);
			return result;
		}

}
