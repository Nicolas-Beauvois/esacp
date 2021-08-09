package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.Sexe;
import fr.delpharm.esacp.repository.SexeRepository;
import fr.delpharm.esacp.service.dto.SexeDTO;
import fr.delpharm.esacp.service.mapper.SexeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sexe}.
 */
@Service
@Transactional
public class SexeService {

    private final Logger log = LoggerFactory.getLogger(SexeService.class);

    private final SexeRepository sexeRepository;

    private final SexeMapper sexeMapper;

    public SexeService(SexeRepository sexeRepository, SexeMapper sexeMapper) {
        this.sexeRepository = sexeRepository;
        this.sexeMapper = sexeMapper;
    }

    /**
     * Save a sexe.
     *
     * @param sexeDTO the entity to save.
     * @return the persisted entity.
     */
    public SexeDTO save(SexeDTO sexeDTO) {
        log.debug("Request to save Sexe : {}", sexeDTO);
        Sexe sexe = sexeMapper.toEntity(sexeDTO);
        sexe = sexeRepository.save(sexe);
        return sexeMapper.toDto(sexe);
    }

    /**
     * Partially update a sexe.
     *
     * @param sexeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SexeDTO> partialUpdate(SexeDTO sexeDTO) {
        log.debug("Request to partially update Sexe : {}", sexeDTO);

        return sexeRepository
            .findById(sexeDTO.getId())
            .map(
                existingSexe -> {
                    sexeMapper.partialUpdate(existingSexe, sexeDTO);

                    return existingSexe;
                }
            )
            .map(sexeRepository::save)
            .map(sexeMapper::toDto);
    }

    /**
     * Get all the sexes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SexeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sexes");
        return sexeRepository.findAll(pageable).map(sexeMapper::toDto);
    }

    /**
     * Get one sexe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SexeDTO> findOne(Long id) {
        log.debug("Request to get Sexe : {}", id);
        return sexeRepository.findById(id).map(sexeMapper::toDto);
    }

    /**
     * Delete the sexe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sexe : {}", id);
        sexeRepository.deleteById(id);
    }
}
