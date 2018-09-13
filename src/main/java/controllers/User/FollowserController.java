
package controllers.User;

import controllers.AbstractController;
import domain.Actor;
import domain.User;
import forms.ActorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.Authority;
import services.ActorService;
import services.FollowService;
import services.UserService;

import java.util.Collection;

@Controller
@RequestMapping("/follow/user")
public class FollowserController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;


	// Constructors -----------------------------------------------------------

	public FollowserController() {
		super();
	}

	// List Followeds -------------------------------------------------------
	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public ModelAndView followeds(final Integer pageSize) {
		ModelAndView result;

		final Collection<User> users = this.userService.findFollowers();

		result = new ModelAndView("actor/list");
		result.addObject("actors", users);
		result.addObject("requestUri", "follow/user/followers.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}
	// List followers --------------------------------------------------------
	@RequestMapping(value = "/followeds", method = RequestMethod.GET)
	public ModelAndView followers(final Integer pageSize) {
		ModelAndView result;

		final Collection<User> users = this.userService.findFolloweds();

		result = new ModelAndView("actor/list");
		result.addObject("actors", users);
		result.addObject("requestUri", "follow/user/followeds.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Foolow  -----------------------------------------------------------
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView activation(@RequestParam final int userId) {
		ModelAndView result;
		try{
			this.followService.follow(userId);

		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), "/");
			} else {
				return this.createMessageModelAndView("panic.message.text", "/");
			}
		}
		result = new ModelAndView("redirect:/follow/user/followeds.do");
		return result;
	}



}
