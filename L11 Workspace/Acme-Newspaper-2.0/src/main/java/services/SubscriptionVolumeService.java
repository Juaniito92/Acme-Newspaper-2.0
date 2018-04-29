package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import repositories.SubscriptionVolumeRepository;
import domain.CreditCard;
import domain.SubscriptionVolume;

@Service
@Transactional
public class SubscriptionVolumeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionVolumeRepository subscriptionVolumeRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VolumeService volumeService;

	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------

	public SubscriptionVolumeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SubscriptionVolume create(int volumeId){
		
		SubscriptionVolume result = new SubscriptionVolume();
		result.setCreditCard(new CreditCard());
		result.setCustomer(this.customerService.findByPrincipal());
		result.setVolume(this.volumeService.findOne(volumeId));
		
		return result;
	}
	
	public Collection<SubscriptionVolume> findAll(){
		
		Collection<SubscriptionVolume> result = subscriptionVolumeRepository.findAll();
		return result;
	}
	
	public SubscriptionVolume findOne(int subscriptionVolumeId){
		
		SubscriptionVolume result = subscriptionVolumeRepository.findOne(subscriptionVolumeId);
		return result;
	}
	
	public SubscriptionVolume save(SubscriptionVolume subscriptionVolume){
		
		SubscriptionVolume result = subscriptionVolumeRepository.save(subscriptionVolume);
		result.getCustomer().getSubscriptionsVolume().add(result);
		result.getVolume().getSubscriptionsVolume().add(result);
		return result;
	}
	
	public void delete(SubscriptionVolume subscriptionVolume){
		
		subscriptionVolume.getCustomer().getSubscriptionsVolume().remove(subscriptionVolume);
		subscriptionVolume.getVolume().getSubscriptionsVolume().remove(subscriptionVolume);
	}
	
	// Other bussiness methods ----------------------------------------------------
	
	
}
