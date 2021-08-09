package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Criticite;
import fr.delpharm.esacp.repository.CriticiteRepository;
import fr.delpharm.esacp.service.dto.CriticiteDTO;
import fr.delpharm.esacp.service.mapper.CriticiteMapper;
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
 * Integration tests for the {@link CriticiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CriticiteResourceIT {

    private static final String DEFAULT_CRITICITE = "AAAAAAAAAA";
    private static final String UPDATED_CRITICITE = "BBBBBBBBBB";

    private static final String DEFAULT_CRITICITE_ACRONYME = "AAAAAAAAAA";
    private static final String UPDATED_CRITICITE_ACRONYME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/criticites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CriticiteRepository criticiteRepository;

    @Autowired
    private CriticiteMapper criticiteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCriticiteMockMvc;

    private Criticite criticite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Criticite createEntity(EntityManager em) {
        Criticite criticite = new Criticite().criticite(DEFAULT_CRITICITE).criticiteAcronyme(DEFAULT_CRITICITE_ACRONYME);
        return criticite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Criticite createUpdatedEntity(EntityManager em) {
        Criticite criticite = new Criticite().criticite(UPDATED_CRITICITE).criticiteAcronyme(UPDATED_CRITICITE_ACRONYME);
        return criticite;
    }

    @BeforeEach
    public void initTest() {
        criticite = createEntity(em);
    }

    @Test
    @Transactional
    void createCriticite() throws Exception {
        int databaseSizeBeforeCreate = criticiteRepository.findAll().size();
        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);
        restCriticiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(criticiteDTO)))
            .andExpect(status().isCreated());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeCreate + 1);
        Criticite testCriticite = criticiteList.get(criticiteList.size() - 1);
        assertThat(testCriticite.getCriticite()).isEqualTo(DEFAULT_CRITICITE);
        assertThat(testCriticite.getCriticiteAcronyme()).isEqualTo(DEFAULT_CRITICITE_ACRONYME);
    }

    @Test
    @Transactional
    void createCriticiteWithExistingId() throws Exception {
        // Create the Criticite with an existing ID
        criticite.setId(1L);
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        int databaseSizeBeforeCreate = criticiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCriticiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(criticiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCriticites() throws Exception {
        // Initialize the database
        criticiteRepository.saveAndFlush(criticite);

        // Get all the criticiteList
        restCriticiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(criticite.getId().intValue())))
            .andExpect(jsonPath("$.[*].criticite").value(hasItem(DEFAULT_CRITICITE)))
            .andExpect(jsonPath("$.[*].criticiteAcronyme").value(hasItem(DEFAULT_CRITICITE_ACRONYME)));
    }

    @Test
    @Transactional
    void getCriticite() throws Exception {
        // Initialize the database
        criticiteRepository.saveAndFlush(criticite);

        // Get the criticite
        restCriticiteMockMvc
            .perform(get(ENTITY_API_URL_ID, criticite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(criticite.getId().intValue()))
            .andExpect(jsonPath("$.criticite").value(DEFAULT_CRITICITE))
            .andExpect(jsonPath("$.criticiteAcronyme").value(DEFAULT_CRITICITE_ACRONYME));
    }

    @Test
    @Transactional
    void getNonExistingCriticite() throws Exception {
        // Get the criticite
        restCriticiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCriticite() throws Exception {
        // Initialize the database
        criticiteRepository.saveAndFlush(criticite);

        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();

        // Update the criticite
        Criticite updatedCriticite = criticiteRepository.findById(criticite.getId()).get();
        // Disconnect from session so that the updates on updatedCriticite are not directly saved in db
        em.detach(updatedCriticite);
        updatedCriticite.criticite(UPDATED_CRITICITE).criticiteAcronyme(UPDATED_CRITICITE_ACRONYME);
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(updatedCriticite);

        restCriticiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, criticiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(criticiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
        Criticite testCriticite = criticiteList.get(criticiteList.size() - 1);
        assertThat(testCriticite.getCriticite()).isEqualTo(UPDATED_CRITICITE);
        assertThat(testCriticite.getCriticiteAcronyme()).isEqualTo(UPDATED_CRITICITE_ACRONYME);
    }

    @Test
    @Transactional
    void putNonExistingCriticite() throws Exception {
        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();
        criticite.setId(count.incrementAndGet());

        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriticiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, criticiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(criticiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCriticite() throws Exception {
        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();
        criticite.setId(count.incrementAndGet());

        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriticiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(criticiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCriticite() throws Exception {
        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();
        criticite.setId(count.incrementAndGet());

        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriticiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(criticiteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCriticiteWithPatch() throws Exception {
        // Initialize the database
        criticiteRepository.saveAndFlush(criticite);

        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();

        // Update the criticite using partial update
        Criticite partialUpdatedCriticite = new Criticite();
        partialUpdatedCriticite.setId(criticite.getId());

        partialUpdatedCriticite.criticite(UPDATED_CRITICITE).criticiteAcronyme(UPDATED_CRITICITE_ACRONYME);

        restCriticiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCriticite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCriticite))
            )
            .andExpect(status().isOk());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
        Criticite testCriticite = criticiteList.get(criticiteList.size() - 1);
        assertThat(testCriticite.getCriticite()).isEqualTo(UPDATED_CRITICITE);
        assertThat(testCriticite.getCriticiteAcronyme()).isEqualTo(UPDATED_CRITICITE_ACRONYME);
    }

    @Test
    @Transactional
    void fullUpdateCriticiteWithPatch() throws Exception {
        // Initialize the database
        criticiteRepository.saveAndFlush(criticite);

        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();

        // Update the criticite using partial update
        Criticite partialUpdatedCriticite = new Criticite();
        partialUpdatedCriticite.setId(criticite.getId());

        partialUpdatedCriticite.criticite(UPDATED_CRITICITE).criticiteAcronyme(UPDATED_CRITICITE_ACRONYME);

        restCriticiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCriticite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCriticite))
            )
            .andExpect(status().isOk());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
        Criticite testCriticite = criticiteList.get(criticiteList.size() - 1);
        assertThat(testCriticite.getCriticite()).isEqualTo(UPDATED_CRITICITE);
        assertThat(testCriticite.getCriticiteAcronyme()).isEqualTo(UPDATED_CRITICITE_ACRONYME);
    }

    @Test
    @Transactional
    void patchNonExistingCriticite() throws Exception {
        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();
        criticite.setId(count.incrementAndGet());

        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriticiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, criticiteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(criticiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCriticite() throws Exception {
        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();
        criticite.setId(count.incrementAndGet());

        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriticiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(criticiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCriticite() throws Exception {
        int databaseSizeBeforeUpdate = criticiteRepository.findAll().size();
        criticite.setId(count.incrementAndGet());

        // Create the Criticite
        CriticiteDTO criticiteDTO = criticiteMapper.toDto(criticite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriticiteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(criticiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Criticite in the database
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCriticite() throws Exception {
        // Initialize the database
        criticiteRepository.saveAndFlush(criticite);

        int databaseSizeBeforeDelete = criticiteRepository.findAll().size();

        // Delete the criticite
        restCriticiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, criticite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Criticite> criticiteList = criticiteRepository.findAll();
        assertThat(criticiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
