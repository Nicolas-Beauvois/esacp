package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.OrigineLesionsRepository;
import fr.delpharm.esacp.service.OrigineLesionsService;
import fr.delpharm.esacp.service.dto.OrigineLesionsDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.OrigineLesions}.
 */
@RestController
@RequestMapping("/api")
public class OrigineLesionsResource {

    private final Logger log = LoggerFactory.getLogger(OrigineLesionsResource.class);

    private static final String ENTITY_NAME = "origineLesions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrigineLesionsService origineLesionsService;

    private final OrigineLesionsRepository origineLesionsRepository;

    public OrigineLesionsResource(OrigineLesionsService origineLesionsService, OrigineLesionsRepository origineLesionsRepository) {
        this.origineLesionsService = origineLesionsService;
        this.origineLesionsRepository = origineLesionsRepository;
    }

    /**
     * {@code POST  /origine-lesions} : Create a new origineLesions.
     *
     * @param origineLesionsDTO the origineLesionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new origineLesionsDTO, or with status {@code 400 (Bad Request)} if the origineLesions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origine-lesions")
    public ResponseEntity<OrigineLesionsDTO> createOrigineLesions(@RequestBody OrigineLesionsDTO origineLesionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrigineLesions : {}", origineLesionsDTO);
        if (origineLesionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new origineLesions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrigineLesionsDTO result = origineLesionsService.save(origineLesionsDTO);
        return ResponseEntity
            .created(new URI("/api/origine-lesions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origine-lesions/:id} : Updates an existing origineLesions.
     *
     * @param id the id of the origineLesionsDTO to save.
     * @param origineLesionsDTO the origineLesionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated origineLesionsDTO,
     * or with status {@code 400 (Bad Request)} if the origineLesionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the origineLesionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origine-lesions/{id}")
    public ResponseEntity<OrigineLesionsDTO> updateOrigineLesions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrigineLesionsDTO origineLesionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrigineLesions : {}, {}", id, origineLesionsDTO);
        if (origineLesionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, origineLesionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!origineLesionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrigineLesionsDTO result = origineLesionsService.save(origineLesionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, origineLesionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /origine-lesions/:id} : Partial updates given fields of an existing origineLesions, field will ignore if it is null
     *
     * @param id the id of the origineLesionsDTO to save.
     * @param origineLesionsDTO the origineLesionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated origineLesionsDTO,
     * or with status {@code 400 (Bad Request)} if the origineLesionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the origineLesionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the origineLesionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/origine-lesions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrigineLesionsDTO> partialUpdateOrigineLesions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrigineLesionsDTO origineLesionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrigineLesions partially : {}, {}", id, origineLesionsDTO);
        if (origineLesionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, origineLesionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!origineLesionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrigineLesionsDTO> result = origineLesionsService.partialUpdate(origineLesionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, origineLesionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /origine-lesions} : get all the origineLesions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of origineLesions in body.
     */
    @GetMapping("/origine-lesions")
    public ResponseEntity<List<OrigineLesionsDTO>> getAllOrigineLesions(Pageable pageable) {
        log.debug("REST request to get a page of OrigineLesions");
        Page<OrigineLesionsDTO> page = origineLesionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /origine-lesions/:id} : get the "id" origineLesions.
     *
     * @param id the id of the origineLesionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the origineLesionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origine-lesions/{id}")
    public ResponseEntity<OrigineLesionsDTO> getOrigineLesions(@PathVariable Long id) {
        log.debug("REST request to get OrigineLesions : {}", id);
        Optional<OrigineLesionsDTO> origineLesionsDTO = origineLesionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(origineLesionsDTO);
    }

    /**
     * {@code DELETE  /origine-lesions/:id} : delete the "id" origineLesions.
     *
     * @param id the id of the origineLesionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origine-lesions/{id}")
    public ResponseEntity<Void> deleteOrigineLesions(@PathVariable Long id) {
        log.debug("REST request to delete OrigineLesions : {}", id);
        origineLesionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
