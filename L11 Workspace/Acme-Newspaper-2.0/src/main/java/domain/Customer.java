
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "userAccount_id") })
public class Customer extends Actor {

	// Constructors

	public Customer() {
		super();
	}


	// Relationships

	private Collection<SubscriptionNewspaper>	subscriptionsNewspaper;
	private Collection<SubscriptionVolume>		subscriptionsVolume;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<SubscriptionNewspaper> getSubscriptionsNewspaper() {
		return this.subscriptionsNewspaper;
	}

	public void setSubscriptionsNewspaper(final Collection<SubscriptionNewspaper> subscriptionsNewspaper) {
		this.subscriptionsNewspaper = subscriptionsNewspaper;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<SubscriptionVolume> getSubscriptionsVolume() {
		return subscriptionsVolume;
	}

	public void setSubscriptionsVolume(
			Collection<SubscriptionVolume> subscriptionsVolume) {
		this.subscriptionsVolume = subscriptionsVolume;
	}
	
	

}
