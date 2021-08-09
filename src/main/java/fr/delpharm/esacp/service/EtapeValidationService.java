package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.EtapeValidation;
import fr.delpharm.esacp.repository.EtapeValidationRepository;
import fr.delpharm.esacp.service.dto.EtapeValidationDTO;
import fr.delpharm.esacp.service.mapper.EtapeValidationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EtapeValidation}.
 */
@Service
@Transactional
public class EtapeValidationService {

    private final Logger log = LoggerFactory.getLogger(EtapeValidationService.class);

    private final EtapeValidationRepository etapeValidationRepository;

    private final EtapeValidationMapper etapeValidationMapper;

    public EtapeValidationService(EtapeValidationRepository etapeValidationRepository, EtapeValidationMapper etapeValidationMapper) {
        this.etapeValidationRepository = etapeValidationRepository;
        this.etapeValidationMapper = etapeValidationMapper;
    }

    /**
     * Save a etapeValidation.
     *
     * @param etapeValidationDTO the entity to save.
     * @return the persisted entity.
     */
    public EtapeValidationDTO save(EtapeValidationDTO etapeValidationDTO) {
        log.debug("Request to save EtapeValidation : {}", etapeValidationDTO);
        EtapeValidation etapeValidation = etapeValidationMapper.toEntity(etapeValidationDTO);
        etapeValidation = etapeValidationRepository.save(etapeValidation);
        return etapeValidationMapper.toDto(etapeValidation);
    }

    /**
     * Partially update a etapeValidation.
     *
     * @param etapeValidationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EtapeValidationDTO> partialUpdate(EtapeValidationDTO etapeValidationDTO) {
        log.debug("Request to partially update EtapeValidation : {}", etapeValidationDTO);

        return etapeValidationRepository
            .findById(etapeValidationDTO.getId())
            .map(
                existingEtapeValidation -> {
                    etapeValidationMapper.partialUpdate(existingEtapeValidation, etapeValidationDTO);

                    return existingEtapeValidation;
                }
            )
            .map(etapeValidationRepository::save)
            .map(etapeValidationMapper::toDto);
    }

    /**
     * Get all the etapeValidations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EtapeValidationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtapeValidations");
        return etapeValidationRepository.findAll(pageable).map(etapeValidationMapper::toDto);
    }

    /**
     * Get one etapeValidation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtapeValidationDTO> findOne(Long id) {
        log.debug("Request to get EtapeValidation : {}", id);
        return etapeValidationRepository.findById(id).map(etapeValidationMapper::toDto);
    }

    /**
     * Delete the etapeValidation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtapeValidation : {}", id);
        etapeValidationRepository.deleteById(id);
    }
}
