package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.TypeAt;
import fr.delpharm.esacp.repository.TypeAtRepository;
import fr.delpharm.esacp.service.dto.TypeAtDTO;
import fr.delpharm.esacp.service.mapper.TypeAtMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeAt}.
 */
@Service
@Transactional
public class TypeAtService {

    private final Logger log = LoggerFactory.getLogger(TypeAtService.class);

    private final TypeAtRepository typeAtRepository;

    private final TypeAtMapper typeAtMapper;

    public TypeAtService(TypeAtRepository typeAtRepository, TypeAtMapper typeAtMapper) {
        this.typeAtRepository = typeAtRepository;
        this.typeAtMapper = typeAtMapper;
    }

    /**
     * Save a typeAt.
     *
     * @param typeAtDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeAtDTO save(TypeAtDTO typeAtDTO) {
        log.debug("Request to save TypeAt : {}", typeAtDTO);
        TypeAt typeAt = typeAtMapper.toEntity(typeAtDTO);
        typeAt = typeAtRepository.save(typeAt);
        return typeAtMapper.toDto(typeAt);
    }

    /**
     * Partially update a typeAt.
     *
     * @param typeAtDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypeAtDTO> partialUpdate(TypeAtDTO typeAtDTO) {
        log.debug("Request to partially update TypeAt : {}", typeAtDTO);

        return typeAtRepository
            .findById(typeAtDTO.getId())
            .map(
                existingTypeAt -> {
                    typeAtMapper.partialUpdate(existingTypeAt, typeAtDTO);

                    return existingTypeAt;
                }
            )
            .map(typeAtRepository::save)
            .map(typeAtMapper::toDto);
    }

    /**
     * Get all the typeAts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeAtDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAts");
        return typeAtRepository.findAll(pageable).map(typeAtMapper::toDto);
    }

    /**
     * Get one typeAt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeAtDTO> findOne(Long id) {
        log.debug("Request to get TypeAt : {}", id);
        return typeAtRepository.findById(id).map(typeAtMapper::toDto);
    }

    /**
     * Delete the typeAt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeAt : {}", id);
        typeAtRepository.deleteById(id);
    }
}
