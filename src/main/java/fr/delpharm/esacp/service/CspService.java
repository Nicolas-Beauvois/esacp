package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Csp;
import fr.delpharm.esacp.repository.CspRepository;
import fr.delpharm.esacp.service.dto.CspDTO;
import fr.delpharm.esacp.service.mapper.CspMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Csp}.
 */
@Service
@Transactional
public class CspService {

    private final Logger log = LoggerFactory.getLogger(CspService.class);

    private final CspRepository cspRepository;

    private final CspMapper cspMapper;

    public CspService(CspRepository cspRepository, CspMapper cspMapper) {
        this.cspRepository = cspRepository;
        this.cspMapper = cspMapper;
    }

    /**
     * Save a csp.
     *
     * @param cspDTO the entity to save.
     * @return the persisted entity.
     */
    public CspDTO save(CspDTO cspDTO) {
        log.debug("Request to save Csp : {}", cspDTO);
        Csp csp = cspMapper.toEntity(cspDTO);
        csp = cspRepository.save(csp);
        return cspMapper.toDto(csp);
    }

    /**
     * Partially update a csp.
     *
     * @param cspDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CspDTO> partialUpdate(CspDTO cspDTO) {
        log.debug("Request to partially update Csp : {}", cspDTO);

        return cspRepository
            .findById(cspDTO.getId())
            .map(
                existingCsp -> {
                    cspMapper.partialUpdate(existingCsp, cspDTO);

                    return existingCsp;
                }
            )
            .map(cspRepository::save)
            .map(cspMapper::toDto);
    }

    /**
     * Get all the csps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CspDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Csps");
        return cspRepository.findAll(pageable).map(cspMapper::toDto);
    }

    /**
     * Get one csp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CspDTO> findOne(Long id) {
        log.debug("Request to get Csp : {}", id);
        return cspRepository.findById(id).map(cspMapper::toDto);
    }

    /**
     * Delete the csp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Csp : {}", id);
        cspRepository.deleteById(id);
    }
}
