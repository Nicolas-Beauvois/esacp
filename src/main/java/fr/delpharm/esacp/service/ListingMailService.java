package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.ListingMail;
import fr.delpharm.esacp.repository.ListingMailRepository;
import fr.delpharm.esacp.service.dto.ListingMailDTO;
import fr.delpharm.esacp.service.mapper.ListingMailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ListingMail}.
 */
@Service
@Transactional
public class ListingMailService {

    private final Logger log = LoggerFactory.getLogger(ListingMailService.class);

    private final ListingMailRepository listingMailRepository;

    private final ListingMailMapper listingMailMapper;

    public ListingMailService(ListingMailRepository listingMailRepository, ListingMailMapper listingMailMapper) {
        this.listingMailRepository = listingMailRepository;
        this.listingMailMapper = listingMailMapper;
    }

    /**
     * Save a listingMail.
     *
     * @param listingMailDTO the entity to save.
     * @return the persisted entity.
     */
    public ListingMailDTO save(ListingMailDTO listingMailDTO) {
        log.debug("Request to save ListingMail : {}", listingMailDTO);
        ListingMail listingMail = listingMailMapper.toEntity(listingMailDTO);
        listingMail = listingMailRepository.save(listingMail);
        return listingMailMapper.toDto(listingMail);
    }

    /**
     * Partially update a listingMail.
     *
     * @param listingMailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ListingMailDTO> partialUpdate(ListingMailDTO listingMailDTO) {
        log.debug("Request to partially update ListingMail : {}", listingMailDTO);

        return listingMailRepository
            .findById(listingMailDTO.getId())
            .map(
                existingListingMail -> {
                    listingMailMapper.partialUpdate(existingListingMail, listingMailDTO);

                    return existingListingMail;
                }
            )
            .map(listingMailRepository::save)
            .map(listingMailMapper::toDto);
    }

    /**
     * Get all the listingMails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ListingMailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ListingMails");
        return listingMailRepository.findAll(pageable).map(listingMailMapper::toDto);
    }

    /**
     * Get one listingMail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ListingMailDTO> findOne(Long id) {
        log.debug("Request to get ListingMail : {}", id);
        return listingMailRepository.findById(id).map(listingMailMapper::toDto);
    }

    /**
     * Delete the listingMail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ListingMail : {}", id);
        listingMailRepository.deleteById(id);
    }
}
