
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import domain.Advertisement;

@Controller
@RequestMapping("/advertisement/admin")
public class AdvertisementAdminController {

	@Autowired
	private AdvertisementService	advertisementService;


	// Constructor
	// -------------------------------------------------------------------

	public AdvertisementAdminController() {
		super();
	}

	// List
	// -------------------------------------------------------------------

	@RequestMapping(value = "/listTaboo", method = RequestMethod.GET)
	public ModelAndView listTaboo() {
		ModelAndView res;
		Collection<Advertisement> advertisements;
		advertisements = this.advertisementService.getAdvertisementsTabooWords();

		res = new ModelAndView("advertisement/list");
		res.addObject("requestURI", "advertisement/list.do");
		res.addObject("advertisements", advertisements);

		return res;
	}

	// Display -----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int advertisementId) {
		ModelAndView result;
		Advertisement s;

		s = this.advertisementService.findOne(advertisementId);

		result = new ModelAndView("advertisement/display");
		result.addObject("advertisement", s);

		return result;
	}

}