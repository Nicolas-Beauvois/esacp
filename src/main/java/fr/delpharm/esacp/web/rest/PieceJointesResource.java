package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.PieceJointesRepository;
import fr.delpharm.esacp.service.PieceJointesService;
import fr.delpharm.esacp.service.dto.PieceJointesDTO;
import fr.delpharm.esacp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.delpharm.esacp.domain.PieceJointes}.
 */
@RestController
@RequestMapping("/api")
public class PieceJointesResource {

    private final Logger log = LoggerFactory.getLogger(PieceJointesResource.class);

    private static final String ENTITY_NAME = "pieceJointes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PieceJointesService pieceJointesService;

    private final PieceJointesRepository pieceJointesRepository;

    public PieceJointesResource(PieceJointesService pieceJointesService, PieceJointesRepository pieceJointesRepository) {
        this.pieceJointesService = pieceJointesService;
        this.pieceJointesRepository = pieceJointesRepository;
    }

    /**
     * {@code POST  /piece-jointes} : Create a new pieceJointes.
     *
     * @param pieceJointesDTO the pieceJointesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pieceJointesDTO, or with status {@code 400 (Bad Request)} if the pieceJointes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/piece-jointes")
    public ResponseEntity<PieceJointesDTO> createPieceJointes(@RequestBody PieceJointesDTO pieceJointesDTO) throws URISyntaxException {
        log.debug("REST request to save PieceJointes : {}", pieceJointesDTO);
        if (pieceJointesDTO.getId() != null) {
            throw new BadRequestAlertException("A new pieceJointes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PieceJointesDTO result = pieceJointesService.save(pieceJointesDTO);
        return ResponseEntity
            .created(new URI("/api/piece-jointes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /piece-jointes/:id} : Updates an existing pieceJointes.
     *
     * @param id the id of the pieceJointesDTO to save.
     * @param pieceJointesDTO the pieceJointesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pieceJointesDTO,
     * or with status {@code 400 (Bad Request)} if the pieceJointesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pieceJointesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/piece-jointes/{id}")
    public ResponseEntity<PieceJointesDTO> updatePieceJointes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PieceJointesDTO pieceJointesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PieceJointes : {}, {}", id, pieceJointesDTO);
        if (pieceJointesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pieceJointesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pieceJointesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PieceJointesDTO result = pieceJointesService.save(pieceJointesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pieceJointesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /piece-jointes/:id} : Partial updates given fields of an existing pieceJointes, field will ignore if it is null
     *
     * @param id the id of the pieceJointesDTO to save.
     * @param pieceJointesDTO the pieceJointesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pieceJointesDTO,
     * or with status {@code 400 (Bad Request)} if the pieceJointesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pieceJointesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pieceJointesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/piece-jointes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PieceJointesDTO> partialUpdatePieceJointes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PieceJointesDTO pieceJointesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PieceJointes partially : {}, {}", id, pieceJointesDTO);
        if (pieceJointesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pieceJointesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pieceJointesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PieceJointesDTO> result = pieceJointesService.partialUpdate(pieceJointesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pieceJointesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /piece-jointes} : get all the pieceJointes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pieceJointes in body.
     */
    @GetMapping("/piece-jointes")
    public ResponseEntity<List<PieceJointesDTO>> getAllPieceJointes(Pageable pageable) {
        log.debug("REST request to get a page of PieceJointes");
        Page<PieceJointesDTO> page = pieceJointesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /piece-jointes/:id} : get the "id" pieceJointes.
     *
     * @param id the id of the pieceJointesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pieceJointesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/piece-jointes/{id}")
    public ResponseEntity<PieceJointesDTO> getPieceJointes(@PathVariable Long id) {
        log.debug("REST request to get PieceJointes : {}", id);
        Optional<PieceJointesDTO> pieceJointesDTO = pieceJointesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pieceJointesDTO);
    }

    /**
     * {@code DELETE  /piece-jointes/:id} : delete the "id" pieceJointes.
     *
     * @param id the id of the pieceJointesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/piece-jointes/{id}")
    public ResponseEntity<Void> deletePieceJointes(@PathVariable Long id) {
        log.debug("REST request to delete PieceJointes : {}", id);
        pieceJointesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
