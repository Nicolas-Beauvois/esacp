package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.SexeRepository;
import fr.delpharm.esacp.service.SexeService;
import fr.delpharm.esacp.service.dto.SexeDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.Sexe}.
 */
@RestController
@RequestMapping("/api")
public class SexeResource {

    private final Logger log = LoggerFactory.getLogger(SexeResource.class);

    private static final String ENTITY_NAME = "sexe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SexeService sexeService;

    private final SexeRepository sexeRepository;

    public SexeResource(SexeService sexeService, SexeRepository sexeRepository) {
        this.sexeService = sexeService;
        this.sexeRepository = sexeRepository;
    }

    /**
     * {@code POST  /sexes} : Create a new sexe.
     *
     * @param sexeDTO the sexeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sexeDTO, or with status {@code 400 (Bad Request)} if the sexe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sexes")
    public ResponseEntity<SexeDTO> createSexe(@RequestBody SexeDTO sexeDTO) throws URISyntaxException {
        log.debug("REST request to save Sexe : {}", sexeDTO);
        if (sexeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sexe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SexeDTO result = sexeService.save(sexeDTO);
        return ResponseEntity
            .created(new URI("/api/sexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sexes/:id} : Updates an existing sexe.
     *
     * @param id the id of the sexeDTO to save.
     * @param sexeDTO the sexeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sexeDTO,
     * or with status {@code 400 (Bad Request)} if the sexeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sexeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sexes/{id}")
    public ResponseEntity<SexeDTO> updateSexe(@PathVariable(value = "id", required = false) final Long id, @RequestBody SexeDTO sexeDTO)
        throws URISyntaxException {
        log.debug("REST request to update Sexe : {}, {}", id, sexeDTO);
        if (sexeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sexeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sexeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SexeDTO result = sexeService.save(sexeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sexeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sexes/:id} : Partial updates given fields of an existing sexe, field will ignore if it is null
     *
     * @param id the id of the sexeDTO to save.
     * @param sexeDTO the sexeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sexeDTO,
     * or with status {@code 400 (Bad Request)} if the sexeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sexeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sexeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sexes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SexeDTO> partialUpdateSexe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SexeDTO sexeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sexe partially : {}, {}", id, sexeDTO);
        if (sexeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sexeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sexeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SexeDTO> result = sexeService.partialUpdate(sexeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sexeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sexes} : get all the sexes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sexes in body.
     */
    @GetMapping("/sexes")
    public ResponseEntity<List<SexeDTO>> getAllSexes(Pageable pageable) {
        log.debug("REST request to get a page of Sexes");
        Page<SexeDTO> page = sexeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sexes/:id} : get the "id" sexe.
     *
     * @param id the id of the sexeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sexeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sexes/{id}")
    public ResponseEntity<SexeDTO> getSexe(@PathVariable Long id) {
        log.debug("REST request to get Sexe : {}", id);
        Optional<SexeDTO> sexeDTO = sexeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sexeDTO);
    }

    /**
     * {@code DELETE  /sexes/:id} : delete the "id" sexe.
     *
     * @param id the id of the sexeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sexes/{id}")
    public ResponseEntity<Void> deleteSexe(@PathVariable Long id) {
        log.debug("REST request to delete Sexe : {}", id);
        sexeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
