package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.VolumeService;
import domain.Volume;

@Controller
@RequestMapping("/volume")
public class VolumeController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private VolumeService volumeService;
	
	// Constructors --------------------------------------------------

	public VolumeController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Volume> volumes;

		volumes = volumeService.findAll();

		ModelAndView result = new ModelAndView("volume/list");
		result.addObject("volumes", volumes);
		result.addObject("requestURI", "volume/list.do");

		return result;
	}

}
