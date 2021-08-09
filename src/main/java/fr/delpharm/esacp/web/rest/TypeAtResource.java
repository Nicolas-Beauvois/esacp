package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.TypeAtRepository;
import fr.delpharm.esacp.service.TypeAtService;
import fr.delpharm.esacp.service.dto.TypeAtDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.TypeAt}.
 */
@RestController
@RequestMapping("/api")
public class TypeAtResource {

    private final Logger log = LoggerFactory.getLogger(TypeAtResource.class);

    private static final String ENTITY_NAME = "typeAt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAtService typeAtService;

    private final TypeAtRepository typeAtRepository;

    public TypeAtResource(TypeAtService typeAtService, TypeAtRepository typeAtRepository) {
        this.typeAtService = typeAtService;
        this.typeAtRepository = typeAtRepository;
    }

    /**
     * {@code POST  /type-ats} : Create a new typeAt.
     *
     * @param typeAtDTO the typeAtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAtDTO, or with status {@code 400 (Bad Request)} if the typeAt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-ats")
    public ResponseEntity<TypeAtDTO> createTypeAt(@RequestBody TypeAtDTO typeAtDTO) throws URISyntaxException {
        log.debug("REST request to save TypeAt : {}", typeAtDTO);
        if (typeAtDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeAt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAtDTO result = typeAtService.save(typeAtDTO);
        return ResponseEntity
            .created(new URI("/api/type-ats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-ats/:id} : Updates an existing typeAt.
     *
     * @param id the id of the typeAtDTO to save.
     * @param typeAtDTO the typeAtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAtDTO,
     * or with status {@code 400 (Bad Request)} if the typeAtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-ats/{id}")
    public ResponseEntity<TypeAtDTO> updateTypeAt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAtDTO typeAtDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeAt : {}, {}", id, typeAtDTO);
        if (typeAtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeAtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeAtDTO result = typeAtService.save(typeAtDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAtDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-ats/:id} : Partial updates given fields of an existing typeAt, field will ignore if it is null
     *
     * @param id the id of the typeAtDTO to save.
     * @param typeAtDTO the typeAtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAtDTO,
     * or with status {@code 400 (Bad Request)} if the typeAtDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeAtDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeAtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-ats/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TypeAtDTO> partialUpdateTypeAt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAtDTO typeAtDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeAt partially : {}, {}", id, typeAtDTO);
        if (typeAtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeAtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeAtDTO> result = typeAtService.partialUpdate(typeAtDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAtDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-ats} : get all the typeAts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAts in body.
     */
    @GetMapping("/type-ats")
    public ResponseEntity<List<TypeAtDTO>> getAllTypeAts(Pageable pageable) {
        log.debug("REST request to get a page of TypeAts");
        Page<TypeAtDTO> page = typeAtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-ats/:id} : get the "id" typeAt.
     *
     * @param id the id of the typeAtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-ats/{id}")
    public ResponseEntity<TypeAtDTO> getTypeAt(@PathVariable Long id) {
        log.debug("REST request to get TypeAt : {}", id);
        Optional<TypeAtDTO> typeAtDTO = typeAtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeAtDTO);
    }

    /**
     * {@code DELETE  /type-ats/:id} : delete the "id" typeAt.
     *
     * @param id the id of the typeAtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-ats/{id}")
    public ResponseEntity<Void> deleteTypeAt(@PathVariable Long id) {
        log.debug("REST request to delete TypeAt : {}", id);
        typeAtService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
