package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private FolderService folderService;

	public MessageController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message m;
		m = messageService.create();
		result = this.createEditModelAndView(m);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Message m, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(m);

		} else {
			try {

				messageService.save(m);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {

				result = createEditModelAndView(m, "ms.commit.error");

			}
		}

		return result;
	}
	

	@RequestMapping(value = "/saveMove", method = RequestMethod.GET)
	public ModelAndView saveMove(@RequestParam(required=true) int messageId,
			@RequestParam(required=true) int folderId){
		ModelAndView result;
		Message m = messageService.findOne(messageId);
		Assert.notNull(m);
		Folder choosedFolder = folderService.findOne(folderId);
		Assert.notNull(choosedFolder);
		
		try {
			
			messageService.saveToMove(m,choosedFolder);
			result = new ModelAndView("redirect:/folder/display.do?folderId="+choosedFolder.getId());

		} catch (Throwable oops) {
			Actor principal = actorService.findByPrincipal();
			Collection<Folder> folders = principal.getFolders();
			result = new ModelAndView("message/move");
			result.addObject("m", m);
			result.addObject("message", "ms.commit.error");
			result.addObject("folders", folders);

			

		}
		
		return result;
	}
	

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId) {
		ModelAndView result;
		Message m;
		
		m = messageService.findOne(messageId);
		Folder folder = folderService.getFolderFromMessageId(m.getId());
		try {
			this.messageService.delete(m);
			result = new ModelAndView("redirect:/folder/display.do?folderId="
					+ folder.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/message/display.do?messageId="
					+ m.getId());

		}

		return result;

	}
	
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView createMove(@RequestParam int messageId) {
		ModelAndView result;
		Message m;
		Folder f;
		
		f = folderService.getFolderFromMessageId(messageId);
		m = messageService.findOne(messageId);
		Actor principal = actorService.findByPrincipal();
		Collection<Folder> folders = principal.getFolders();
		result = new ModelAndView("message/move");
		result.addObject("m", m);
		result.addObject("folder", f);
		result.addObject("message", null);
		result.addObject("folders", folders);
		
		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageId) {

		ModelAndView result;
		Message m;
		Folder folder;

		m = messageService.findOne(messageId);
		folder = folderService.getFolderFromMessageId(messageId);

		result = new ModelAndView("message/display");
		result.addObject("m", m);
		result.addObject("folder", folder);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message m) {
		ModelAndView result;

		result = this.createEditModelAndView(m, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m,
			final String messageCode) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();
		result = new ModelAndView("message/create");
		result.addObject("m", m);
		result.addObject("message", messageCode);
		result.addObject("actors", actors);

		return result;
	}

}
