package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.SubscriptionNewspaper;

@Service
@Transactional
public class SubscriptionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------

	public SubscriptionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<SubscriptionNewspaper> findAll() {

		Collection<SubscriptionNewspaper> result = subscriptionRepository.findAll();
		return result;
	}

	public void delete(SubscriptionNewspaper subscription) {

		Assert.notNull(subscription);
		Assert.isTrue(subscription.getId() != 0);

		subscription.getCustomer().getSubscriptionsNewspaper().remove(subscription);
		subscriptionRepository.delete(subscription);

	}
}
