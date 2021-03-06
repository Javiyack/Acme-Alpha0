
package controllers;

import domain.Hike;
import domain.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.HikeService;
import services.RouteService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/route")
public class RouteController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private RouteService routeService;

    @Autowired
    private HikeService hikeService;
    // Constructors -----------------------------------------------------------

    public RouteController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;
        final Collection<Route> routes;
        routes = this.routeService.findAll();
        result = new ModelAndView("route/list");
        result.addObject("routes", routes);
        result.addObject("requestUri", "route/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int routeId) {
        ModelAndView result;
        try {
            final Route route = this.routeService.findOne(routeId);
            Assert.notNull(route, "msg.not.found.resource");
            result = this.createDisplaytModelAndView(route);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        return result;
    }


    protected ModelAndView createDisplaytModelAndView(final Route model) {
        final ModelAndView result;
        result = this.createDisplaytModelAndView(model, null);
        return result;
    }

    protected ModelAndView createDisplaytModelAndView(final Route model, final String message) {
        final ModelAndView result;
        Collection<Hike> hikes =  hikeService.findByRoute(model);
        result = new ModelAndView("route/create");
        result.addObject("route", model);
        result.addObject("hikes", hikes);
        result.addObject("requestUri", "route/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);
        result.addObject("display", true);
        return result;

    }


}
