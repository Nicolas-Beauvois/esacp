package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.repository.ListingMailRepository;
import fr.delpharm.esacp.service.ListingMailService;
import fr.delpharm.esacp.service.dto.ListingMailDTO;
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
 * REST controller for managing {@link fr.delpharm.esacp.domain.ListingMail}.
 */
@RestController
@RequestMapping("/api")
public class ListingMailResource {

    private final Logger log = LoggerFactory.getLogger(ListingMailResource.class);

    private static final String ENTITY_NAME = "listingMail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListingMailService listingMailService;

    private final ListingMailRepository listingMailRepository;

    public ListingMailResource(ListingMailService listingMailService, ListingMailRepository listingMailRepository) {
        this.listingMailService = listingMailService;
        this.listingMailRepository = listingMailRepository;
    }

    /**
     * {@code POST  /listing-mails} : Create a new listingMail.
     *
     * @param listingMailDTO the listingMailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listingMailDTO, or with status {@code 400 (Bad Request)} if the listingMail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/listing-mails")
    public ResponseEntity<ListingMailDTO> createListingMail(@RequestBody ListingMailDTO listingMailDTO) throws URISyntaxException {
        log.debug("REST request to save ListingMail : {}", listingMailDTO);
        if (listingMailDTO.getId() != null) {
            throw new BadRequestAlertException("A new listingMail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListingMailDTO result = listingMailService.save(listingMailDTO);
        return ResponseEntity
            .created(new URI("/api/listing-mails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /listing-mails/:id} : Updates an existing listingMail.
     *
     * @param id the id of the listingMailDTO to save.
     * @param listingMailDTO the listingMailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listingMailDTO,
     * or with status {@code 400 (Bad Request)} if the listingMailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listingMailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/listing-mails/{id}")
    public ResponseEntity<ListingMailDTO> updateListingMail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListingMailDTO listingMailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ListingMail : {}, {}", id, listingMailDTO);
        if (listingMailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listingMailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listingMailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListingMailDTO result = listingMailService.save(listingMailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listingMailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /listing-mails/:id} : Partial updates given fields of an existing listingMail, field will ignore if it is null
     *
     * @param id the id of the listingMailDTO to save.
     * @param listingMailDTO the listingMailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listingMailDTO,
     * or with status {@code 400 (Bad Request)} if the listingMailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the listingMailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the listingMailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/listing-mails/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListingMailDTO> partialUpdateListingMail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListingMailDTO listingMailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListingMail partially : {}, {}", id, listingMailDTO);
        if (listingMailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listingMailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listingMailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListingMailDTO> result = listingMailService.partialUpdate(listingMailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listingMailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /listing-mails} : get all the listingMails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listingMails in body.
     */
    @GetMapping("/listing-mails")
    public ResponseEntity<List<ListingMailDTO>> getAllListingMails(Pageable pageable) {
        log.debug("REST request to get a page of ListingMails");
        Page<ListingMailDTO> page = listingMailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /listing-mails/:id} : get the "id" listingMail.
     *
     * @param id the id of the listingMailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listingMailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/listing-mails/{id}")
    public ResponseEntity<ListingMailDTO> getListingMail(@PathVariable Long id) {
        log.debug("REST request to get ListingMail : {}", id);
        Optional<ListingMailDTO> listingMailDTO = listingMailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listingMailDTO);
    }

    /**
     * {@code DELETE  /listing-mails/:id} : delete the "id" listingMail.
     *
     * @param id the id of the listingMailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/listing-mails/{id}")
    public ResponseEntity<Void> deleteListingMail(@PathVariable Long id) {
        log.debug("REST request to delete ListingMail : {}", id);
        listingMailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
