package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.CriticiteRepository;
import fr.delpharm.esacp.service.CriticiteService;
import fr.delpharm.esacp.service.dto.CriticiteDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.Criticite}.
 */
@RestController
@RequestMapping("/api")
public class CriticiteResource {

    private final Logger log = LoggerFactory.getLogger(CriticiteResource.class);

    private static final String ENTITY_NAME = "criticite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CriticiteService criticiteService;

    private final CriticiteRepository criticiteRepository;

    public CriticiteResource(CriticiteService criticiteService, CriticiteRepository criticiteRepository) {
        this.criticiteService = criticiteService;
        this.criticiteRepository = criticiteRepository;
    }

    /**
     * {@code POST  /criticites} : Create a new criticite.
     *
     * @param criticiteDTO the criticiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new criticiteDTO, or with status {@code 400 (Bad Request)} if the criticite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/criticites")
    public ResponseEntity<CriticiteDTO> createCriticite(@RequestBody CriticiteDTO criticiteDTO) throws URISyntaxException {
        log.debug("REST request to save Criticite : {}", criticiteDTO);
        if (criticiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new criticite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CriticiteDTO result = criticiteService.save(criticiteDTO);
        return ResponseEntity
            .created(new URI("/api/criticites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /criticites/:id} : Updates an existing criticite.
     *
     * @param id the id of the criticiteDTO to save.
     * @param criticiteDTO the criticiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criticiteDTO,
     * or with status {@code 400 (Bad Request)} if the criticiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the criticiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/criticites/{id}")
    public ResponseEntity<CriticiteDTO> updateCriticite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CriticiteDTO criticiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Criticite : {}, {}", id, criticiteDTO);
        if (criticiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criticiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criticiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CriticiteDTO result = criticiteService.save(criticiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criticiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /criticites/:id} : Partial updates given fields of an existing criticite, field will ignore if it is null
     *
     * @param id the id of the criticiteDTO to save.
     * @param criticiteDTO the criticiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criticiteDTO,
     * or with status {@code 400 (Bad Request)} if the criticiteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the criticiteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the criticiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/criticites/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CriticiteDTO> partialUpdateCriticite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CriticiteDTO criticiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Criticite partially : {}, {}", id, criticiteDTO);
        if (criticiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criticiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criticiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CriticiteDTO> result = criticiteService.partialUpdate(criticiteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criticiteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /criticites} : get all the criticites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of criticites in body.
     */
    @GetMapping("/criticites")
    public ResponseEntity<List<CriticiteDTO>> getAllCriticites(Pageable pageable) {
        log.debug("REST request to get a page of Criticites");
        Page<CriticiteDTO> page = criticiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /criticites/:id} : get the "id" criticite.
     *
     * @param id the id of the criticiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the criticiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/criticites/{id}")
    public ResponseEntity<CriticiteDTO> getCriticite(@PathVariable Long id) {
        log.debug("REST request to get Criticite : {}", id);
        Optional<CriticiteDTO> criticiteDTO = criticiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criticiteDTO);
    }

    /**
     * {@code DELETE  /criticites/:id} : delete the "id" criticite.
     *
     * @param id the id of the criticiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/criticites/{id}")
    public ResponseEntity<Void> deleteCriticite(@PathVariable Long id) {
        log.debug("REST request to delete Criticite : {}", id);
        criticiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
