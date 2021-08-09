package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Criticite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Criticite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriticiteRepository extends JpaRepository<Criticite, Long> {}
