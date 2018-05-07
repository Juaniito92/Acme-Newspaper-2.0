
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
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
		Assert.isTrue(this.checkExpiration(advertisement.getCreditCard()), "cardExpireError");
		Advertisement res;
		res = this.advertisementRepository.save(advertisement);

		return res;
	}

	public void delete(Advertisement ad) {
		Assert.notNull(ad);

		Newspaper newspaper = this.newspaperService.findOne(ad.getNewspaper().getId());
		Collection<Advertisement> ads = newspaper.getAdvertisements();
		ads.remove(ad);
		newspaper.setAdvertisements(ads);

		//this.newspaperService.save(newspaper);
		this.advertisementRepository.delete(ad);

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

	public Advertisement getRandomForNewspaper(int newspaperId) {
		Random rn = new Random();
		Advertisement ans = null;
		Collection<Advertisement> ads = this.advertisementRepository.findRandomForNewspaper(newspaperId);
		if (ads.size() > 0)
			ans = (Advertisement) ads.toArray()[rn.nextInt(ads.size())];
		return ans;
	}

	public Collection<Advertisement> getAdvertisementsTabooWords() {
		String pattern = "^";
		for (String tabooWord : this.configService.getTabooWordsFromConfiguration())
			pattern += ".*" + tabooWord + ".*" + "|";
		pattern = pattern.substring(0, pattern.length() - 1);
		pattern += "$";

		List<Advertisement> ans = new ArrayList<Advertisement>();
		for (Advertisement a : this.advertisementRepository.findAll()) {
			if (a.getTitle().matches(pattern))
				ans.add(a);
		}
		return new HashSet<Advertisement>(ans);
	}

	//TODO Arreglar query de Lucene
	//	public Collection<Advertisement> getAdvertisementsTabooWords() {
	//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Acme-Newspaper-2.0");
	//		EntityManager mgr = factory.createEntityManager();
	//		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(mgr);
	//		mgr.getTransaction().begin();
	//		String pattern = "";
	//
	//		for (String tabooWord : this.configService.getTabooWordsFromConfiguration())
	//			pattern += tabooWord + "|";
	//
	//		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Advertisement.class).get();
	//		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title").ignoreFieldBridge().matching(pattern).createQuery();
	//		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Advertisement.class);
	//		@SuppressWarnings("unchecked")
	//		List<Advertisement> res = jpaQuery.getResultList();
	//		Set<Advertisement> cc = new HashSet<>(res);
	//		mgr.getTransaction().commit();
	//
	//		mgr.close();
	//
	//		System.out.println(cc);
	//
	//		return cc;
	//	}

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

	public boolean checkExpiration(CreditCard c) {
		Boolean res = true;

		if ((c.getExpirationYear() == LocalDate.now().getYear() && (c.getExpirationMonth() == LocalDate.now().getMonthOfYear() || c.getExpirationMonth() < LocalDate.now().getMonthOfYear())) || c.getExpirationYear() < LocalDate.now().getYear()) {
			res = false;
		}

		return res;
	}

}
