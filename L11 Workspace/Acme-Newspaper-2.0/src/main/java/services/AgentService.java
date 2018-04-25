package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Advertisement;
import domain.Agent;
import domain.Folder;
import domain.User;
import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AgentService {

	// Managed repository

	@Autowired
	private AgentRepository agentRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;

	// @Autowired
	// private Validator validator;

	// Constructors

	public AgentService() {
		super();
	}

	// Simple CRUD methods

	public Agent create() {

		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final Agent res = new Agent();

		final UserAccount agentAccount = new UserAccount();
		final Authority authority = new Authority();
		Collection<Folder> folders = new ArrayList<Folder>();
		Collection<Advertisement> advertisements = new ArrayList<Advertisement>();

		authority.setAuthority(Authority.AGENT);
		agentAccount.addAuthority(authority);
		res.setUserAccount(agentAccount);

		res.setFolders(folders);
		res.setAdvertisements(advertisements);

		return res;
	}

	public Collection<Agent> findAll() {
		Collection<Agent> res;
		res = this.agentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Agent findOne(final int agentId) {
		Assert.isTrue(agentId != 0);
		Agent res;
		res = this.agentRepository.findOne(agentId);
		return res;
	}

	public Agent save(final Agent agent) {
		Agent res;

		if (agent.getId() == 0) {
			String pass = agent.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			agent.getUserAccount().setPassword(pass);
		}
		res = this.agentRepository.save(agent);
		return res;
	}

	// Other business methods

	public Agent findAgentByUserAccountId(int uA) {
		return this.agentRepository.findAgentByUserAccountId(uA);
	}
	
	public Agent findByPrincipal() {
		Agent res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.agentRepository.findAgentByUserAccountId(userAccount
					.getId());
		return res;
	}


}
