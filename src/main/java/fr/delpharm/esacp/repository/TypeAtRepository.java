package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.TypeAt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeAt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAtRepository extends JpaRepository<TypeAt, Long> {}
