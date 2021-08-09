package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Repartition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Repartition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepartitionRepository extends JpaRepository<Repartition, Long> {}
