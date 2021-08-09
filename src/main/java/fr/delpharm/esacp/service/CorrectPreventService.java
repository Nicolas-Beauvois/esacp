package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.CorrectPrevent;
import fr.delpharm.esacp.repository.CorrectPreventRepository;
import fr.delpharm.esacp.service.dto.CorrectPreventDTO;
import fr.delpharm.esacp.service.mapper.CorrectPreventMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CorrectPrevent}.
 */
@Service
@Transactional
public class CorrectPreventService {

    private final Logger log = LoggerFactory.getLogger(CorrectPreventService.class);

    private final CorrectPreventRepository correctPreventRepository;

    private final CorrectPreventMapper correctPreventMapper;

    public CorrectPreventService(CorrectPreventRepository correctPreventRepository, CorrectPreventMapper correctPreventMapper) {
        this.correctPreventRepository = correctPreventRepository;
        this.correctPreventMapper = correctPreventMapper;
    }

    /**
     * Save a correctPrevent.
     *
     * @param correctPreventDTO the entity to save.
     * @return the persisted entity.
     */
    public CorrectPreventDTO save(CorrectPreventDTO correctPreventDTO) {
        log.debug("Request to save CorrectPrevent : {}", correctPreventDTO);
        CorrectPrevent correctPrevent = correctPreventMapper.toEntity(correctPreventDTO);
        correctPrevent = correctPreventRepository.save(correctPrevent);
        return correctPreventMapper.toDto(correctPrevent);
    }

    /**
     * Partially update a correctPrevent.
     *
     * @param correctPreventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CorrectPreventDTO> partialUpdate(CorrectPreventDTO correctPreventDTO) {
        log.debug("Request to partially update CorrectPrevent : {}", correctPreventDTO);

        return correctPreventRepository
            .findById(correctPreventDTO.getId())
            .map(
                existingCorrectPrevent -> {
                    correctPreventMapper.partialUpdate(existingCorrectPrevent, correctPreventDTO);

                    return existingCorrectPrevent;
                }
            )
            .map(correctPreventRepository::save)
            .map(correctPreventMapper::toDto);
    }

    /**
     * Get all the correctPrevents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrectPreventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrectPrevents");
        return correctPreventRepository.findAll(pageable).map(correctPreventMapper::toDto);
    }

    /**
     * Get one correctPrevent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CorrectPreventDTO> findOne(Long id) {
        log.debug("Request to get CorrectPrevent : {}", id);
        return correctPreventRepository.findById(id).map(correctPreventMapper::toDto);
    }

    /**
     * Delete the correctPrevent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CorrectPrevent : {}", id);
        correctPreventRepository.deleteById(id);
    }
}
