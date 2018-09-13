
package controllers.InnKeeper;

import controllers.AbstractController;
import domain.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.RouteService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/inn/innkeeper")
public class InnInnKeeperController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private RouteService routeService;

    // Constructors -----------------------------------------------------------

    public InnInnKeeperController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer userId, final Integer pageSize) {
        ModelAndView result;
        final Collection<Route> routes;
        if (userId != null) {
            routes = this.routeService.findByUserId(userId);

        } else {
            routes = this.routeService.findAll();

        }
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
            result = new ModelAndView("route/display");
            result.addObject("route", route);
            result.addObject("display", true);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        return result;
    }

    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        final Route route = routeService.create();
        result = this.createEditModelAndView(route);
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int routeId) {
        ModelAndView result;

        try {
            final Route route = this.routeService.findOne(routeId);
            Assert.notNull(route, "msg.not.found.resource");
            result = new ModelAndView("route/edit");
            result.addObject("route", route);
            result.addObject("display", false);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        return result;
    }


    // Save mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Route route, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(route);
        else
            try {
                this.routeService.save(route);
                result = new ModelAndView("redirect:/route/list.do");
            } catch (final Throwable oops) {
                if (oops.getCause().getCause() != null
                        && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                    result = this.createEditModelAndView(route, "msg.duplicate.username");
                else
                    result = this.createEditModelAndView(route, "msg.commit.error");
            }
        return result;
    }

    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final Route model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Route model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("route/create");
        result.addObject("route", model);
        result.addObject("requestUri", "route/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
