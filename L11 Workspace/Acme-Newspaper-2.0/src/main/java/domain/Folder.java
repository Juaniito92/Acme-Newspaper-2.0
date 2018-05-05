
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	// Constructor
	// ----------------------------------------------------------------------------
	public Folder() {
		super();
	}

	// Attributes
	// ------------------------------------------------------------------------------
	private String name;
	private boolean predefined;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getPredefined() {
		return this.predefined;
	}

	public void setPredefined(final boolean predefined) {
		this.predefined = predefined;
	}

	// RelashionShips
	// ----------------------------------------------------------------

	private Folder parent;
	private Collection<Message> messages;
	private Actor actor;
	private Collection<Notification> notifications;

	@OneToOne(optional = true)
	public Folder getParent() {
		return this.parent;
	}

	public void setParent(final Folder parent) {
		this.parent = parent;
	}

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.ALL)
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@Valid
	@OneToMany
	public Collection<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Collection<Notification> notifications) {
		this.notifications = notifications;
	}
}
