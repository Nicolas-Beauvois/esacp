package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.TypeAction;
import fr.delpharm.esacp.repository.TypeActionRepository;
import fr.delpharm.esacp.service.dto.TypeActionDTO;
import fr.delpharm.esacp.service.mapper.TypeActionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeAction}.
 */
@Service
@Transactional
public class TypeActionService {

    private final Logger log = LoggerFactory.getLogger(TypeActionService.class);

    private final TypeActionRepository typeActionRepository;

    private final TypeActionMapper typeActionMapper;

    public TypeActionService(TypeActionRepository typeActionRepository, TypeActionMapper typeActionMapper) {
        this.typeActionRepository = typeActionRepository;
        this.typeActionMapper = typeActionMapper;
    }

    /**
     * Save a typeAction.
     *
     * @param typeActionDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeActionDTO save(TypeActionDTO typeActionDTO) {
        log.debug("Request to save TypeAction : {}", typeActionDTO);
        TypeAction typeAction = typeActionMapper.toEntity(typeActionDTO);
        typeAction = typeActionRepository.save(typeAction);
        return typeActionMapper.toDto(typeAction);
    }

    /**
     * Partially update a typeAction.
     *
     * @param typeActionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypeActionDTO> partialUpdate(TypeActionDTO typeActionDTO) {
        log.debug("Request to partially update TypeAction : {}", typeActionDTO);

        return typeActionRepository
            .findById(typeActionDTO.getId())
            .map(
                existingTypeAction -> {
                    typeActionMapper.partialUpdate(existingTypeAction, typeActionDTO);

                    return existingTypeAction;
                }
            )
            .map(typeActionRepository::save)
            .map(typeActionMapper::toDto);
    }

    /**
     * Get all the typeActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeActions");
        return typeActionRepository.findAll(pageable).map(typeActionMapper::toDto);
    }

    /**
     * Get one typeAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeActionDTO> findOne(Long id) {
        log.debug("Request to get TypeAction : {}", id);
        return typeActionRepository.findById(id).map(typeActionMapper::toDto);
    }

    /**
     * Delete the typeAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeAction : {}", id);
        typeActionRepository.deleteById(id);
    }
}
