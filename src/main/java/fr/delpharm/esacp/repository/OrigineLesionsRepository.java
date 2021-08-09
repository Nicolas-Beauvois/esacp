package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.OrigineLesions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrigineLesions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrigineLesionsRepository extends JpaRepository<OrigineLesions, Long> {}
