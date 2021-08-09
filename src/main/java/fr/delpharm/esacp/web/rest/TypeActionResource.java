package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.TypeActionRepository;
import fr.delpharm.esacp.service.TypeActionService;
import fr.delpharm.esacp.service.dto.TypeActionDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.TypeAction}.
 */
@RestController
@RequestMapping("/api")
public class TypeActionResource {

    private final Logger log = LoggerFactory.getLogger(TypeActionResource.class);

    private static final String ENTITY_NAME = "typeAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeActionService typeActionService;

    private final TypeActionRepository typeActionRepository;

    public TypeActionResource(TypeActionService typeActionService, TypeActionRepository typeActionRepository) {
        this.typeActionService = typeActionService;
        this.typeActionRepository = typeActionRepository;
    }

    /**
     * {@code POST  /type-actions} : Create a new typeAction.
     *
     * @param typeActionDTO the typeActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeActionDTO, or with status {@code 400 (Bad Request)} if the typeAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-actions")
    public ResponseEntity<TypeActionDTO> createTypeAction(@RequestBody TypeActionDTO typeActionDTO) throws URISyntaxException {
        log.debug("REST request to save TypeAction : {}", typeActionDTO);
        if (typeActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeActionDTO result = typeActionService.save(typeActionDTO);
        return ResponseEntity
            .created(new URI("/api/type-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-actions/:id} : Updates an existing typeAction.
     *
     * @param id the id of the typeActionDTO to save.
     * @param typeActionDTO the typeActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeActionDTO,
     * or with status {@code 400 (Bad Request)} if the typeActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-actions/{id}")
    public ResponseEntity<TypeActionDTO> updateTypeAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeActionDTO typeActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeAction : {}, {}", id, typeActionDTO);
        if (typeActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeActionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeActionDTO result = typeActionService.save(typeActionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-actions/:id} : Partial updates given fields of an existing typeAction, field will ignore if it is null
     *
     * @param id the id of the typeActionDTO to save.
     * @param typeActionDTO the typeActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeActionDTO,
     * or with status {@code 400 (Bad Request)} if the typeActionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeActionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-actions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TypeActionDTO> partialUpdateTypeAction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeActionDTO typeActionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeAction partially : {}, {}", id, typeActionDTO);
        if (typeActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeActionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeActionDTO> result = typeActionService.partialUpdate(typeActionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeActionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-actions} : get all the typeActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeActions in body.
     */
    @GetMapping("/type-actions")
    public ResponseEntity<List<TypeActionDTO>> getAllTypeActions(Pageable pageable) {
        log.debug("REST request to get a page of TypeActions");
        Page<TypeActionDTO> page = typeActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-actions/:id} : get the "id" typeAction.
     *
     * @param id the id of the typeActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-actions/{id}")
    public ResponseEntity<TypeActionDTO> getTypeAction(@PathVariable Long id) {
        log.debug("REST request to get TypeAction : {}", id);
        Optional<TypeActionDTO> typeActionDTO = typeActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeActionDTO);
    }

    /**
     * {@code DELETE  /type-actions/:id} : delete the "id" typeAction.
     *
     * @param id the id of the typeActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-actions/{id}")
    public ResponseEntity<Void> deleteTypeAction(@PathVariable Long id) {
        log.debug("REST request to delete TypeAction : {}", id);
        typeActionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
