package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Priorite;
import fr.delpharm.esacp.repository.PrioriteRepository;
import fr.delpharm.esacp.service.dto.PrioriteDTO;
import fr.delpharm.esacp.service.mapper.PrioriteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Priorite}.
 */
@Service
@Transactional
public class PrioriteService {

    private final Logger log = LoggerFactory.getLogger(PrioriteService.class);

    private final PrioriteRepository prioriteRepository;

    private final PrioriteMapper prioriteMapper;

    public PrioriteService(PrioriteRepository prioriteRepository, PrioriteMapper prioriteMapper) {
        this.prioriteRepository = prioriteRepository;
        this.prioriteMapper = prioriteMapper;
    }

    /**
     * Save a priorite.
     *
     * @param prioriteDTO the entity to save.
     * @return the persisted entity.
     */
    public PrioriteDTO save(PrioriteDTO prioriteDTO) {
        log.debug("Request to save Priorite : {}", prioriteDTO);
        Priorite priorite = prioriteMapper.toEntity(prioriteDTO);
        priorite = prioriteRepository.save(priorite);
        return prioriteMapper.toDto(priorite);
    }

    /**
     * Partially update a priorite.
     *
     * @param prioriteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrioriteDTO> partialUpdate(PrioriteDTO prioriteDTO) {
        log.debug("Request to partially update Priorite : {}", prioriteDTO);

        return prioriteRepository
            .findById(prioriteDTO.getId())
            .map(
                existingPriorite -> {
                    prioriteMapper.partialUpdate(existingPriorite, prioriteDTO);

                    return existingPriorite;
                }
            )
            .map(prioriteRepository::save)
            .map(prioriteMapper::toDto);
    }

    /**
     * Get all the priorites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrioriteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Priorites");
        return prioriteRepository.findAll(pageable).map(prioriteMapper::toDto);
    }

    /**
     * Get one priorite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrioriteDTO> findOne(Long id) {
        log.debug("Request to get Priorite : {}", id);
        return prioriteRepository.findById(id).map(prioriteMapper::toDto);
    }

    /**
     * Delete the priorite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Priorite : {}", id);
        prioriteRepository.deleteById(id);
    }
}
