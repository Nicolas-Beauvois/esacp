package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.RepartitionRepository;
import fr.delpharm.esacp.service.RepartitionService;
import fr.delpharm.esacp.service.dto.RepartitionDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.Repartition}.
 */
@RestController
@RequestMapping("/api")
public class RepartitionResource {

    private final Logger log = LoggerFactory.getLogger(RepartitionResource.class);

    private static final String ENTITY_NAME = "repartition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepartitionService repartitionService;

    private final RepartitionRepository repartitionRepository;

    public RepartitionResource(RepartitionService repartitionService, RepartitionRepository repartitionRepository) {
        this.repartitionService = repartitionService;
        this.repartitionRepository = repartitionRepository;
    }

    /**
     * {@code POST  /repartitions} : Create a new repartition.
     *
     * @param repartitionDTO the repartitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repartitionDTO, or with status {@code 400 (Bad Request)} if the repartition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/repartitions")
    public ResponseEntity<RepartitionDTO> createRepartition(@RequestBody RepartitionDTO repartitionDTO) throws URISyntaxException {
        log.debug("REST request to save Repartition : {}", repartitionDTO);
        if (repartitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new repartition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepartitionDTO result = repartitionService.save(repartitionDTO);
        return ResponseEntity
            .created(new URI("/api/repartitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /repartitions/:id} : Updates an existing repartition.
     *
     * @param id the id of the repartitionDTO to save.
     * @param repartitionDTO the repartitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repartitionDTO,
     * or with status {@code 400 (Bad Request)} if the repartitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repartitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/repartitions/{id}")
    public ResponseEntity<RepartitionDTO> updateRepartition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepartitionDTO repartitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Repartition : {}, {}", id, repartitionDTO);
        if (repartitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repartitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repartitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RepartitionDTO result = repartitionService.save(repartitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, repartitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /repartitions/:id} : Partial updates given fields of an existing repartition, field will ignore if it is null
     *
     * @param id the id of the repartitionDTO to save.
     * @param repartitionDTO the repartitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repartitionDTO,
     * or with status {@code 400 (Bad Request)} if the repartitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the repartitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the repartitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/repartitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RepartitionDTO> partialUpdateRepartition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepartitionDTO repartitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Repartition partially : {}, {}", id, repartitionDTO);
        if (repartitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repartitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repartitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RepartitionDTO> result = repartitionService.partialUpdate(repartitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, repartitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /repartitions} : get all the repartitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repartitions in body.
     */
    @GetMapping("/repartitions")
    public ResponseEntity<List<RepartitionDTO>> getAllRepartitions(Pageable pageable) {
        log.debug("REST request to get a page of Repartitions");
        Page<RepartitionDTO> page = repartitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /repartitions/:id} : get the "id" repartition.
     *
     * @param id the id of the repartitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repartitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/repartitions/{id}")
    public ResponseEntity<RepartitionDTO> getRepartition(@PathVariable Long id) {
        log.debug("REST request to get Repartition : {}", id);
        Optional<RepartitionDTO> repartitionDTO = repartitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(repartitionDTO);
    }

    /**
     * {@code DELETE  /repartitions/:id} : delete the "id" repartition.
     *
     * @param id the id of the repartitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/repartitions/{id}")
    public ResponseEntity<Void> deleteRepartition(@PathVariable Long id) {
        log.debug("REST request to delete Repartition : {}", id);
        repartitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
