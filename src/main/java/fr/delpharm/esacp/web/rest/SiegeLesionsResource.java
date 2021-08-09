package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.SiegeLesionsRepository;
import fr.delpharm.esacp.service.SiegeLesionsService;
import fr.delpharm.esacp.service.dto.SiegeLesionsDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.SiegeLesions}.
 */
@RestController
@RequestMapping("/api")
public class SiegeLesionsResource {

    private final Logger log = LoggerFactory.getLogger(SiegeLesionsResource.class);

    private static final String ENTITY_NAME = "siegeLesions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiegeLesionsService siegeLesionsService;

    private final SiegeLesionsRepository siegeLesionsRepository;

    public SiegeLesionsResource(SiegeLesionsService siegeLesionsService, SiegeLesionsRepository siegeLesionsRepository) {
        this.siegeLesionsService = siegeLesionsService;
        this.siegeLesionsRepository = siegeLesionsRepository;
    }

    /**
     * {@code POST  /siege-lesions} : Create a new siegeLesions.
     *
     * @param siegeLesionsDTO the siegeLesionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siegeLesionsDTO, or with status {@code 400 (Bad Request)} if the siegeLesions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/siege-lesions")
    public ResponseEntity<SiegeLesionsDTO> createSiegeLesions(@RequestBody SiegeLesionsDTO siegeLesionsDTO) throws URISyntaxException {
        log.debug("REST request to save SiegeLesions : {}", siegeLesionsDTO);
        if (siegeLesionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new siegeLesions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiegeLesionsDTO result = siegeLesionsService.save(siegeLesionsDTO);
        return ResponseEntity
            .created(new URI("/api/siege-lesions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /siege-lesions/:id} : Updates an existing siegeLesions.
     *
     * @param id the id of the siegeLesionsDTO to save.
     * @param siegeLesionsDTO the siegeLesionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siegeLesionsDTO,
     * or with status {@code 400 (Bad Request)} if the siegeLesionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siegeLesionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/siege-lesions/{id}")
    public ResponseEntity<SiegeLesionsDTO> updateSiegeLesions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiegeLesionsDTO siegeLesionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SiegeLesions : {}, {}", id, siegeLesionsDTO);
        if (siegeLesionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siegeLesionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siegeLesionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiegeLesionsDTO result = siegeLesionsService.save(siegeLesionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siegeLesionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /siege-lesions/:id} : Partial updates given fields of an existing siegeLesions, field will ignore if it is null
     *
     * @param id the id of the siegeLesionsDTO to save.
     * @param siegeLesionsDTO the siegeLesionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siegeLesionsDTO,
     * or with status {@code 400 (Bad Request)} if the siegeLesionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the siegeLesionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the siegeLesionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/siege-lesions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SiegeLesionsDTO> partialUpdateSiegeLesions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiegeLesionsDTO siegeLesionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiegeLesions partially : {}, {}", id, siegeLesionsDTO);
        if (siegeLesionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siegeLesionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siegeLesionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiegeLesionsDTO> result = siegeLesionsService.partialUpdate(siegeLesionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siegeLesionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /siege-lesions} : get all the siegeLesions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siegeLesions in body.
     */
    @GetMapping("/siege-lesions")
    public ResponseEntity<List<SiegeLesionsDTO>> getAllSiegeLesions(Pageable pageable) {
        log.debug("REST request to get a page of SiegeLesions");
        Page<SiegeLesionsDTO> page = siegeLesionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /siege-lesions/:id} : get the "id" siegeLesions.
     *
     * @param id the id of the siegeLesionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siegeLesionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/siege-lesions/{id}")
    public ResponseEntity<SiegeLesionsDTO> getSiegeLesions(@PathVariable Long id) {
        log.debug("REST request to get SiegeLesions : {}", id);
        Optional<SiegeLesionsDTO> siegeLesionsDTO = siegeLesionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siegeLesionsDTO);
    }

    /**
     * {@code DELETE  /siege-lesions/:id} : delete the "id" siegeLesions.
     *
     * @param id the id of the siegeLesionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/siege-lesions/{id}")
    public ResponseEntity<Void> deleteSiegeLesions(@PathVariable Long id) {
        log.debug("REST request to delete SiegeLesions : {}", id);
        siegeLesionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
