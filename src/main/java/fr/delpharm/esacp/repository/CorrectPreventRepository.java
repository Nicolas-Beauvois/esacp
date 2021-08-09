package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.CorrectPrevent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CorrectPrevent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrectPreventRepository extends JpaRepository<CorrectPrevent, Long> {}
