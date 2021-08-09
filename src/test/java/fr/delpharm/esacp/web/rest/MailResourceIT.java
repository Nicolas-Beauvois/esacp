package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Mail;
import fr.delpharm.esacp.repository.MailRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MailResourceIT {

    private static final String DEFAULT_TYPE_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MSG_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MSG_MAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mail";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMailMockMvc;

    private Mail mail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mail createEntity(EntityManager em) {
        Mail mail = new Mail().typeMail(DEFAULT_TYPE_MAIL).msgMail(DEFAULT_MSG_MAIL);
        return mail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mail createUpdatedEntity(EntityManager em) {
        Mail mail = new Mail().typeMail(UPDATED_TYPE_MAIL).msgMail(UPDATED_MSG_MAIL);
        return mail;
    }

    @BeforeEach
    public void initTest() {
        mail = createEntity(em);
    }

    @Test
    @Transactional
    void createMail() throws Exception {
        int databaseSizeBeforeCreate = mailRepository.findAll().size();
        // Create the Mail
        restMailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mail)))
            .andExpect(status().isCreated());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeCreate + 1);
        Mail testMail = mailList.get(mailList.size() - 1);
        assertThat(testMail.getTypeMail()).isEqualTo(DEFAULT_TYPE_MAIL);
        assertThat(testMail.getMsgMail()).isEqualTo(DEFAULT_MSG_MAIL);
    }

    @Test
    @Transactional
    void createMailWithExistingId() throws Exception {
        // Create the Mail with an existing ID
        mail.setId(1L);

        int databaseSizeBeforeCreate = mailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mail)))
            .andExpect(status().isBadRequest());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMail() throws Exception {
        // Initialize the database
        mailRepository.saveAndFlush(mail);

        // Get all the mailList
        restMailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mail.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeMail").value(hasItem(DEFAULT_TYPE_MAIL)))
            .andExpect(jsonPath("$.[*].msgMail").value(hasItem(DEFAULT_MSG_MAIL)));
    }

    @Test
    @Transactional
    void getMail() throws Exception {
        // Initialize the database
        mailRepository.saveAndFlush(mail);

        // Get the mail
        restMailMockMvc
            .perform(get(ENTITY_API_URL_ID, mail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mail.getId().intValue()))
            .andExpect(jsonPath("$.typeMail").value(DEFAULT_TYPE_MAIL))
            .andExpect(jsonPath("$.msgMail").value(DEFAULT_MSG_MAIL));
    }

    @Test
    @Transactional
    void getNonExistingMail() throws Exception {
        // Get the mail
        restMailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMail() throws Exception {
        // Initialize the database
        mailRepository.saveAndFlush(mail);

        int databaseSizeBeforeUpdate = mailRepository.findAll().size();

        // Update the mail
        Mail updatedMail = mailRepository.findById(mail.getId()).get();
        // Disconnect from session so that the updates on updatedMail are not directly saved in db
        em.detach(updatedMail);
        updatedMail.typeMail(UPDATED_TYPE_MAIL).msgMail(UPDATED_MSG_MAIL);

        restMailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMail))
            )
            .andExpect(status().isOk());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
        Mail testMail = mailList.get(mailList.size() - 1);
        assertThat(testMail.getTypeMail()).isEqualTo(UPDATED_TYPE_MAIL);
        assertThat(testMail.getMsgMail()).isEqualTo(UPDATED_MSG_MAIL);
    }

    @Test
    @Transactional
    void putNonExistingMail() throws Exception {
        int databaseSizeBeforeUpdate = mailRepository.findAll().size();
        mail.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mail.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMail() throws Exception {
        int databaseSizeBeforeUpdate = mailRepository.findAll().size();
        mail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMail() throws Exception {
        int databaseSizeBeforeUpdate = mailRepository.findAll().size();
        mail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mail)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMailWithPatch() throws Exception {
        // Initialize the database
        mailRepository.saveAndFlush(mail);

        int databaseSizeBeforeUpdate = mailRepository.findAll().size();

        // Update the mail using partial update
        Mail partialUpdatedMail = new Mail();
        partialUpdatedMail.setId(mail.getId());

        partialUpdatedMail.typeMail(UPDATED_TYPE_MAIL);

        restMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMail))
            )
            .andExpect(status().isOk());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
        Mail testMail = mailList.get(mailList.size() - 1);
        assertThat(testMail.getTypeMail()).isEqualTo(UPDATED_TYPE_MAIL);
        assertThat(testMail.getMsgMail()).isEqualTo(DEFAULT_MSG_MAIL);
    }

    @Test
    @Transactional
    void fullUpdateMailWithPatch() throws Exception {
        // Initialize the database
        mailRepository.saveAndFlush(mail);

        int databaseSizeBeforeUpdate = mailRepository.findAll().size();

        // Update the mail using partial update
        Mail partialUpdatedMail = new Mail();
        partialUpdatedMail.setId(mail.getId());

        partialUpdatedMail.typeMail(UPDATED_TYPE_MAIL).msgMail(UPDATED_MSG_MAIL);

        restMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMail))
            )
            .andExpect(status().isOk());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
        Mail testMail = mailList.get(mailList.size() - 1);
        assertThat(testMail.getTypeMail()).isEqualTo(UPDATED_TYPE_MAIL);
        assertThat(testMail.getMsgMail()).isEqualTo(UPDATED_MSG_MAIL);
    }

    @Test
    @Transactional
    void patchNonExistingMail() throws Exception {
        int databaseSizeBeforeUpdate = mailRepository.findAll().size();
        mail.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMail() throws Exception {
        int databaseSizeBeforeUpdate = mailRepository.findAll().size();
        mail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mail))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMail() throws Exception {
        int databaseSizeBeforeUpdate = mailRepository.findAll().size();
        mail.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mail)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mail in the database
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMail() throws Exception {
        // Initialize the database
        mailRepository.saveAndFlush(mail);

        int databaseSizeBeforeDelete = mailRepository.findAll().size();

        // Delete the mail
        restMailMockMvc
            .perform(delete(ENTITY_API_URL_ID, mail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mail> mailList = mailRepository.findAll();
        assertThat(mailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
