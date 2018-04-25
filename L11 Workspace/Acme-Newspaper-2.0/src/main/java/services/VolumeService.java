package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Newspaper;
import domain.User;
import domain.Volume;
import repositories.VolumeRepository;

@Service
@Transactional
public class VolumeService {

	// Managed repository
	@Autowired
	private VolumeRepository volumeRepository;

	// Supporting services

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	// Constructors
	public VolumeService() {
		super();
	}

	// Simple CRUD methods
	public Volume create(final int newspaperId) {

		Assert.notNull(userService.findByPrincipal());

		Volume res = new Volume();
		Collection<Newspaper> newspapers = new ArrayList<Newspaper>();

		Assert.notNull(newspapers);

		User user = this.userService.findByPrincipal();

		res.setUser(user);
		res.setNewspapers(newspapers);

		return res;
	}

	public Volume save(final Volume volume) {
		Assert.notNull(volume);

		Volume res;
		res = this.volumeRepository.save(volume);
		return res;
	}

	public Collection<Volume> findAll() {
		Collection<Volume> res = new ArrayList<Volume>();

		res = this.volumeRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Volume findOne(final int volumeId) {
		Assert.isTrue(volumeId != 0);
		Volume res;

		res = this.volumeRepository.findOne(volumeId);

		return res;
	}

	public void delete(final Volume volume) {
		this.adminService.checkAuthority();

		Assert.notNull(volume);
		Assert.isTrue(volume.getId() != 0);

		this.volumeRepository.delete(volume);
	}

}
