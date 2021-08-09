package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.FicheSuiviSanteRepository;
import fr.delpharm.esacp.service.FicheSuiviSanteService;
import fr.delpharm.esacp.service.dto.FicheSuiviSanteDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.FicheSuiviSante}.
 */
@RestController
@RequestMapping("/api")
public class FicheSuiviSanteResource {

    private final Logger log = LoggerFactory.getLogger(FicheSuiviSanteResource.class);

    private static final String ENTITY_NAME = "ficheSuiviSante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FicheSuiviSanteService ficheSuiviSanteService;

    private final FicheSuiviSanteRepository ficheSuiviSanteRepository;

    public FicheSuiviSanteResource(FicheSuiviSanteService ficheSuiviSanteService, FicheSuiviSanteRepository ficheSuiviSanteRepository) {
        this.ficheSuiviSanteService = ficheSuiviSanteService;
        this.ficheSuiviSanteRepository = ficheSuiviSanteRepository;
    }

    /**
     * {@code POST  /fiche-suivi-santes} : Create a new ficheSuiviSante.
     *
     * @param ficheSuiviSanteDTO the ficheSuiviSanteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ficheSuiviSanteDTO, or with status {@code 400 (Bad Request)} if the ficheSuiviSante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fiche-suivi-santes")
    public ResponseEntity<FicheSuiviSanteDTO> createFicheSuiviSante(@RequestBody FicheSuiviSanteDTO ficheSuiviSanteDTO)
        throws URISyntaxException {
        log.debug("REST request to save FicheSuiviSante : {}", ficheSuiviSanteDTO);
        if (ficheSuiviSanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ficheSuiviSante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FicheSuiviSanteDTO result = ficheSuiviSanteService.save(ficheSuiviSanteDTO);
        return ResponseEntity
            .created(new URI("/api/fiche-suivi-santes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fiche-suivi-santes/:id} : Updates an existing ficheSuiviSante.
     *
     * @param id the id of the ficheSuiviSanteDTO to save.
     * @param ficheSuiviSanteDTO the ficheSuiviSanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ficheSuiviSanteDTO,
     * or with status {@code 400 (Bad Request)} if the ficheSuiviSanteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ficheSuiviSanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fiche-suivi-santes/{id}")
    public ResponseEntity<FicheSuiviSanteDTO> updateFicheSuiviSante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FicheSuiviSanteDTO ficheSuiviSanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FicheSuiviSante : {}, {}", id, ficheSuiviSanteDTO);
        if (ficheSuiviSanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ficheSuiviSanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ficheSuiviSanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FicheSuiviSanteDTO result = ficheSuiviSanteService.save(ficheSuiviSanteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ficheSuiviSanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fiche-suivi-santes/:id} : Partial updates given fields of an existing ficheSuiviSante, field will ignore if it is null
     *
     * @param id the id of the ficheSuiviSanteDTO to save.
     * @param ficheSuiviSanteDTO the ficheSuiviSanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ficheSuiviSanteDTO,
     * or with status {@code 400 (Bad Request)} if the ficheSuiviSanteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ficheSuiviSanteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ficheSuiviSanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fiche-suivi-santes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FicheSuiviSanteDTO> partialUpdateFicheSuiviSante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FicheSuiviSanteDTO ficheSuiviSanteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FicheSuiviSante partially : {}, {}", id, ficheSuiviSanteDTO);
        if (ficheSuiviSanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ficheSuiviSanteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ficheSuiviSanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FicheSuiviSanteDTO> result = ficheSuiviSanteService.partialUpdate(ficheSuiviSanteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ficheSuiviSanteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fiche-suivi-santes} : get all the ficheSuiviSantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ficheSuiviSantes in body.
     */
    @GetMapping("/fiche-suivi-santes")
    public ResponseEntity<List<FicheSuiviSanteDTO>> getAllFicheSuiviSantes(Pageable pageable) {
        log.debug("REST request to get a page of FicheSuiviSantes");
        Page<FicheSuiviSanteDTO> page = ficheSuiviSanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fiche-suivi-santes/:id} : get the "id" ficheSuiviSante.
     *
     * @param id the id of the ficheSuiviSanteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ficheSuiviSanteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fiche-suivi-santes/{id}")
    public ResponseEntity<FicheSuiviSanteDTO> getFicheSuiviSante(@PathVariable Long id) {
        log.debug("REST request to get FicheSuiviSante : {}", id);
        Optional<FicheSuiviSanteDTO> ficheSuiviSanteDTO = ficheSuiviSanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ficheSuiviSanteDTO);
    }

    /**
     * {@code DELETE  /fiche-suivi-santes/:id} : delete the "id" ficheSuiviSante.
     *
     * @param id the id of the ficheSuiviSanteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fiche-suivi-santes/{id}")
    public ResponseEntity<Void> deleteFicheSuiviSante(@PathVariable Long id) {
        log.debug("REST request to delete FicheSuiviSante : {}", id);
        ficheSuiviSanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
