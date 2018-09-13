
package controllers.User;

import controllers.AbstractController;
import controllers.RouteController;
import controllers.UserController;
import domain.Hike;
import domain.Route;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.HikeService;
import services.RouteService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/route/user")
public class RouteUserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private RouteService routeService;

    @Autowired
    private HikeService hikeService;
    @Autowired
    private ActorService actorService;
    // Constructors -----------------------------------------------------------

    public RouteUserController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer pageSize) {
        ModelAndView result;
        Collection<Route> routes;
        result = new ModelAndView("route/list");
        try {
            routes = this.routeService.findByLogedActor();
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/route/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/route/list.do");
            }
        }
        result.addObject("routes", routes);
        result.addObject("requestUri", "route/user/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }



    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        Route route = routeService.create();
        result = this.createEditModelAndView(route);
        result.setViewName("route/create");
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int routeId, Integer pageSize, String word) {
        ModelAndView result;

        try {
            Route route = this.routeService.findOne(routeId);
            Assert.notNull(route, "msg.not.found.resource");
            Assert.notNull(route.getUser().equals((User)actorService.findByPrincipal()), "msg.not.owned.block");
            result = this.createEditModelAndView(route);
            result.addObject("display", false);
            result.addObject("pageSize", (pageSize != null) ? pageSize : 5);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/route/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/route/list.do");
            }
        }
        return result;
    }


    // Save mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Route route, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(route);
        else
            try {
                route = this.routeService.save(route);
                result = new ModelAndView("redirect:/route/user/list.do");
            } catch ( Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    return createMessageModelAndView(oops.getLocalizedMessage(), "/route/user/list.do");
                } else {
                    return this.createMessageModelAndView("msg.commit.error", "/");
                }
            }
        return result;
    }
// Delete mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@Valid Route route) {
        ModelAndView result = erase(route.getId());
        return result;
    }

    //  Delete mediante GET  -----------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView remove(@RequestParam int routeId) {
        ModelAndView result = erase(routeId);
        return result;
    }

    // Auxiliary methods -----------------------------------------------------

    protected ModelAndView erase(Integer routeId){
        ModelAndView result;
        try {
            this.routeService.delete(routeId);
            result = new ModelAndView("redirect:/route/user/list.do");
        } catch ( Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/route/user/list.do");
            } else {
                return this.createMessageModelAndView("msg.commit.error", "/");
            }
        }
        return result;
    }

    protected ModelAndView createEditModelAndView(Route model) {
         ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(Route model,  String message) {
        ModelAndView result;
        Collection<Hike> hikes =  hikeService.findByRoute(model);

        result = new ModelAndView("route/edit");
        result.addObject("route", model);
        result.addObject("hikes", hikes);
        result.addObject("requestUri", "route/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
