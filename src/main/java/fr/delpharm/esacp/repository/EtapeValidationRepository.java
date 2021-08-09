package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.EtapeValidation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EtapeValidation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapeValidationRepository extends JpaRepository<EtapeValidation, Long> {}
