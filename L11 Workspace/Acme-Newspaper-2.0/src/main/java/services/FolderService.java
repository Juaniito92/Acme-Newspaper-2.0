package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;
import repositories.FolderRepository;

@Service
@Transactional
public class FolderService {

	// Managed repository
	@Autowired
	private FolderRepository folderRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageService messageService;

	// Constructor
	public FolderService() {
		super();
	}

	// Simple CRUD methods
	public Folder create() {

		Folder folder = new Folder();
		folder.setPredefined(false);
		folder.setMessages(new ArrayList<Message>());
		Actor actor;
		actor = actorService.findByPrincipal();
		Collection<Folder> actorFolders = actor.getFolders();
		actorFolders.add(folder);
		actor.setFolders(actorFolders);
		return folder;
	}

	public Folder save(Folder folder) {
		Assert.notNull(folder);
		// Assert.isTrue(!folder.getSystemFolder());
		if (folder.getId() != 0) {
			checkPrincipal(folder);
		}
		Actor actor;
		actor = actorService.findByPrincipal();
		Folder folderSaved = folderRepository.save(folder);
		Collection<Folder> actorFolders = actor.getFolders();
		actorFolders.add(folderSaved);
		actor.setFolders(actorFolders);

		return folderSaved;
		// actorService.save(actor);

	}

	public void saveToMove(Folder folder, Folder targetFolder) {

		Assert.notNull(targetFolder);
		Assert.notNull(folder);
		folder.setParent(targetFolder);

	}

	public Folder simpleSave(Folder f) {
		Assert.notNull(f);

		Folder folderSaved = folderRepository.save(f);

		return folderSaved;

	}

	public Folder saveNotificationBox(Folder folder) {
		Assert.notNull(folder);
		// Assert.isTrue(!folder.getSystemFolder());
		Actor actor;
		actor = actorService.findByPrincipal();
		Folder folderSaved = folderRepository.save(folder);
		Collection<Folder> actorFolders = actor.getFolders();
		actorFolders.add(folderSaved);
		actor.setFolders(actorFolders);

		return folderSaved;
	}

	public List<Folder> save(Iterable<Folder> folders) {
		Assert.notNull(folders);
		return folderRepository.save(folders);

	}

	public Folder findOne(int folderId) {
		Assert.notNull(folderId);
		Assert.isTrue(folderId != 0);
		Folder folder;
		folder = folderRepository.findOne(folderId);
		return folder;
	}

	public void delete(Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(!folder.getPredefined());
		checkPrincipal(folder);
		Collection<Message> messages = folder.getMessages();

		Collection<Folder> childFolders = folderRepository.getChildFolders(folder.getId());

		if (childFolders.size() > 0) {
			for (Folder child : childFolders) {
				this.delete(child);
			}
		}

		folderRepository.delete(folder);
		messageService.delete(messages);

	}

	public void delete(Iterable<Folder> folders) {
		Assert.notNull(folders);

		folderRepository.delete(folders);
	}

	public Collection<Folder> findAll() {
		return folderRepository.findAll();
	}

	// Other business methods
	public void checkPrincipal(Folder folder) {
		Actor actor;
		actor = actorService.findByPrincipal();
		Assert.isTrue(actor.getFolders().contains(folder));
	}

	// Other business methods
	public void createSystemFolders(Actor actor) {

		Folder inbox;
		Folder outbox;
		Folder trashbox;
		Folder notificationbox;
		Folder spambox;
		Collection<Folder> folders = new ArrayList<Folder>();

		inbox = new Folder();
		inbox.setPredefined(true);
		inbox.setName("in box");
		inbox.setMessages(new ArrayList<Message>());

		outbox = new Folder();
		outbox.setPredefined(true);
		outbox.setName("out box");
		outbox.setMessages(new ArrayList<Message>());

		trashbox = new Folder();
		trashbox.setPredefined(true);
		trashbox.setName("trash box");
		trashbox.setMessages(new ArrayList<Message>());

		notificationbox = new Folder();
		notificationbox.setPredefined(true);
		notificationbox.setName("notification box");
		notificationbox.setMessages(new ArrayList<Message>());

		spambox = new Folder();
		spambox.setPredefined(true);
		spambox.setName("spam box");
		spambox.setMessages(new ArrayList<Message>());

		Folder savedinbox = folderRepository.save(inbox);
		Folder savedoutbox = folderRepository.save(outbox);
		Folder savedtrashbox = folderRepository.save(trashbox);
		Folder savednotificationbox = folderRepository.save(notificationbox);
		Folder savedspambox = folderRepository.save(spambox);

		folders.add(savedinbox);
		folders.add(savedoutbox);
		folders.add(savedtrashbox);
		folders.add(savednotificationbox);
		folders.add(savedspambox);

		actor.setFolders(folders);

	}

	public Folder getOutBoxFolderFromActorId(int id) {
		return folderRepository.getOutBoxFolderFromActorId(id);
	}

	public Folder getInBoxFolderFromActorId(int id) {
		return folderRepository.getInBoxFolderFromActorId(id);
	}

	public Folder getSpamBoxFolderFromActorId(int id) {
		return folderRepository.getSpamBoxFolderFromActorId(id);
	}

	public Folder getTrashBoxFolderFromActorId(int id) {
		return folderRepository.getTrashBoxFolderFromActorId(id);
	}

	public Folder getNotificationBoxFolderFromActorId(int id) {
		return folderRepository.getNotificationBoxFolderFromActorId(id);
	}

	public Collection<Folder> getFirstLevelFoldersFromActorId(int actorId) {
		return folderRepository.getFirstLevelFoldersFromActorId(actorId);
	}

	public Folder getFolderFromMessageId(int messageId) {
		return folderRepository.getFolderFromMessageId(messageId);
	}

	public Collection<Folder> getChildFolders(int folderId) {
		return folderRepository.getChildFolders(folderId);
	}

}
