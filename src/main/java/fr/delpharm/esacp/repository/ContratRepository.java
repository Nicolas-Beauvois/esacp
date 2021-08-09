package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Contrat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Contrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {}
