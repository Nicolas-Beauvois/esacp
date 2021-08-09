package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.CorrectPreventRepository;
import fr.delpharm.esacp.service.CorrectPreventService;
import fr.delpharm.esacp.service.dto.CorrectPreventDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.CorrectPrevent}.
 */
@RestController
@RequestMapping("/api")
public class CorrectPreventResource {

    private final Logger log = LoggerFactory.getLogger(CorrectPreventResource.class);

    private static final String ENTITY_NAME = "correctPrevent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrectPreventService correctPreventService;

    private final CorrectPreventRepository correctPreventRepository;

    public CorrectPreventResource(CorrectPreventService correctPreventService, CorrectPreventRepository correctPreventRepository) {
        this.correctPreventService = correctPreventService;
        this.correctPreventRepository = correctPreventRepository;
    }

    /**
     * {@code POST  /correct-prevents} : Create a new correctPrevent.
     *
     * @param correctPreventDTO the correctPreventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correctPreventDTO, or with status {@code 400 (Bad Request)} if the correctPrevent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correct-prevents")
    public ResponseEntity<CorrectPreventDTO> createCorrectPrevent(@RequestBody CorrectPreventDTO correctPreventDTO)
        throws URISyntaxException {
        log.debug("REST request to save CorrectPrevent : {}", correctPreventDTO);
        if (correctPreventDTO.getId() != null) {
            throw new BadRequestAlertException("A new correctPrevent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrectPreventDTO result = correctPreventService.save(correctPreventDTO);
        return ResponseEntity
            .created(new URI("/api/correct-prevents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correct-prevents/:id} : Updates an existing correctPrevent.
     *
     * @param id the id of the correctPreventDTO to save.
     * @param correctPreventDTO the correctPreventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correctPreventDTO,
     * or with status {@code 400 (Bad Request)} if the correctPreventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correctPreventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correct-prevents/{id}")
    public ResponseEntity<CorrectPreventDTO> updateCorrectPrevent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CorrectPreventDTO correctPreventDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CorrectPrevent : {}, {}", id, correctPreventDTO);
        if (correctPreventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, correctPreventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!correctPreventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CorrectPreventDTO result = correctPreventService.save(correctPreventDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correctPreventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /correct-prevents/:id} : Partial updates given fields of an existing correctPrevent, field will ignore if it is null
     *
     * @param id the id of the correctPreventDTO to save.
     * @param correctPreventDTO the correctPreventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correctPreventDTO,
     * or with status {@code 400 (Bad Request)} if the correctPreventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the correctPreventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the correctPreventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/correct-prevents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CorrectPreventDTO> partialUpdateCorrectPrevent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CorrectPreventDTO correctPreventDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CorrectPrevent partially : {}, {}", id, correctPreventDTO);
        if (correctPreventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, correctPreventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!correctPreventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CorrectPreventDTO> result = correctPreventService.partialUpdate(correctPreventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correctPreventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /correct-prevents} : get all the correctPrevents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correctPrevents in body.
     */
    @GetMapping("/correct-prevents")
    public ResponseEntity<List<CorrectPreventDTO>> getAllCorrectPrevents(Pageable pageable) {
        log.debug("REST request to get a page of CorrectPrevents");
        Page<CorrectPreventDTO> page = correctPreventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /correct-prevents/:id} : get the "id" correctPrevent.
     *
     * @param id the id of the correctPreventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correctPreventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correct-prevents/{id}")
    public ResponseEntity<CorrectPreventDTO> getCorrectPrevent(@PathVariable Long id) {
        log.debug("REST request to get CorrectPrevent : {}", id);
        Optional<CorrectPreventDTO> correctPreventDTO = correctPreventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correctPreventDTO);
    }

    /**
     * {@code DELETE  /correct-prevents/:id} : delete the "id" correctPrevent.
     *
     * @param id the id of the correctPreventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correct-prevents/{id}")
    public ResponseEntity<Void> deleteCorrectPrevent(@PathVariable Long id) {
        log.debug("REST request to delete CorrectPrevent : {}", id);
        correctPreventService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
