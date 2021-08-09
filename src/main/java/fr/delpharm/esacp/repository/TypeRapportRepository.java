package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.TypeRapport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeRapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRapportRepository extends JpaRepository<TypeRapport, Long> {}
