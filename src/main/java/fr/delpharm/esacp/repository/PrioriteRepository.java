package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Priorite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Priorite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrioriteRepository extends JpaRepository<Priorite, Long> {}
