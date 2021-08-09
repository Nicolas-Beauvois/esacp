package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.NatureAccident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureAccident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureAccidentRepository extends JpaRepository<NatureAccident, Long> {}
