package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Sexe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sexe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long> {}
