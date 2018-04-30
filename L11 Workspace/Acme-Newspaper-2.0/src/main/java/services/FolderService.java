
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	// Managed repository
	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	// Constructor
	public FolderService() {
		super();
	}

	// Simple CRUD methods
	public Folder create() {

		final Folder folder = new Folder();
		folder.setPredefined(false);
		folder.setMessages(new ArrayList<Message>());
		Actor actor;
		actor = this.actorService.findByPrincipal();
		final Collection<Folder> actorFolders = actor.getFolders();
		actorFolders.add(folder);
		actor.setFolders(actorFolders);
		return folder;
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		// Assert.isTrue(!folder.getSystemFolder());
		if (folder.getId() != 0)
			this.checkPrincipal(folder);
		Actor actor;
		actor = this.actorService.findByPrincipal();
		final Folder folderSaved = this.folderRepository.save(folder);
		final Collection<Folder> actorFolders = actor.getFolders();
		actorFolders.add(folderSaved);
		actor.setFolders(actorFolders);

		return folderSaved;
		// actorService.save(actor);

	}

	public void saveToMove(final Folder folder, final Folder targetFolder) {

		Assert.notNull(targetFolder);
		Assert.notNull(folder);
		folder.setParent(targetFolder);

	}

	public Folder simpleSave(final Folder f) {
		Assert.notNull(f);

		final Folder folderSaved = this.folderRepository.save(f);

		return folderSaved;

	}

	public Folder saveNotificationBox(final Folder folder) {
		Assert.notNull(folder);
		// Assert.isTrue(!folder.getSystemFolder());
		Actor actor;
		actor = this.actorService.findByPrincipal();
		final Folder folderSaved = this.folderRepository.save(folder);
		final Collection<Folder> actorFolders = actor.getFolders();
		actorFolders.add(folderSaved);
		actor.setFolders(actorFolders);

		return folderSaved;
	}

	public List<Folder> save(final Iterable<Folder> folders) {
		Assert.notNull(folders);
		return this.folderRepository.save(folders);

	}

	public Folder findOne(final int folderId) {
		Assert.notNull(folderId);
		Assert.isTrue(folderId != 0);
		Folder folder;
		folder = this.folderRepository.findOne(folderId);
		return folder;
	}

	public void delete(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(!folder.getPredefined());
		this.checkPrincipal(folder);
		final Collection<Message> messages = folder.getMessages();

		final Collection<Folder> childFolders = this.folderRepository.getChildFolders(folder.getId());

		if (childFolders.size() > 0)
			for (final Folder child : childFolders)
				this.delete(child);

		this.folderRepository.delete(folder);
		this.messageService.delete(messages);

	}

	public void delete(final Iterable<Folder> folders) {
		Assert.notNull(folders);

		this.folderRepository.delete(folders);
	}

	public Collection<Folder> findAll() {
		return this.folderRepository.findAll();
	}

	// Other business methods
	public void checkPrincipal(final Folder folder) {
		Actor actor;
		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getFolders().contains(folder));
	}

	// Other business methods
	public void createSystemFolders(final Actor actor) {

		Folder inbox;
		Folder outbox;
		Folder trashbox;
		Folder notificationbox;
		Folder spambox;
		final Collection<Folder> folders = new ArrayList<Folder>();

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

		final Folder savedinbox = this.folderRepository.save(inbox);
		final Folder savedoutbox = this.folderRepository.save(outbox);
		final Folder savedtrashbox = this.folderRepository.save(trashbox);
		final Folder savednotificationbox = this.folderRepository.save(notificationbox);
		final Folder savedspambox = this.folderRepository.save(spambox);

		folders.add(savedinbox);
		folders.add(savedoutbox);
		folders.add(savedtrashbox);
		folders.add(savednotificationbox);
		folders.add(savedspambox);

		actor.setFolders(folders);

	}

	public Folder getOutBoxFolderFromActorId(final int id) {
		return this.folderRepository.getOutBoxFolderFromActorId(id);
	}

	public Folder getInBoxFolderFromActorId(final int id) {
		return this.folderRepository.getInBoxFolderFromActorId(id);
	}

	public Folder getSpamBoxFolderFromActorId(final int id) {
		return this.folderRepository.getSpamBoxFolderFromActorId(id);
	}

	public Folder getTrashBoxFolderFromActorId(final int id) {
		return this.folderRepository.getTrashBoxFolderFromActorId(id);
	}

	public Folder getNotificationBoxFolderFromActorId(final int id) {
		return this.folderRepository.getNotificationBoxFolderFromActorId(id);
	}

	public Collection<Folder> getFirstLevelFoldersFromActorId(final int actorId) {
		return this.folderRepository.getFirstLevelFoldersFromActorId(actorId);
	}

	public Folder getFolderFromMessageId(final int messageId) {
		return this.folderRepository.getFolderFromMessageId(messageId);
	}

	public Collection<Folder> getChildFolders(final int folderId) {
		return this.folderRepository.getChildFolders(folderId);
	}

}
