
package controllers.administrator;

import controllers.AbstractController;
import controllers.ActorController;
import domain.Actor;
import domain.Route;
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
import services.RouteService;

import java.util.Collection;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

    // Supporting services -----------------------------------------------------
    @Autowired
    protected ActorService actorService;

    // Constructors -----------------------------------------------------------

    public ActorAdministratorController() {
        super();
    }



    // Activation  -----------------------------------------------------------
    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView activation(@RequestParam final int actorId, final Integer pageSize, final Integer word) {
        ModelAndView result;

        try {
            this.actorService.activation(actorId);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        result = new ModelAndView("redirect:/actor/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

}
