package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.NatureAccidentRepository;
import fr.delpharm.esacp.service.NatureAccidentService;
import fr.delpharm.esacp.service.dto.NatureAccidentDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.NatureAccident}.
 */
@RestController
@RequestMapping("/api")
public class NatureAccidentResource {

    private final Logger log = LoggerFactory.getLogger(NatureAccidentResource.class);

    private static final String ENTITY_NAME = "natureAccident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureAccidentService natureAccidentService;

    private final NatureAccidentRepository natureAccidentRepository;

    public NatureAccidentResource(NatureAccidentService natureAccidentService, NatureAccidentRepository natureAccidentRepository) {
        this.natureAccidentService = natureAccidentService;
        this.natureAccidentRepository = natureAccidentRepository;
    }

    /**
     * {@code POST  /nature-accidents} : Create a new natureAccident.
     *
     * @param natureAccidentDTO the natureAccidentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureAccidentDTO, or with status {@code 400 (Bad Request)} if the natureAccident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-accidents")
    public ResponseEntity<NatureAccidentDTO> createNatureAccident(@RequestBody NatureAccidentDTO natureAccidentDTO)
        throws URISyntaxException {
        log.debug("REST request to save NatureAccident : {}", natureAccidentDTO);
        if (natureAccidentDTO.getId() != null) {
            throw new BadRequestAlertException("A new natureAccident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureAccidentDTO result = natureAccidentService.save(natureAccidentDTO);
        return ResponseEntity
            .created(new URI("/api/nature-accidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-accidents/:id} : Updates an existing natureAccident.
     *
     * @param id the id of the natureAccidentDTO to save.
     * @param natureAccidentDTO the natureAccidentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAccidentDTO,
     * or with status {@code 400 (Bad Request)} if the natureAccidentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureAccidentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-accidents/{id}")
    public ResponseEntity<NatureAccidentDTO> updateNatureAccident(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAccidentDTO natureAccidentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NatureAccident : {}, {}", id, natureAccidentDTO);
        if (natureAccidentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAccidentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAccidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureAccidentDTO result = natureAccidentService.save(natureAccidentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureAccidentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-accidents/:id} : Partial updates given fields of an existing natureAccident, field will ignore if it is null
     *
     * @param id the id of the natureAccidentDTO to save.
     * @param natureAccidentDTO the natureAccidentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAccidentDTO,
     * or with status {@code 400 (Bad Request)} if the natureAccidentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the natureAccidentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureAccidentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-accidents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NatureAccidentDTO> partialUpdateNatureAccident(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAccidentDTO natureAccidentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureAccident partially : {}, {}", id, natureAccidentDTO);
        if (natureAccidentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAccidentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAccidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureAccidentDTO> result = natureAccidentService.partialUpdate(natureAccidentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureAccidentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-accidents} : get all the natureAccidents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureAccidents in body.
     */
    @GetMapping("/nature-accidents")
    public ResponseEntity<List<NatureAccidentDTO>> getAllNatureAccidents(Pageable pageable) {
        log.debug("REST request to get a page of NatureAccidents");
        Page<NatureAccidentDTO> page = natureAccidentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-accidents/:id} : get the "id" natureAccident.
     *
     * @param id the id of the natureAccidentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureAccidentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-accidents/{id}")
    public ResponseEntity<NatureAccidentDTO> getNatureAccident(@PathVariable Long id) {
        log.debug("REST request to get NatureAccident : {}", id);
        Optional<NatureAccidentDTO> natureAccidentDTO = natureAccidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureAccidentDTO);
    }

    /**
     * {@code DELETE  /nature-accidents/:id} : delete the "id" natureAccident.
     *
     * @param id the id of the natureAccidentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-accidents/{id}")
    public ResponseEntity<Void> deleteNatureAccident(@PathVariable Long id) {
        log.debug("REST request to delete NatureAccident : {}", id);
        natureAccidentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
