
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	@Query("select n from Newspaper n where n.publicationDate <= current_date")
	Collection<Newspaper> findPastNewspapers();

	@Query("select n from Newspaper n join n.articles a where n.publicationDate <= current_date and a.isFinal = false")
	Collection<Newspaper> findPastNewspapersWithNonFinalArticle();

	@Query("select n from Newspaper n where(n.title LIKE %?1% or n.description LIKE %?1%) and n.publicationDate <= current_date")
	Collection<Newspaper> findPerKeyword(String keyword);

	@Query("select n from Newspaper n join n.articles a where(n.title LIKE %?1% or n.description LIKE %?1%) and n.publicationDate <= current_date and a.isFinal = false")
	Collection<Newspaper> findPerKeywordNonFinalArticle(String keyword);

	@Query("select n from Newspaper n where n.publisher.id = ?1")
	Collection<Newspaper> findByPublisherId(int publisherId);

	@Query("select n from Newspaper n where n.publicationDate > current_date")
	Collection<Newspaper> findNonPublished();

	//			ACME-NEWSPAPER 2.0

	@Query("select sum(case when n.advertisements.size>0 = 1 then 1.0 else 0.0 end)/count(n)from Newspaper n")
	float ratioNewspapersWithVsWithoutAdvertisements();
	
	@Query("select n from Newspaper n join n.volumes v where v.id = ?1")
	Collection<Newspaper> findByVolumeId(int volumeId);
	
	@Query("select n from Newspaper n join n.volumes v where v.id = ?1 and (n.title LIKE %?2% or n.description LIKE %?2%)")
	Collection<Newspaper> findByVolumeIdByKeyword(int volumeId, String keyword);
	

}
