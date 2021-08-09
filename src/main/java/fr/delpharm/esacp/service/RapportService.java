package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Rapport;
import fr.delpharm.esacp.repository.RapportRepository;
import fr.delpharm.esacp.service.dto.RapportDTO;
import fr.delpharm.esacp.service.mapper.RapportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rapport}.
 */
@Service
@Transactional
public class RapportService {

    private final Logger log = LoggerFactory.getLogger(RapportService.class);

    private final RapportRepository rapportRepository;

    private final RapportMapper rapportMapper;

    public RapportService(RapportRepository rapportRepository, RapportMapper rapportMapper) {
        this.rapportRepository = rapportRepository;
        this.rapportMapper = rapportMapper;
    }

    /**
     * Save a rapport.
     *
     * @param rapportDTO the entity to save.
     * @return the persisted entity.
     */
    public RapportDTO save(RapportDTO rapportDTO) {
        log.debug("Request to save Rapport : {}", rapportDTO);
        Rapport rapport = rapportMapper.toEntity(rapportDTO);
        rapport = rapportRepository.save(rapport);
        return rapportMapper.toDto(rapport);
    }

    /**
     * Partially update a rapport.
     *
     * @param rapportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RapportDTO> partialUpdate(RapportDTO rapportDTO) {
        log.debug("Request to partially update Rapport : {}", rapportDTO);

        return rapportRepository
            .findById(rapportDTO.getId())
            .map(
                existingRapport -> {
                    rapportMapper.partialUpdate(existingRapport, rapportDTO);

                    return existingRapport;
                }
            )
            .map(rapportRepository::save)
            .map(rapportMapper::toDto);
    }

    /**
     * Get all the rapports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RapportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rapports");
        return rapportRepository.findAll(pageable).map(rapportMapper::toDto);
    }

    /**
     * Get one rapport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RapportDTO> findOne(Long id) {
        log.debug("Request to get Rapport : {}", id);
        return rapportRepository.findById(id).map(rapportMapper::toDto);
    }

    /**
     * Delete the rapport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rapport : {}", id);
        rapportRepository.deleteById(id);
    }
}
