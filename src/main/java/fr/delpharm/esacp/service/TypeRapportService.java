package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.TypeRapport;
import fr.delpharm.esacp.repository.TypeRapportRepository;
import fr.delpharm.esacp.service.dto.TypeRapportDTO;
import fr.delpharm.esacp.service.mapper.TypeRapportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeRapport}.
 */
@Service
@Transactional
public class TypeRapportService {

    private final Logger log = LoggerFactory.getLogger(TypeRapportService.class);

    private final TypeRapportRepository typeRapportRepository;

    private final TypeRapportMapper typeRapportMapper;

    public TypeRapportService(TypeRapportRepository typeRapportRepository, TypeRapportMapper typeRapportMapper) {
        this.typeRapportRepository = typeRapportRepository;
        this.typeRapportMapper = typeRapportMapper;
    }

    /**
     * Save a typeRapport.
     *
     * @param typeRapportDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeRapportDTO save(TypeRapportDTO typeRapportDTO) {
        log.debug("Request to save TypeRapport : {}", typeRapportDTO);
        TypeRapport typeRapport = typeRapportMapper.toEntity(typeRapportDTO);
        typeRapport = typeRapportRepository.save(typeRapport);
        return typeRapportMapper.toDto(typeRapport);
    }

    /**
     * Partially update a typeRapport.
     *
     * @param typeRapportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypeRapportDTO> partialUpdate(TypeRapportDTO typeRapportDTO) {
        log.debug("Request to partially update TypeRapport : {}", typeRapportDTO);

        return typeRapportRepository
            .findById(typeRapportDTO.getId())
            .map(
                existingTypeRapport -> {
                    typeRapportMapper.partialUpdate(existingTypeRapport, typeRapportDTO);

                    return existingTypeRapport;
                }
            )
            .map(typeRapportRepository::save)
            .map(typeRapportMapper::toDto);
    }

    /**
     * Get all the typeRapports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeRapportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeRapports");
        return typeRapportRepository.findAll(pageable).map(typeRapportMapper::toDto);
    }

    /**
     * Get one typeRapport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeRapportDTO> findOne(Long id) {
        log.debug("Request to get TypeRapport : {}", id);
        return typeRapportRepository.findById(id).map(typeRapportMapper::toDto);
    }

    /**
     * Delete the typeRapport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeRapport : {}", id);
        typeRapportRepository.deleteById(id);
    }
}
