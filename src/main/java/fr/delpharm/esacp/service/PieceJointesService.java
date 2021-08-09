package fr.delpharm.esacp.service;

import fr.delpharm.esacp.domain.PieceJointes;
import fr.delpharm.esacp.repository.PieceJointesRepository;
import fr.delpharm.esacp.service.dto.PieceJointesDTO;
import fr.delpharm.esacp.service.mapper.PieceJointesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PieceJointes}.
 */
@Service
@Transactional
public class PieceJointesService {

    private final Logger log = LoggerFactory.getLogger(PieceJointesService.class);

    private final PieceJointesRepository pieceJointesRepository;

    private final PieceJointesMapper pieceJointesMapper;

    public PieceJointesService(PieceJointesRepository pieceJointesRepository, PieceJointesMapper pieceJointesMapper) {
        this.pieceJointesRepository = pieceJointesRepository;
        this.pieceJointesMapper = pieceJointesMapper;
    }

    /**
     * Save a pieceJointes.
     *
     * @param pieceJointesDTO the entity to save.
     * @return the persisted entity.
     */
    public PieceJointesDTO save(PieceJointesDTO pieceJointesDTO) {
        log.debug("Request to save PieceJointes : {}", pieceJointesDTO);
        PieceJointes pieceJointes = pieceJointesMapper.toEntity(pieceJointesDTO);
        pieceJointes = pieceJointesRepository.save(pieceJointes);
        return pieceJointesMapper.toDto(pieceJointes);
    }

    /**
     * Partially update a pieceJointes.
     *
     * @param pieceJointesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PieceJointesDTO> partialUpdate(PieceJointesDTO pieceJointesDTO) {
        log.debug("Request to partially update PieceJointes : {}", pieceJointesDTO);

        return pieceJointesRepository
            .findById(pieceJointesDTO.getId())
            .map(
                existingPieceJointes -> {
                    pieceJointesMapper.partialUpdate(existingPieceJointes, pieceJointesDTO);

                    return existingPieceJointes;
                }
            )
            .map(pieceJointesRepository::save)
            .map(pieceJointesMapper::toDto);
    }

    /**
     * Get all the pieceJointes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PieceJointesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PieceJointes");
        return pieceJointesRepository.findAll(pageable).map(pieceJointesMapper::toDto);
    }

    /**
     * Get one pieceJointes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PieceJointesDTO> findOne(Long id) {
        log.debug("Request to get PieceJointes : {}", id);
        return pieceJointesRepository.findById(id).map(pieceJointesMapper::toDto);
    }

    /**
     * Delete the pieceJointes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PieceJointes : {}", id);
        pieceJointesRepository.deleteById(id);
    }
}
