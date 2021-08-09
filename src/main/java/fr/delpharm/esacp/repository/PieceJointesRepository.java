package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.PieceJointes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PieceJointes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceJointesRepository extends JpaRepository<PieceJointes, Long> {}
