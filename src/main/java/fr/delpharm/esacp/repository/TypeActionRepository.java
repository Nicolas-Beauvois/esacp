package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.TypeAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeActionRepository extends JpaRepository<TypeAction, Long> {}
