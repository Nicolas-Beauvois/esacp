package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.EtapeValidationRepository;
import fr.delpharm.esacp.service.EtapeValidationService;
import fr.delpharm.esacp.service.dto.EtapeValidationDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.EtapeValidation}.
 */
@RestController
@RequestMapping("/api")
public class EtapeValidationResource {

    private final Logger log = LoggerFactory.getLogger(EtapeValidationResource.class);

    private static final String ENTITY_NAME = "etapeValidation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapeValidationService etapeValidationService;

    private final EtapeValidationRepository etapeValidationRepository;

    public EtapeValidationResource(EtapeValidationService etapeValidationService, EtapeValidationRepository etapeValidationRepository) {
        this.etapeValidationService = etapeValidationService;
        this.etapeValidationRepository = etapeValidationRepository;
    }

    /**
     * {@code POST  /etape-validations} : Create a new etapeValidation.
     *
     * @param etapeValidationDTO the etapeValidationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapeValidationDTO, or with status {@code 400 (Bad Request)} if the etapeValidation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etape-validations")
    public ResponseEntity<EtapeValidationDTO> createEtapeValidation(@RequestBody EtapeValidationDTO etapeValidationDTO)
        throws URISyntaxException {
        log.debug("REST request to save EtapeValidation : {}", etapeValidationDTO);
        if (etapeValidationDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapeValidation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapeValidationDTO result = etapeValidationService.save(etapeValidationDTO);
        return ResponseEntity
            .created(new URI("/api/etape-validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etape-validations/:id} : Updates an existing etapeValidation.
     *
     * @param id the id of the etapeValidationDTO to save.
     * @param etapeValidationDTO the etapeValidationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapeValidationDTO,
     * or with status {@code 400 (Bad Request)} if the etapeValidationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapeValidationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etape-validations/{id}")
    public ResponseEntity<EtapeValidationDTO> updateEtapeValidation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtapeValidationDTO etapeValidationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EtapeValidation : {}, {}", id, etapeValidationDTO);
        if (etapeValidationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapeValidationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapeValidationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtapeValidationDTO result = etapeValidationService.save(etapeValidationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapeValidationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etape-validations/:id} : Partial updates given fields of an existing etapeValidation, field will ignore if it is null
     *
     * @param id the id of the etapeValidationDTO to save.
     * @param etapeValidationDTO the etapeValidationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapeValidationDTO,
     * or with status {@code 400 (Bad Request)} if the etapeValidationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the etapeValidationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the etapeValidationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etape-validations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EtapeValidationDTO> partialUpdateEtapeValidation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtapeValidationDTO etapeValidationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtapeValidation partially : {}, {}", id, etapeValidationDTO);
        if (etapeValidationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapeValidationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapeValidationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtapeValidationDTO> result = etapeValidationService.partialUpdate(etapeValidationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapeValidationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /etape-validations} : get all the etapeValidations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapeValidations in body.
     */
    @GetMapping("/etape-validations")
    public ResponseEntity<List<EtapeValidationDTO>> getAllEtapeValidations(Pageable pageable) {
        log.debug("REST request to get a page of EtapeValidations");
        Page<EtapeValidationDTO> page = etapeValidationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etape-validations/:id} : get the "id" etapeValidation.
     *
     * @param id the id of the etapeValidationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapeValidationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etape-validations/{id}")
    public ResponseEntity<EtapeValidationDTO> getEtapeValidation(@PathVariable Long id) {
        log.debug("REST request to get EtapeValidation : {}", id);
        Optional<EtapeValidationDTO> etapeValidationDTO = etapeValidationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapeValidationDTO);
    }

    /**
     * {@code DELETE  /etape-validations/:id} : delete the "id" etapeValidation.
     *
     * @param id the id of the etapeValidationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etape-validations/{id}")
    public ResponseEntity<Void> deleteEtapeValidation(@PathVariable Long id) {
        log.debug("REST request to delete EtapeValidation : {}", id);
        etapeValidationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
