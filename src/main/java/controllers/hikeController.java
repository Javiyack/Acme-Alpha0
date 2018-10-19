
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Constant;
import domain.Hike;
import services.ActorService;
import services.HikeService;
import services.RouteService;

@Controller
@RequestMapping("/hike")
public class hikeController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private RouteService routeService;
    @Autowired
    private HikeService hikeService;
    @Autowired
    private ActorService actorService;
    // Constructors -----------------------------------------------------------

    public hikeController() {
        super();
    }

    // Display -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int hikeId) {
        ModelAndView result;
        try {
            final Hike hike = this.hikeService.findOne(hikeId);
            Assert.notNull(hike, "msg.not.found.resource");
            result = this.createEditModelAndView(hike);
            result.addObject("display", true);
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/route/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/route/list.do");
            }
        }
        return result;
    }

    // Auxiliary methods -----------------------------------------------------

    protected ModelAndView createEditModelAndView(final Hike model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Hike model, final String message) {
        final ModelAndView result;
        Constant.difficultyLevels[] difficulties = Constant.difficultyLevels.values();
        List<String> difficultyLevels = new ArrayList<>();
        for (Constant.difficultyLevels level : difficulties) {
            difficultyLevels.add(level.toString());
        }
        result = new ModelAndView("hike/edit");
        result.addObject("hike", model);
        result.addObject("difficultyLevels", difficultyLevels);
        result.addObject("message", message);

        return result;

    }

}
