package fr.delpharm.esacp.web.rest;

import fr.delpharm.esacp.domain.Mail;
import fr.delpharm.esacp.repository.MailRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.delpharm.esacp.domain.Mail}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MailResource {

    private final Logger log = LoggerFactory.getLogger(MailResource.class);

    private static final String ENTITY_NAME = "mail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MailRepository mailRepository;

    public MailResource(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    /**
     * {@code POST  /mail} : Create a new mail.
     *
     * @param mail the mail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mail, or with status {@code 400 (Bad Request)} if the mail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mail")
    public ResponseEntity<Mail> createMail(@RequestBody Mail mail) throws URISyntaxException {
        log.debug("REST request to save Mail : {}", mail);
        if (mail.getId() != null) {
            throw new BadRequestAlertException("A new mail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mail result = mailRepository.save(mail);
        return ResponseEntity
            .created(new URI("/api/mail/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mail/:id} : Updates an existing mail.
     *
     * @param id the id of the mail to save.
     * @param mail the mail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mail,
     * or with status {@code 400 (Bad Request)} if the mail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mail/{id}")
    public ResponseEntity<Mail> updateMail(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mail mail)
        throws URISyntaxException {
        log.debug("REST request to update Mail : {}, {}", id, mail);
        if (mail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mail result = mailRepository.save(mail);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mail.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mail/:id} : Partial updates given fields of an existing mail, field will ignore if it is null
     *
     * @param id the id of the mail to save.
     * @param mail the mail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mail,
     * or with status {@code 400 (Bad Request)} if the mail is not valid,
     * or with status {@code 404 (Not Found)} if the mail is not found,
     * or with status {@code 500 (Internal Server Error)} if the mail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mail/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Mail> partialUpdateMail(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mail mail)
        throws URISyntaxException {
        log.debug("REST request to partial update Mail partially : {}, {}", id, mail);
        if (mail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mail.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mail> result = mailRepository
            .findById(mail.getId())
            .map(
                existingMail -> {
                    if (mail.getTypeMail() != null) {
                        existingMail.setTypeMail(mail.getTypeMail());
                    }
                    if (mail.getMsgMail() != null) {
                        existingMail.setMsgMail(mail.getMsgMail());
                    }

                    return existingMail;
                }
            )
            .map(mailRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mail.getId().toString())
        );
    }

    /**
     * {@code GET  /mail} : get all the mail.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mail in body.
     */
    @GetMapping("/mail")
    public ResponseEntity<List<Mail>> getAllMail(Pageable pageable) {
        log.debug("REST request to get a page of Mail");
        Page<Mail> page = mailRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mail/:id} : get the "id" mail.
     *
     * @param id the id of the mail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mail/{id}")
    public ResponseEntity<Mail> getMail(@PathVariable Long id) {
        log.debug("REST request to get Mail : {}", id);
        Optional<Mail> mail = mailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mail);
    }

    /**
     * {@code DELETE  /mail/:id} : delete the "id" mail.
     *
     * @param id the id of the mail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mail/{id}")
    public ResponseEntity<Void> deleteMail(@PathVariable Long id) {
        log.debug("REST request to delete Mail : {}", id);
        mailRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
