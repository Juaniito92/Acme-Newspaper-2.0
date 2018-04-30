
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

	//TODO no hace ná todavía, to be remade in Lucene
	// @Query("select a from Advertisement a where(a.title LIKE %?1%")
	// Collection<Advertisement> findAllTabooWords(String tabooWords);

}
