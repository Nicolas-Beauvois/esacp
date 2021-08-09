package fr.delpharm.esacp.repository;

import fr.delpharm.esacp.domain.ListingMail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListingMail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListingMailRepository extends JpaRepository<ListingMail, Long> {}
