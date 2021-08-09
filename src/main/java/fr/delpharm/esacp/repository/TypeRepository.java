package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.Type;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Type entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {}
