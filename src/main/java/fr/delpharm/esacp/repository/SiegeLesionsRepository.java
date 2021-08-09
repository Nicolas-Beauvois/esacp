package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.SiegeLesions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiegeLesions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiegeLesionsRepository extends JpaRepository<SiegeLesions, Long> {}
