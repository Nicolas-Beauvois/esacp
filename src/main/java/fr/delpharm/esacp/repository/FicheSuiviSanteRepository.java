package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.FicheSuiviSante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FicheSuiviSante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FicheSuiviSanteRepository extends JpaRepository<FicheSuiviSante, Long> {}
