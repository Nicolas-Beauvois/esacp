package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Rapport;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {
    @Query("select rapport from Rapport rapport where rapport.user.login = ?#{principal.username}")
    List<Rapport> findByUserIsCurrentUser();
}
