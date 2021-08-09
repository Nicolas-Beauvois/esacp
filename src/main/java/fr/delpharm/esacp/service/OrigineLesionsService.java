package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.OrigineLesions;
import fr.delpharm.esacp.repository.OrigineLesionsRepository;
import fr.delpharm.esacp.service.dto.OrigineLesionsDTO;
import fr.delpharm.esacp.service.mapper.OrigineLesionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrigineLesions}.
 */
@Service
@Transactional
public class OrigineLesionsService {

    private final Logger log = LoggerFactory.getLogger(OrigineLesionsService.class);

    private final OrigineLesionsRepository origineLesionsRepository;

    private final OrigineLesionsMapper origineLesionsMapper;

    public OrigineLesionsService(OrigineLesionsRepository origineLesionsRepository, OrigineLesionsMapper origineLesionsMapper) {
        this.origineLesionsRepository = origineLesionsRepository;
        this.origineLesionsMapper = origineLesionsMapper;
    }

    /**
     * Save a origineLesions.
     *
     * @param origineLesionsDTO the entity to save.
     * @return the persisted entity.
     */
    public OrigineLesionsDTO save(OrigineLesionsDTO origineLesionsDTO) {
        log.debug("Request to save OrigineLesions : {}", origineLesionsDTO);
        OrigineLesions origineLesions = origineLesionsMapper.toEntity(origineLesionsDTO);
        origineLesions = origineLesionsRepository.save(origineLesions);
        return origineLesionsMapper.toDto(origineLesions);
    }

    /**
     * Partially update a origineLesions.
     *
     * @param origineLesionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrigineLesionsDTO> partialUpdate(OrigineLesionsDTO origineLesionsDTO) {
        log.debug("Request to partially update OrigineLesions : {}", origineLesionsDTO);

        return origineLesionsRepository
            .findById(origineLesionsDTO.getId())
            .map(
                existingOrigineLesions -> {
                    origineLesionsMapper.partialUpdate(existingOrigineLesions, origineLesionsDTO);

                    return existingOrigineLesions;
                }
            )
            .map(origineLesionsRepository::save)
            .map(origineLesionsMapper::toDto);
    }

    /**
     * Get all the origineLesions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrigineLesionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrigineLesions");
        return origineLesionsRepository.findAll(pageable).map(origineLesionsMapper::toDto);
    }

    /**
     * Get one origineLesions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrigineLesionsDTO> findOne(Long id) {
        log.debug("Request to get OrigineLesions : {}", id);
        return origineLesionsRepository.findById(id).map(origineLesionsMapper::toDto);
    }

    /**
     * Delete the origineLesions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrigineLesions : {}", id);
        origineLesionsRepository.deleteById(id);
    }
}
