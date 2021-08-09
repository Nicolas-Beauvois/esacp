package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Criticite;
import fr.delpharm.esacp.repository.CriticiteRepository;
import fr.delpharm.esacp.service.dto.CriticiteDTO;
import fr.delpharm.esacp.service.mapper.CriticiteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Criticite}.
 */
@Service
@Transactional
public class CriticiteService {

    private final Logger log = LoggerFactory.getLogger(CriticiteService.class);

    private final CriticiteRepository criticiteRepository;

    private final CriticiteMapper criticiteMapper;

    public CriticiteService(CriticiteRepository criticiteRepository, CriticiteMapper criticiteMapper) {
        this.criticiteRepository = criticiteRepository;
        this.criticiteMapper = criticiteMapper;
    }

    /**
     * Save a criticite.
     *
     * @param criticiteDTO the entity to save.
     * @return the persisted entity.
     */
    public CriticiteDTO save(CriticiteDTO criticiteDTO) {
        log.debug("Request to save Criticite : {}", criticiteDTO);
        Criticite criticite = criticiteMapper.toEntity(criticiteDTO);
        criticite = criticiteRepository.save(criticite);
        return criticiteMapper.toDto(criticite);
    }

    /**
     * Partially update a criticite.
     *
     * @param criticiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CriticiteDTO> partialUpdate(CriticiteDTO criticiteDTO) {
        log.debug("Request to partially update Criticite : {}", criticiteDTO);

        return criticiteRepository
            .findById(criticiteDTO.getId())
            .map(
                existingCriticite -> {
                    criticiteMapper.partialUpdate(existingCriticite, criticiteDTO);

                    return existingCriticite;
                }
            )
            .map(criticiteRepository::save)
            .map(criticiteMapper::toDto);
    }

    /**
     * Get all the criticites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CriticiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Criticites");
        return criticiteRepository.findAll(pageable).map(criticiteMapper::toDto);
    }

    /**
     * Get one criticite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CriticiteDTO> findOne(Long id) {
        log.debug("Request to get Criticite : {}", id);
        return criticiteRepository.findById(id).map(criticiteMapper::toDto);
    }

    /**
     * Delete the criticite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Criticite : {}", id);
        criticiteRepository.deleteById(id);
    }
}
