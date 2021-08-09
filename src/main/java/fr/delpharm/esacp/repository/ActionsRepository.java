package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Actions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Actions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionsRepository extends JpaRepository<Actions, Long> {}
