package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Statut;
import fr.delpharm.esacp.repository.StatutRepository;
import fr.delpharm.esacp.service.dto.StatutDTO;
import fr.delpharm.esacp.service.mapper.StatutMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Statut}.
 */
@Service
@Transactional
public class StatutService {

    private final Logger log = LoggerFactory.getLogger(StatutService.class);

    private final StatutRepository statutRepository;

    private final StatutMapper statutMapper;

    public StatutService(StatutRepository statutRepository, StatutMapper statutMapper) {
        this.statutRepository = statutRepository;
        this.statutMapper = statutMapper;
    }

    /**
     * Save a statut.
     *
     * @param statutDTO the entity to save.
     * @return the persisted entity.
     */
    public StatutDTO save(StatutDTO statutDTO) {
        log.debug("Request to save Statut : {}", statutDTO);
        Statut statut = statutMapper.toEntity(statutDTO);
        statut = statutRepository.save(statut);
        return statutMapper.toDto(statut);
    }

    /**
     * Partially update a statut.
     *
     * @param statutDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StatutDTO> partialUpdate(StatutDTO statutDTO) {
        log.debug("Request to partially update Statut : {}", statutDTO);

        return statutRepository
            .findById(statutDTO.getId())
            .map(
                existingStatut -> {
                    statutMapper.partialUpdate(existingStatut, statutDTO);

                    return existingStatut;
                }
            )
            .map(statutRepository::save)
            .map(statutMapper::toDto);
    }

    /**
     * Get all the statuts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Statuts");
        return statutRepository.findAll(pageable).map(statutMapper::toDto);
    }

    /**
     * Get one statut by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatutDTO> findOne(Long id) {
        log.debug("Request to get Statut : {}", id);
        return statutRepository.findById(id).map(statutMapper::toDto);
    }

    /**
     * Delete the statut by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Statut : {}", id);
        statutRepository.deleteById(id);
    }
}
