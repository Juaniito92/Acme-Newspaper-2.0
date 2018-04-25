package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Advertisement;
import domain.Agent;
import domain.Newspaper;
import repositories.AdvertisementRepository;

@Service
@Transactional
public class AdvertisementService {

	// Managed repository
	@Autowired
	private AdvertisementRepository advertisementRepository;

	// Supporting services
	@Autowired
	private NewspaperService newspaperService;

	@Autowired
	private AgentService agentService;

	// Constructors
	public AdvertisementService() {
		super();
	}

	// Simple CRUD methods
	public Advertisement create(final int newspaperId) {

		Assert.notNull(agentService.findByPrincipal());

		Advertisement res = new Advertisement();
		Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		Assert.notNull(newspaper);

		Agent agent = agentService.findByPrincipal();
		res.setAgent(agent);
		res.setNewspaper(newspaper);
		return res;
	}

	public Advertisement save(final Advertisement advertisement) {
		Assert.notNull(advertisement);
		Advertisement res;
		res = this.advertisementRepository.save(advertisement);

		return res;
	}

	public Collection<Advertisement> findAll() {
		Collection<Advertisement> res = new ArrayList<Advertisement>();

		res = this.advertisementRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Advertisement findOne(final int advertisementId) {
		Assert.isTrue(advertisementId != 0);
		Advertisement res;

		res = this.advertisementRepository.findOne(advertisementId);

		return res;
	}

}
