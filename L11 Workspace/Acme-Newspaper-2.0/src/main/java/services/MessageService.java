package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;
import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	// Managed repository
	@Autowired
	private MessageRepository messageRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private AdminService administratorService;

	// Constructor
	public MessageService() {
		super();
	}

	// Simple CRUD methods
	public Message create() {
		Message res;
		Date moment;
		res = new Message();
		Actor actor = actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1);
		res.setSender(actor);
		res.setMoment(moment);
		return res;
	}

	// TODO lo que se guarda en el outbox al enviar un mensaje es una copia, no
	// el mensaje
	public Message save(Message message) {
		Assert.notNull(message);
		Assert.notNull(message.getRecipient());
		Folder recipientFolder;

		Message saved;
		saved = messageRepository.save(message);

		Message copiedMessage = message;
		Message copiedAndSavedMessage = messageRepository.save(copiedMessage);

		if (administratorService.checkIsSpam(saved.getBody()) || administratorService.checkIsSpam(saved.getSubject())) {
			// instancio el Folder del destinatario como el spambox
			recipientFolder = folderService.getSpamBoxFolderFromActorId(saved.getRecipient().getId());
		} else {// si no contiene spam
			// instancio el Folder del destinatario como inbox
			recipientFolder = folderService.getInBoxFolderFromActorId(saved.getRecipient().getId());
		}

		Collection<Message> recipientFolderMessages = recipientFolder.getMessages();
		recipientFolderMessages.add(saved);
		recipientFolder.setMessages(recipientFolderMessages);
		Actor sender = actorService.findByPrincipal();
		Folder senderOutbox = folderService.getOutBoxFolderFromActorId(sender.getId());
		Collection<Message> senderOutboxMessages = senderOutbox.getMessages();

		senderOutboxMessages.add(copiedAndSavedMessage);
		senderOutbox.setMessages(senderOutboxMessages);

		return saved;
	}

	public void saveToMove(Message message, Folder folder) {

		Assert.notNull(message);
		Assert.notNull(folder);

		Folder currentFolder = folderService.getFolderFromMessageId(message.getId());
		Collection<Message> currentFolderMessages = currentFolder.getMessages();
		currentFolderMessages.remove(message);
		currentFolder.setMessages(currentFolderMessages);
		folderService.simpleSave(currentFolder);

		// this.messageRepository.delete(message.getId());

		// Message savedCopy = this.messageRepository.save(copy);

		// Message saved = this.messageRepository.save(message);
		Collection<Message> folderMessages = folder.getMessages();
		folderMessages.add(message);
		folder.setMessages(folderMessages);
		folderService.simpleSave(folder);

		// this.messageRepository.save(message);

	}

	public void delete(Message message) {
		Assert.notNull(message);
		Actor actor = actorService.findByPrincipal();

		// cojo el trashbox del actor logueado
		Folder trashbox = folderService.getTrashBoxFolderFromActorId(actor.getId());
		Collection<Message> trashboxMessages = trashbox.getMessages();

		Folder messageFolder = folderService.getFolderFromMessageId(message.getId());
		Collection<Message> folderMessages = messageFolder.getMessages();
		// Compruebo que el trashbox del actor logueado no sea nulo
		Assert.notNull(trashbox);
		// si el mensaje que me pasan está en el trashbox del actor logueado:
		if (trashboxMessages.contains(message)) {
			trashboxMessages.remove(message);
			trashbox.setMessages(trashboxMessages);
			messageRepository.delete(message);

		} else {// si el mensaje que se quiere borrar no está en el trashbox:

			// Borro el mensaje del folder en el que estaba

			Assert.notNull(messageFolder);
			folderMessages.remove(message);
			messageFolder.setMessages(folderMessages);

			trashboxMessages.add(message);
			trashbox.setMessages(trashboxMessages);

		}
	}

	public void delete(Iterable<Message> messages) {
		Assert.notNull(messages);
		messageRepository.delete(messages);
	}

	public Message findOne(int messageId) {
		return messageRepository.findOne(messageId);

	}

	public Collection<Message> findAll() {
		return messageRepository.findAll();

	}

	// Other business methods

}
