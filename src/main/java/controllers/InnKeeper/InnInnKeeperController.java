
package controllers.InnKeeper;

import controllers.AbstractController;
import domain.Inn;
import domain.Inn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.InnService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/inn/innkeeper")
public class InnInnKeeperController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private InnService innService;

    // Constructors -----------------------------------------------------------

    public InnInnKeeperController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer pageSize) {
        ModelAndView result;
        final Collection<Inn> inns = innService.findAll();

        result = new ModelAndView("inn/list");
        result.addObject("routes", inns);
        result.addObject("requestUri", "inn/innkeeper/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int innId) {
        ModelAndView result;
        try{
            final Inn inn = this.innService.findOne(innId);
            Assert.notNull(inn, "msg.not.found.resource");
            result = new ModelAndView("inn/display");
            result.addObject("inn", inn);
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
        final Inn route = innService.create();
        result = this.createEditModelAndView(route);
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int innId) {
        ModelAndView result;

        try {
            final Inn inn = this.innService.findOne(innId);
            Assert.notNull(inn, "msg.not.found.resource");
            result = new ModelAndView("route/edit");
            result.addObject("inn", inn);
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
    public ModelAndView save(@Valid final Inn inn, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(inn);
        else
            try {
                this.innService.save(inn);
                result = new ModelAndView("redirect:/route/list.do");
            } catch (final Throwable oops) {
                if (oops.getCause().getCause() != null
                        && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                    result = this.createEditModelAndView(inn, "msg.duplicate.username");
                else
                    result = this.createEditModelAndView(inn, "msg.commit.error");
            }
        return result;
    }

    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final Inn model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Inn model, final String message) {
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
