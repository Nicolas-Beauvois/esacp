package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Csp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Csp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CspRepository extends JpaRepository<Csp, Long> {}
