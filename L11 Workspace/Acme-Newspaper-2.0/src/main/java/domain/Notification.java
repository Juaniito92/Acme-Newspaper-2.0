package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity {

	public Notification() {
		super();
	}

	private String body;
	private Admin sender;

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Admin getSender() {
		return sender;
	}

	public void setSender(Admin sender) {
		this.sender = sender;
	}

}
