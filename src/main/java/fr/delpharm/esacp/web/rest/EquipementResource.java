package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.EquipementRepository;
import fr.delpharm.esacp.service.EquipementService;
import fr.delpharm.esacp.service.dto.EquipementDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.Equipement}.
 */
@RestController
@RequestMapping("/api")
public class EquipementResource {

    private final Logger log = LoggerFactory.getLogger(EquipementResource.class);

    private static final String ENTITY_NAME = "equipement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipementService equipementService;

    private final EquipementRepository equipementRepository;

    public EquipementResource(EquipementService equipementService, EquipementRepository equipementRepository) {
        this.equipementService = equipementService;
        this.equipementRepository = equipementRepository;
    }

    /**
     * {@code POST  /equipements} : Create a new equipement.
     *
     * @param equipementDTO the equipementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipementDTO, or with status {@code 400 (Bad Request)} if the equipement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipements")
    public ResponseEntity<EquipementDTO> createEquipement(@RequestBody EquipementDTO equipementDTO) throws URISyntaxException {
        log.debug("REST request to save Equipement : {}", equipementDTO);
        if (equipementDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipementDTO result = equipementService.save(equipementDTO);
        return ResponseEntity
            .created(new URI("/api/equipements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipements/:id} : Updates an existing equipement.
     *
     * @param id the id of the equipementDTO to save.
     * @param equipementDTO the equipementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipementDTO,
     * or with status {@code 400 (Bad Request)} if the equipementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipements/{id}")
    public ResponseEntity<EquipementDTO> updateEquipement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EquipementDTO equipementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Equipement : {}, {}", id, equipementDTO);
        if (equipementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EquipementDTO result = equipementService.save(equipementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipements/:id} : Partial updates given fields of an existing equipement, field will ignore if it is null
     *
     * @param id the id of the equipementDTO to save.
     * @param equipementDTO the equipementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipementDTO,
     * or with status {@code 400 (Bad Request)} if the equipementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the equipementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/equipements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EquipementDTO> partialUpdateEquipement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EquipementDTO equipementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipement partially : {}, {}", id, equipementDTO);
        if (equipementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipementDTO> result = equipementService.partialUpdate(equipementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /equipements} : get all the equipements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipements in body.
     */
    @GetMapping("/equipements")
    public ResponseEntity<List<EquipementDTO>> getAllEquipements(Pageable pageable) {
        log.debug("REST request to get a page of Equipements");
        Page<EquipementDTO> page = equipementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipements/:id} : get the "id" equipement.
     *
     * @param id the id of the equipementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipements/{id}")
    public ResponseEntity<EquipementDTO> getEquipement(@PathVariable Long id) {
        log.debug("REST request to get Equipement : {}", id);
        Optional<EquipementDTO> equipementDTO = equipementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipementDTO);
    }

    /**
     * {@code DELETE  /equipements/:id} : delete the "id" equipement.
     *
     * @param id the id of the equipementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipements/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable Long id) {
        log.debug("REST request to delete Equipement : {}", id);
        equipementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
