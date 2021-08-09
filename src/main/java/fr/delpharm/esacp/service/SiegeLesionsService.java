package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.SiegeLesions;
import fr.delpharm.esacp.repository.SiegeLesionsRepository;
import fr.delpharm.esacp.service.dto.SiegeLesionsDTO;
import fr.delpharm.esacp.service.mapper.SiegeLesionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SiegeLesions}.
 */
@Service
@Transactional
public class SiegeLesionsService {

    private final Logger log = LoggerFactory.getLogger(SiegeLesionsService.class);

    private final SiegeLesionsRepository siegeLesionsRepository;

    private final SiegeLesionsMapper siegeLesionsMapper;

    public SiegeLesionsService(SiegeLesionsRepository siegeLesionsRepository, SiegeLesionsMapper siegeLesionsMapper) {
        this.siegeLesionsRepository = siegeLesionsRepository;
        this.siegeLesionsMapper = siegeLesionsMapper;
    }

    /**
     * Save a siegeLesions.
     *
     * @param siegeLesionsDTO the entity to save.
     * @return the persisted entity.
     */
    public SiegeLesionsDTO save(SiegeLesionsDTO siegeLesionsDTO) {
        log.debug("Request to save SiegeLesions : {}", siegeLesionsDTO);
        SiegeLesions siegeLesions = siegeLesionsMapper.toEntity(siegeLesionsDTO);
        siegeLesions = siegeLesionsRepository.save(siegeLesions);
        return siegeLesionsMapper.toDto(siegeLesions);
    }

    /**
     * Partially update a siegeLesions.
     *
     * @param siegeLesionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiegeLesionsDTO> partialUpdate(SiegeLesionsDTO siegeLesionsDTO) {
        log.debug("Request to partially update SiegeLesions : {}", siegeLesionsDTO);

        return siegeLesionsRepository
            .findById(siegeLesionsDTO.getId())
            .map(
                existingSiegeLesions -> {
                    siegeLesionsMapper.partialUpdate(existingSiegeLesions, siegeLesionsDTO);

                    return existingSiegeLesions;
                }
            )
            .map(siegeLesionsRepository::save)
            .map(siegeLesionsMapper::toDto);
    }

    /**
     * Get all the siegeLesions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiegeLesionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiegeLesions");
        return siegeLesionsRepository.findAll(pageable).map(siegeLesionsMapper::toDto);
    }

    /**
     * Get one siegeLesions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiegeLesionsDTO> findOne(Long id) {
        log.debug("Request to get SiegeLesions : {}", id);
        return siegeLesionsRepository.findById(id).map(siegeLesionsMapper::toDto);
    }

    /**
     * Delete the siegeLesions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiegeLesions : {}", id);
        siegeLesionsRepository.deleteById(id);
    }
}
