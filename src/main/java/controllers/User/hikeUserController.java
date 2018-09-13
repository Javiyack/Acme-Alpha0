
package controllers.User;

import controllers.AbstractController;
import domain.Constant;
import domain.Hike;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/hike/user")
public class hikeUserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private HikeService hikeService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private ActorService actorService;
    // Constructors -----------------------------------------------------------

    public hikeUserController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list( Integer routeId,  Integer pageSize) {
        ModelAndView result;
         Collection<Hike> routes;
        result = new ModelAndView("hike/list");
        try {
            routes = this.hikeService.findByRouteId(routeId);
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/route/user/edit.do?routeId=" + routeId);
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
    public ModelAndView create(int routeId) {
        ModelAndView result;
         Hike hike = hikeService.create(routeService.findOne(routeId));
        result = this.createEditModelAndView(hike);
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int hikeId) {
        ModelAndView result;

        try {
             Hike hike = this.hikeService.findOne(hikeId);
            Assert.notNull(hike, "msg.not.found.resource");
            Assert.notNull(hike.getRoute().getUser().equals((User) actorService.findByPrincipal()), "msg.not.owned.block");
            result = this.createEditModelAndView(hike);
            result.addObject("display", false);

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
    public ModelAndView save(@Valid Hike hike, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(hike);
        else
            try {
                hike = this.hikeService.save(hike);
                result = new ModelAndView("redirect:/route/user/edit.do?routeId=" + hike.getRoute().getId());
            } catch ( Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    return createMessageModelAndView(oops.getLocalizedMessage(), "/route/user/edit.do?routeId=" + hike.getRoute().getId());
                } else {
                    return this.createMessageModelAndView("msg.commit.error", "/route/user/edit.do?routeId=" + hike.getRoute().getId());
                }
            }
        return result;
    }
// Delete mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@Valid  Hike hike) {
        ModelAndView result = erase(hike.getId(), hike.getRoute().getId());
        return result;
    }

    //  Delete mediante GET  -----------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView remove(@RequestParam  int hikeId,  int routeId) {
        ModelAndView result = erase(hikeId, routeId);
        return result;
    }

    // Auxiliary methods -----------------------------------------------------

    protected ModelAndView erase( Integer hikeId,  Integer routeId) {
        ModelAndView result;
        try {
            this.hikeService.delete(hikeId);
            result = new ModelAndView("redirect:/route/user/edit.do?routeId=" + routeId);
        } catch ( Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/route/user/edit.do?routeId=" + routeId);
            } else {
                return this.createMessageModelAndView("msg.commit.error", "/route/user/edit.do?routeId=" + routeId);
            }
        }
        return result;
    }


    protected ModelAndView createEditModelAndView( Hike model) {
         ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView( Hike model,  String message) {
         ModelAndView result;
        Constant.difficultyLevels[] difficulties = Constant.difficultyLevels.values();
        List<String> difficultyLevels = new ArrayList<>();

        for (Constant.difficultyLevels level:difficulties) {
            difficultyLevels.add(level.toString());
        }
        result = new ModelAndView("hike/create");
        result.addObject("hike", model);
        result.addObject("difficultyLevels", difficultyLevels);
        result.addObject("requestUri", "hike/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
