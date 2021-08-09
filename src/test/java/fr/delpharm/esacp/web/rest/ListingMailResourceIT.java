package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.ListingMail;
import fr.delpharm.esacp.repository.ListingMailRepository;
import fr.delpharm.esacp.service.dto.ListingMailDTO;
import fr.delpharm.esacp.service.mapper.ListingMailMapper;
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
 * Integration tests for the {@link ListingMailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListingMailResourceIT {

    private static final String DEFAULT_TYPE_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/listing-mails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListingMailRepository listingMailRepository;

    @Autowired
    private ListingMailMapper listingMailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListingMailMockMvc;

    private ListingMail listingMail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListingMail createEntity(EntityManager em) {
        ListingMail listingMail = new ListingMail().typeMessage(DEFAULT_TYPE_MESSAGE).email(DEFAULT_EMAIL);
        return listingMail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListingMail createUpdatedEntity(EntityManager em) {
        ListingMail listingMail = new ListingMail().typeMessage(UPDATED_TYPE_MESSAGE).email(UPDATED_EMAIL);
        return listingMail;
    }

    @BeforeEach
    public void initTest() {
        listingMail = createEntity(em);
    }

    @Test
    @Transactional
    void createListingMail() throws Exception {
        int databaseSizeBeforeCreate = listingMailRepository.findAll().size();
        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);
        restListingMailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeCreate + 1);
        ListingMail testListingMail = listingMailList.get(listingMailList.size() - 1);
        assertThat(testListingMail.getTypeMessage()).isEqualTo(DEFAULT_TYPE_MESSAGE);
        assertThat(testListingMail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createListingMailWithExistingId() throws Exception {
        // Create the ListingMail with an existing ID
        listingMail.setId(1L);
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        int databaseSizeBeforeCreate = listingMailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListingMailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListingMails() throws Exception {
        // Initialize the database
        listingMailRepository.saveAndFlush(listingMail);

        // Get all the listingMailList
        restListingMailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listingMail.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeMessage").value(hasItem(DEFAULT_TYPE_MESSAGE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getListingMail() throws Exception {
        // Initialize the database
        listingMailRepository.saveAndFlush(listingMail);

        // Get the listingMail
        restListingMailMockMvc
            .perform(get(ENTITY_API_URL_ID, listingMail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listingMail.getId().intValue()))
            .andExpect(jsonPath("$.typeMessage").value(DEFAULT_TYPE_MESSAGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingListingMail() throws Exception {
        // Get the listingMail
        restListingMailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListingMail() throws Exception {
        // Initialize the database
        listingMailRepository.saveAndFlush(listingMail);

        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();

        // Update the listingMail
        ListingMail updatedListingMail = listingMailRepository.findById(listingMail.getId()).get();
        // Disconnect from session so that the updates on updatedListingMail are not directly saved in db
        em.detach(updatedListingMail);
        updatedListingMail.typeMessage(UPDATED_TYPE_MESSAGE).email(UPDATED_EMAIL);
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(updatedListingMail);

        restListingMailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listingMailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isOk());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
        ListingMail testListingMail = listingMailList.get(listingMailList.size() - 1);
        assertThat(testListingMail.getTypeMessage()).isEqualTo(UPDATED_TYPE_MESSAGE);
        assertThat(testListingMail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingListingMail() throws Exception {
        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();
        listingMail.setId(count.incrementAndGet());

        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingMailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listingMailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListingMail() throws Exception {
        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();
        listingMail.setId(count.incrementAndGet());

        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListingMail() throws Exception {
        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();
        listingMail.setId(count.incrementAndGet());

        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listingMailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListingMailWithPatch() throws Exception {
        // Initialize the database
        listingMailRepository.saveAndFlush(listingMail);

        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();

        // Update the listingMail using partial update
        ListingMail partialUpdatedListingMail = new ListingMail();
        partialUpdatedListingMail.setId(listingMail.getId());

        partialUpdatedListingMail.typeMessage(UPDATED_TYPE_MESSAGE);

        restListingMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListingMail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListingMail))
            )
            .andExpect(status().isOk());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
        ListingMail testListingMail = listingMailList.get(listingMailList.size() - 1);
        assertThat(testListingMail.getTypeMessage()).isEqualTo(UPDATED_TYPE_MESSAGE);
        assertThat(testListingMail.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateListingMailWithPatch() throws Exception {
        // Initialize the database
        listingMailRepository.saveAndFlush(listingMail);

        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();

        // Update the listingMail using partial update
        ListingMail partialUpdatedListingMail = new ListingMail();
        partialUpdatedListingMail.setId(listingMail.getId());

        partialUpdatedListingMail.typeMessage(UPDATED_TYPE_MESSAGE).email(UPDATED_EMAIL);

        restListingMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListingMail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListingMail))
            )
            .andExpect(status().isOk());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
        ListingMail testListingMail = listingMailList.get(listingMailList.size() - 1);
        assertThat(testListingMail.getTypeMessage()).isEqualTo(UPDATED_TYPE_MESSAGE);
        assertThat(testListingMail.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingListingMail() throws Exception {
        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();
        listingMail.setId(count.incrementAndGet());

        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListingMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listingMailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListingMail() throws Exception {
        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();
        listingMail.setId(count.incrementAndGet());

        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListingMail() throws Exception {
        int databaseSizeBeforeUpdate = listingMailRepository.findAll().size();
        listingMail.setId(count.incrementAndGet());

        // Create the ListingMail
        ListingMailDTO listingMailDTO = listingMailMapper.toDto(listingMail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListingMailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listingMailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListingMail in the database
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListingMail() throws Exception {
        // Initialize the database
        listingMailRepository.saveAndFlush(listingMail);

        int databaseSizeBeforeDelete = listingMailRepository.findAll().size();

        // Delete the listingMail
        restListingMailMockMvc
            .perform(delete(ENTITY_API_URL_ID, listingMail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListingMail> listingMailList = listingMailRepository.findAll();
        assertThat(listingMailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
