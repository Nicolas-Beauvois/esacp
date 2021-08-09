package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.FicheSuiviSante;
import fr.delpharm.esacp.repository.FicheSuiviSanteRepository;
import fr.delpharm.esacp.service.dto.FicheSuiviSanteDTO;
import fr.delpharm.esacp.service.mapper.FicheSuiviSanteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FicheSuiviSante}.
 */
@Service
@Transactional
public class FicheSuiviSanteService {

    private final Logger log = LoggerFactory.getLogger(FicheSuiviSanteService.class);

    private final FicheSuiviSanteRepository ficheSuiviSanteRepository;

    private final FicheSuiviSanteMapper ficheSuiviSanteMapper;

    public FicheSuiviSanteService(FicheSuiviSanteRepository ficheSuiviSanteRepository, FicheSuiviSanteMapper ficheSuiviSanteMapper) {
        this.ficheSuiviSanteRepository = ficheSuiviSanteRepository;
        this.ficheSuiviSanteMapper = ficheSuiviSanteMapper;
    }

    /**
     * Save a ficheSuiviSante.
     *
     * @param ficheSuiviSanteDTO the entity to save.
     * @return the persisted entity.
     */
    public FicheSuiviSanteDTO save(FicheSuiviSanteDTO ficheSuiviSanteDTO) {
        log.debug("Request to save FicheSuiviSante : {}", ficheSuiviSanteDTO);
        FicheSuiviSante ficheSuiviSante = ficheSuiviSanteMapper.toEntity(ficheSuiviSanteDTO);
        ficheSuiviSante = ficheSuiviSanteRepository.save(ficheSuiviSante);
        return ficheSuiviSanteMapper.toDto(ficheSuiviSante);
    }

    /**
     * Partially update a ficheSuiviSante.
     *
     * @param ficheSuiviSanteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FicheSuiviSanteDTO> partialUpdate(FicheSuiviSanteDTO ficheSuiviSanteDTO) {
        log.debug("Request to partially update FicheSuiviSante : {}", ficheSuiviSanteDTO);

        return ficheSuiviSanteRepository
            .findById(ficheSuiviSanteDTO.getId())
            .map(
                existingFicheSuiviSante -> {
                    ficheSuiviSanteMapper.partialUpdate(existingFicheSuiviSante, ficheSuiviSanteDTO);

                    return existingFicheSuiviSante;
                }
            )
            .map(ficheSuiviSanteRepository::save)
            .map(ficheSuiviSanteMapper::toDto);
    }

    /**
     * Get all the ficheSuiviSantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FicheSuiviSanteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FicheSuiviSantes");
        return ficheSuiviSanteRepository.findAll(pageable).map(ficheSuiviSanteMapper::toDto);
    }

    /**
     * Get one ficheSuiviSante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FicheSuiviSanteDTO> findOne(Long id) {
        log.debug("Request to get FicheSuiviSante : {}", id);
        return ficheSuiviSanteRepository.findById(id).map(ficheSuiviSanteMapper::toDto);
    }

    /**
     * Delete the ficheSuiviSante by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FicheSuiviSante : {}", id);
        ficheSuiviSanteRepository.deleteById(id);
    }
}
