package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	// Constructors

	public Advertisement() {
		super();
	}

	// Attributes

	private String title;
	private String banner;
	private String page;
	private CreditCard creditCard;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@URL
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@URL
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	// Relationships

	private Agent agent;
	private Newspaper newspaper;

	@Valid
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Valid
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
