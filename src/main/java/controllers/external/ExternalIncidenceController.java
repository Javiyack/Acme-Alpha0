package controllers.external;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Incidence;
import domain.Labor;
import domain.Technician;
import domain.User;
import forms.IncidenceForm;
import services.IncidenceService;
import services.LaborService;
import services.TechnicianService;
import services.UserService;

@Controller
@RequestMapping("/incidence/external")
public class ExternalIncidenceController extends AbstractController {

	// Services
	@Autowired
	private IncidenceService incidenceService;
	@Autowired
	private TechnicianService technicianService;
	@Autowired
	private UserService userService;
	@Autowired
	private LaborService laborService;

	// Constructor

	public ExternalIncidenceController() {
		super();
	}

	// List All incidences
	// ---------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;
		final Collection<Incidence> inidencias = this.incidenceService.findAllByActor();

		result = new ModelAndView("incidence/list");
		result.addObject("incidences", inidencias);
		result.addObject("requestUri", "incidence/external/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 20);
		return result;
	}

	// Create incidence
	// ---------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		final Incidence incidencia = this.incidenceService.create();
		final IncidenceForm incidence = new IncidenceForm(incidencia);

		result = this.createEditModelAndView(incidence);

		return result;
	}

	// Edit incidence
	// ---------------------------------------------------------------
	@RequestMapping("/edit")
	public ModelAndView edit(@Valid final int id) {
		ModelAndView result;
		final Incidence incidencia = this.incidenceService.findOne(id);

		final IncidenceForm incidence = new IncidenceForm(incidencia);
		result = this.createEditModelAndView(incidence);
		Collection<Labor> labors = laborService.findByIncidence(id);
		result.addObject("labors", labors);
		return result;
	}

	// Edit incidence
	// ---------------------------------------------------------------
	@RequestMapping("/display")
	public ModelAndView display(@Valid final int id) {
		ModelAndView result;
		final Incidence incidencia = this.incidenceService.findOne(id);

		final IncidenceForm incidence = new IncidenceForm(incidencia);
		result = this.createEditModelAndView(incidence);
		result.addObject("display", true);
		Collection<Labor> labors = laborService.findByIncidence(id);
		result.addObject("labors", labors);
		return result;
	}

	// Save incidence
	// ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final IncidenceForm incidence, final BindingResult binding) {
		ModelAndView result;
		Incidence incidencia = incidenceService.recontruct(incidence, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(incidence);
		else
			try {
				this.incidenceService.save(incidencia);
				result = new ModelAndView("redirect:/incidence/external/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("msg.not.loged.block"))
					result = this.createEditModelAndView(incidence, "msg.not.loged.block");
				else if (oops.getMessage().equals("msg.not.owned.block"))
					result = this.createEditModelAndView(incidence, "msg.not.owned.block");
				else
					result = this.createEditModelAndView(incidence, "msg.commit.error");
			}
		return result;
	}

	// Save incidence
	// ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final IncidenceForm incidence, final BindingResult binding) {
		ModelAndView result;

		try {
			this.incidenceService.delete(incidence);
			result = new ModelAndView("redirect:/incidence/external/list.do");

		} catch (final Throwable ooops) {
			if (ooops.getMessage().equals("msg.not.loged.block"))
				result = this.createEditModelAndView(incidence, "msg.not.loged.block");
			else if (ooops.getMessage().equals("msg.not.owned.block"))
				result = this.createEditModelAndView(incidence, "msg.not.owned.block");
			else
				result = this.createEditModelAndView(incidence, "msg.commit.error");
		}
		return result;
	}

	// M�todos auxiliares
	// ----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final IncidenceForm inidence) {
		final ModelAndView result;
		result = this.createEditModelAndView(inidence, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final IncidenceForm incidence, final String message) {
		final ModelAndView result;

		Collection<Technician> techs = technicianService.findAll();
		Collection<User> users = userService.findAllByPrincipal();

		result = new ModelAndView("incidence/edit");
		result.addObject("incidenceForm", incidence);
		result.addObject("technicians", techs);
		result.addObject("users", users);
		result.addObject("message", message);
		result.addObject("requestUri", "incidence/external/edit.do");

		return result;

	}
}
