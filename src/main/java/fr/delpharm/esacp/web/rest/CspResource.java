package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.CspRepository;
import fr.delpharm.esacp.service.CspService;
import fr.delpharm.esacp.service.dto.CspDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.Csp}.
 */
@RestController
@RequestMapping("/api")
public class CspResource {

    private final Logger log = LoggerFactory.getLogger(CspResource.class);

    private static final String ENTITY_NAME = "csp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CspService cspService;

    private final CspRepository cspRepository;

    public CspResource(CspService cspService, CspRepository cspRepository) {
        this.cspService = cspService;
        this.cspRepository = cspRepository;
    }

    /**
     * {@code POST  /csps} : Create a new csp.
     *
     * @param cspDTO the cspDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cspDTO, or with status {@code 400 (Bad Request)} if the csp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/csps")
    public ResponseEntity<CspDTO> createCsp(@RequestBody CspDTO cspDTO) throws URISyntaxException {
        log.debug("REST request to save Csp : {}", cspDTO);
        if (cspDTO.getId() != null) {
            throw new BadRequestAlertException("A new csp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CspDTO result = cspService.save(cspDTO);
        return ResponseEntity
            .created(new URI("/api/csps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /csps/:id} : Updates an existing csp.
     *
     * @param id the id of the cspDTO to save.
     * @param cspDTO the cspDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cspDTO,
     * or with status {@code 400 (Bad Request)} if the cspDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cspDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/csps/{id}")
    public ResponseEntity<CspDTO> updateCsp(@PathVariable(value = "id", required = false) final Long id, @RequestBody CspDTO cspDTO)
        throws URISyntaxException {
        log.debug("REST request to update Csp : {}, {}", id, cspDTO);
        if (cspDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cspDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cspRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CspDTO result = cspService.save(cspDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cspDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /csps/:id} : Partial updates given fields of an existing csp, field will ignore if it is null
     *
     * @param id the id of the cspDTO to save.
     * @param cspDTO the cspDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cspDTO,
     * or with status {@code 400 (Bad Request)} if the cspDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cspDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cspDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/csps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CspDTO> partialUpdateCsp(@PathVariable(value = "id", required = false) final Long id, @RequestBody CspDTO cspDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Csp partially : {}, {}", id, cspDTO);
        if (cspDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cspDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cspRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CspDTO> result = cspService.partialUpdate(cspDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cspDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /csps} : get all the csps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of csps in body.
     */
    @GetMapping("/csps")
    public ResponseEntity<List<CspDTO>> getAllCsps(Pageable pageable) {
        log.debug("REST request to get a page of Csps");
        Page<CspDTO> page = cspService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /csps/:id} : get the "id" csp.
     *
     * @param id the id of the cspDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cspDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/csps/{id}")
    public ResponseEntity<CspDTO> getCsp(@PathVariable Long id) {
        log.debug("REST request to get Csp : {}", id);
        Optional<CspDTO> cspDTO = cspService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cspDTO);
    }

    /**
     * {@code DELETE  /csps/:id} : delete the "id" csp.
     *
     * @param id the id of the cspDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/csps/{id}")
    public ResponseEntity<Void> deleteCsp(@PathVariable Long id) {
        log.debug("REST request to delete Csp : {}", id);
        cspService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
