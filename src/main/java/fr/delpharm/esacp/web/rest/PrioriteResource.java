package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.PrioriteRepository;
import fr.delpharm.esacp.service.PrioriteService;
import fr.delpharm.esacp.service.dto.PrioriteDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.Priorite}.
 */
@RestController
@RequestMapping("/api")
public class PrioriteResource {

    private final Logger log = LoggerFactory.getLogger(PrioriteResource.class);

    private static final String ENTITY_NAME = "priorite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrioriteService prioriteService;

    private final PrioriteRepository prioriteRepository;

    public PrioriteResource(PrioriteService prioriteService, PrioriteRepository prioriteRepository) {
        this.prioriteService = prioriteService;
        this.prioriteRepository = prioriteRepository;
    }

    /**
     * {@code POST  /priorites} : Create a new priorite.
     *
     * @param prioriteDTO the prioriteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prioriteDTO, or with status {@code 400 (Bad Request)} if the priorite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/priorites")
    public ResponseEntity<PrioriteDTO> createPriorite(@RequestBody PrioriteDTO prioriteDTO) throws URISyntaxException {
        log.debug("REST request to save Priorite : {}", prioriteDTO);
        if (prioriteDTO.getId() != null) {
            throw new BadRequestAlertException("A new priorite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrioriteDTO result = prioriteService.save(prioriteDTO);
        return ResponseEntity
            .created(new URI("/api/priorites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /priorites/:id} : Updates an existing priorite.
     *
     * @param id the id of the prioriteDTO to save.
     * @param prioriteDTO the prioriteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prioriteDTO,
     * or with status {@code 400 (Bad Request)} if the prioriteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prioriteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/priorites/{id}")
    public ResponseEntity<PrioriteDTO> updatePriorite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrioriteDTO prioriteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Priorite : {}, {}", id, prioriteDTO);
        if (prioriteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prioriteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prioriteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrioriteDTO result = prioriteService.save(prioriteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prioriteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /priorites/:id} : Partial updates given fields of an existing priorite, field will ignore if it is null
     *
     * @param id the id of the prioriteDTO to save.
     * @param prioriteDTO the prioriteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prioriteDTO,
     * or with status {@code 400 (Bad Request)} if the prioriteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prioriteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the prioriteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/priorites/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PrioriteDTO> partialUpdatePriorite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrioriteDTO prioriteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Priorite partially : {}, {}", id, prioriteDTO);
        if (prioriteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prioriteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prioriteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrioriteDTO> result = prioriteService.partialUpdate(prioriteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prioriteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /priorites} : get all the priorites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priorites in body.
     */
    @GetMapping("/priorites")
    public ResponseEntity<List<PrioriteDTO>> getAllPriorites(Pageable pageable) {
        log.debug("REST request to get a page of Priorites");
        Page<PrioriteDTO> page = prioriteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /priorites/:id} : get the "id" priorite.
     *
     * @param id the id of the prioriteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prioriteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/priorites/{id}")
    public ResponseEntity<PrioriteDTO> getPriorite(@PathVariable Long id) {
        log.debug("REST request to get Priorite : {}", id);
        Optional<PrioriteDTO> prioriteDTO = prioriteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prioriteDTO);
    }

    /**
     * {@code DELETE  /priorites/:id} : delete the "id" priorite.
     *
     * @param id the id of the prioriteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/priorites/{id}")
    public ResponseEntity<Void> deletePriorite(@PathVariable Long id) {
        log.debug("REST request to delete Priorite : {}", id);
        prioriteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
