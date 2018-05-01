
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.Newspaper;

@Service
@Transactional
public class AdvertisementService {

	// Managed repository
	@Autowired
	private AdvertisementRepository	advertisementRepository;

	// Supporting services
	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private AgentService			agentService;

	@Autowired
	private ConfigurationService	configService;


	// Constructors
	public AdvertisementService() {
		super();
	}

	// Simple CRUD methods
	public Advertisement create(final int newspaperId) {

		Assert.notNull(this.agentService.findByPrincipal());

		final Advertisement res = new Advertisement();
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		Assert.notNull(newspaper);

		final Agent agent = this.agentService.findByPrincipal();
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

	public Collection<Advertisement> getAdvertisementsTabooWords() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Acme-Newspaper-2.0");
		EntityManager mgr = factory.createEntityManager();
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(mgr);
		mgr.getTransaction().begin();
		String pattern = "";

		for (String tabooWord : this.configService.getTabooWordsFromConfiguration())
			pattern += tabooWord + "|";
		pattern = pattern.substring(0, pattern.length() - 1);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Advertisement.class).get();
		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title").ignoreFieldBridge().matching(pattern).createQuery();
		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Advertisement.class);
		@SuppressWarnings({
			"rawtypes"
		})
		List res = jpaQuery.getResultList();
		@SuppressWarnings("unchecked")
		Set<Advertisement> cc = new HashSet<>(res);
		mgr.getTransaction().commit();

		mgr.close();

		return cc;
	}

	public Advertisement findOneToEdit(final int sponsorshipId) {
		Advertisement result;
		result = this.advertisementRepository.findOne(sponsorshipId);
		this.checkPrincipal(result);
		return result;
	}

	public void checkPrincipal(Advertisement s) {
		Agent agent;

		agent = this.agentService.findByPrincipal();

		Assert.isTrue(agent.getAdvertisements().contains(s));
	}

}
