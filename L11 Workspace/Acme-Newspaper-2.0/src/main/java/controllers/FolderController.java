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
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;

	public FolderController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int folderId) {
		ModelAndView result;
		Folder parentFolder = folderService.findOne(folderId);
		Folder folder;
		folder = folderService.create();
		folder.setParent(parentFolder);
		result = this.createEditModelAndView(folder, false);
		return result;
	}
	
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView createMove(@RequestParam int folderId) {
		ModelAndView result;
		Folder folder;
		
		folder = folderService.findOne(folderId);
		
		Actor principal = actorService.findByPrincipal();
		Collection<Folder> folders = principal.getFolders();
		result = new ModelAndView("folder/move");
		result.addObject("folder", folder);
		result.addObject("message", null);
		result.addObject("folders", folders);
		
		return result;

	}

	@RequestMapping(value = "/createFirst", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Folder folder;
		folder = folderService.create();
		result = this.createEditModelAndView(folder, true);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Folder folder, BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(folder, false);

		} else {

			try {

				folderService.save(folder);

				result = new ModelAndView("redirect:display.do?folderId="
						+ folder.getParent().getId());

			} catch (Throwable oops) {

				result = createEditModelAndView(folder, "ms.commit.error",
						false);

			}

		}
		return result;

	}
	
	@RequestMapping(value = "/saveMove", method = RequestMethod.GET)
	public ModelAndView saveMove(@RequestParam(required=true) int targetfolderId,
			@RequestParam(required=true) int folderId){
		ModelAndView result;
		Folder folder = folderService.findOne(folderId);
		Assert.notNull(folder);
		Folder targetFolder = folderService.findOne(targetfolderId);
		Assert.notNull(targetFolder);
		
		try {
			
			folderService.saveToMove(folder,targetFolder);
			result = new ModelAndView("redirect:/folder/display.do?folderId="+targetFolder.getId());

		} catch (Throwable oops) {
			Actor principal = actorService.findByPrincipal();
			Collection<Folder> folders = principal.getFolders();
			result = new ModelAndView("message/move");
			result.addObject("folder", folder);
			result.addObject("message", "ms.commit.error");
			result.addObject("folders", folders);

			

		}
		
		return result;
	}
	
	

	@RequestMapping(value = "/editFirst", method = RequestMethod.POST, params = "saveFirst")
	public ModelAndView saveFirst(@Valid Folder folder, BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(folder, true);

		} else {
			try {

				folderService.save(folder);

				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {

				result = createEditModelAndView(folder, "ms.commit.error", true);

			}

		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int folderId) {
		ModelAndView result;
		Folder folder;
		folder = folderService.findOne(folderId);
		Folder parentFolder = folder.getParent();

		if (folder.getParent()==null) {
			
			try {
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView(
						"redirect:/folder/display.do?folderId="
								+ folder.getId());

			}
			
		} else {

			try {
				this.folderService.delete(folder);

				result = new ModelAndView(
						"redirect:/folder/display.do?folderId="
								+ parentFolder.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView(
						"redirect:/folder/display.do?folderId="
								+ folder.getId());

			}
		}

		return result;

	}


	@RequestMapping(value = "/editFirst", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteFirst(Folder folder, BindingResult binding) {
		ModelAndView result;

		try {
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder,
					"application.commit.error", true);
		}

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Folder> folders;

		Actor actor = actorService.findByPrincipal();
		folders = folderService.getFirstLevelFoldersFromActorId(actor.getId());

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);

		return result;

	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int folderId) {

		ModelAndView result;
		Folder folder;
		Collection<Message> messages;
		Collection<Folder> folders;
		folder = folderService.findOne(folderId);
		messages = folder.getMessages();
		folders = folderService.getChildFolders(folderId);
		result = new ModelAndView("folder/display");
		result.addObject("folders", folders);
		result.addObject("messages", messages);
		result.addObject("folder", folder);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Folder folder,
			boolean isFirst) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null, isFirst);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder,
			final String messageCode, boolean isFirst) {

		ModelAndView result;
		if (isFirst) {
			result = new ModelAndView("folder/createFirst");
			result.addObject("folder", folder);
			result.addObject("message", messageCode);
		} else {
			result = new ModelAndView("folder/create");
			Folder parentFolder = folder.getParent();
			result.addObject("folder", folder);
			result.addObject("message", messageCode);
			result.addObject("parentFolder", parentFolder);
		}

		return result;
	}
}
