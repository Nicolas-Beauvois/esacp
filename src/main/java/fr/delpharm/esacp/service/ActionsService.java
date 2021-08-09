package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Actions;
import fr.delpharm.esacp.repository.ActionsRepository;
import fr.delpharm.esacp.service.dto.ActionsDTO;
import fr.delpharm.esacp.service.mapper.ActionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Actions}.
 */
@Service
@Transactional
public class ActionsService {

    private final Logger log = LoggerFactory.getLogger(ActionsService.class);

    private final ActionsRepository actionsRepository;

    private final ActionsMapper actionsMapper;

    public ActionsService(ActionsRepository actionsRepository, ActionsMapper actionsMapper) {
        this.actionsRepository = actionsRepository;
        this.actionsMapper = actionsMapper;
    }

    /**
     * Save a actions.
     *
     * @param actionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ActionsDTO save(ActionsDTO actionsDTO) {
        log.debug("Request to save Actions : {}", actionsDTO);
        Actions actions = actionsMapper.toEntity(actionsDTO);
        actions = actionsRepository.save(actions);
        return actionsMapper.toDto(actions);
    }

    /**
     * Partially update a actions.
     *
     * @param actionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActionsDTO> partialUpdate(ActionsDTO actionsDTO) {
        log.debug("Request to partially update Actions : {}", actionsDTO);

        return actionsRepository
            .findById(actionsDTO.getId())
            .map(
                existingActions -> {
                    actionsMapper.partialUpdate(existingActions, actionsDTO);

                    return existingActions;
                }
            )
            .map(actionsRepository::save)
            .map(actionsMapper::toDto);
    }

    /**
     * Get all the actions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Actions");
        return actionsRepository.findAll(pageable).map(actionsMapper::toDto);
    }

    /**
     * Get one actions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActionsDTO> findOne(Long id) {
        log.debug("Request to get Actions : {}", id);
        return actionsRepository.findById(id).map(actionsMapper::toDto);
    }

    /**
     * Delete the actions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Actions : {}", id);
        actionsRepository.deleteById(id);
    }
}
