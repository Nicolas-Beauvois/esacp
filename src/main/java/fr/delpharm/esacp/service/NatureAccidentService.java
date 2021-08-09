package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.NatureAccident;
import fr.delpharm.esacp.repository.NatureAccidentRepository;
import fr.delpharm.esacp.service.dto.NatureAccidentDTO;
import fr.delpharm.esacp.service.mapper.NatureAccidentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureAccident}.
 */
@Service
@Transactional
public class NatureAccidentService {

    private final Logger log = LoggerFactory.getLogger(NatureAccidentService.class);

    private final NatureAccidentRepository natureAccidentRepository;

    private final NatureAccidentMapper natureAccidentMapper;

    public NatureAccidentService(NatureAccidentRepository natureAccidentRepository, NatureAccidentMapper natureAccidentMapper) {
        this.natureAccidentRepository = natureAccidentRepository;
        this.natureAccidentMapper = natureAccidentMapper;
    }

    /**
     * Save a natureAccident.
     *
     * @param natureAccidentDTO the entity to save.
     * @return the persisted entity.
     */
    public NatureAccidentDTO save(NatureAccidentDTO natureAccidentDTO) {
        log.debug("Request to save NatureAccident : {}", natureAccidentDTO);
        NatureAccident natureAccident = natureAccidentMapper.toEntity(natureAccidentDTO);
        natureAccident = natureAccidentRepository.save(natureAccident);
        return natureAccidentMapper.toDto(natureAccident);
    }

    /**
     * Partially update a natureAccident.
     *
     * @param natureAccidentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NatureAccidentDTO> partialUpdate(NatureAccidentDTO natureAccidentDTO) {
        log.debug("Request to partially update NatureAccident : {}", natureAccidentDTO);

        return natureAccidentRepository
            .findById(natureAccidentDTO.getId())
            .map(
                existingNatureAccident -> {
                    natureAccidentMapper.partialUpdate(existingNatureAccident, natureAccidentDTO);

                    return existingNatureAccident;
                }
            )
            .map(natureAccidentRepository::save)
            .map(natureAccidentMapper::toDto);
    }

    /**
     * Get all the natureAccidents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NatureAccidentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NatureAccidents");
        return natureAccidentRepository.findAll(pageable).map(natureAccidentMapper::toDto);
    }

    /**
     * Get one natureAccident by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NatureAccidentDTO> findOne(Long id) {
        log.debug("Request to get NatureAccident : {}", id);
        return natureAccidentRepository.findById(id).map(natureAccidentMapper::toDto);
    }

    /**
     * Delete the natureAccident by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NatureAccident : {}", id);
        natureAccidentRepository.deleteById(id);
    }
}
