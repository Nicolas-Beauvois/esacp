package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Repartition;
import fr.delpharm.esacp.repository.RepartitionRepository;
import fr.delpharm.esacp.service.dto.RepartitionDTO;
import fr.delpharm.esacp.service.mapper.RepartitionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Repartition}.
 */
@Service
@Transactional
public class RepartitionService {

    private final Logger log = LoggerFactory.getLogger(RepartitionService.class);

    private final RepartitionRepository repartitionRepository;

    private final RepartitionMapper repartitionMapper;

    public RepartitionService(RepartitionRepository repartitionRepository, RepartitionMapper repartitionMapper) {
        this.repartitionRepository = repartitionRepository;
        this.repartitionMapper = repartitionMapper;
    }

    /**
     * Save a repartition.
     *
     * @param repartitionDTO the entity to save.
     * @return the persisted entity.
     */
    public RepartitionDTO save(RepartitionDTO repartitionDTO) {
        log.debug("Request to save Repartition : {}", repartitionDTO);
        Repartition repartition = repartitionMapper.toEntity(repartitionDTO);
        repartition = repartitionRepository.save(repartition);
        return repartitionMapper.toDto(repartition);
    }

    /**
     * Partially update a repartition.
     *
     * @param repartitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RepartitionDTO> partialUpdate(RepartitionDTO repartitionDTO) {
        log.debug("Request to partially update Repartition : {}", repartitionDTO);

        return repartitionRepository
            .findById(repartitionDTO.getId())
            .map(
                existingRepartition -> {
                    repartitionMapper.partialUpdate(existingRepartition, repartitionDTO);

                    return existingRepartition;
                }
            )
            .map(repartitionRepository::save)
            .map(repartitionMapper::toDto);
    }

    /**
     * Get all the repartitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RepartitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Repartitions");
        return repartitionRepository.findAll(pageable).map(repartitionMapper::toDto);
    }

    /**
     * Get one repartition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RepartitionDTO> findOne(Long id) {
        log.debug("Request to get Repartition : {}", id);
        return repartitionRepository.findById(id).map(repartitionMapper::toDto);
    }

    /**
     * Delete the repartition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Repartition : {}", id);
        repartitionRepository.deleteById(id);
    }
}
