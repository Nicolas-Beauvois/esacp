package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Equipement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Equipement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {}
