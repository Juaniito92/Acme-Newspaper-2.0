package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Actor;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository actorRepository;

	// Constructors
	public ActorService() {
		super();
	}

	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	// Ancillary methods

	public Actor findActorByUserAccountId(int uA) {

		Actor actor = actorRepository.findActorByUserAccountId(uA);
		return actor;
	}

	public Actor findByPrincipal() {

		Actor actor;

		UserAccount principalUserAccount = LoginService.getPrincipal();
		if (principalUserAccount == null) {
			actor = null;
		} else {
			actor = actorRepository.findActorByUserAccountId(principalUserAccount.getId());
		}

		return actor;
	}

	public String getType(UserAccount userAccount) {
		List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

}
