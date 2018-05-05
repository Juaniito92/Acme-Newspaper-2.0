package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NotificationService;
import controllers.AbstractController;
import domain.Message;
import domain.Notification;

@Controller
@RequestMapping("/notification")
public class NotificationAdministratorController extends AbstractController {

	@Autowired
	private NotificationService notificationService;



	public NotificationAdministratorController() {
		super();
	}

	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
	public ModelAndView notification() {
		ModelAndView result;
		Notification notification;
		notification = notificationService.create();
		result = this.createEditModelAndView(notification);
		return result;
	}
	
	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Notification notification, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(notification);

		} else {
			try {

				notificationService.sendNotification(notification);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {

				result = createEditModelAndView(notification, "ms.commit.error");

			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Notification notification) {
		ModelAndView result;

		result = this.createEditModelAndView(notification, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Notification notification,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("notification/create");
		result.addObject("notification", notification);
		result.addObject("message", messageCode);

		return result;
	}

}
