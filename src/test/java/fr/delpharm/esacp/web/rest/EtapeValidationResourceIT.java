package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.EtapeValidation;
import fr.delpharm.esacp.repository.EtapeValidationRepository;
import fr.delpharm.esacp.service.dto.EtapeValidationDTO;
import fr.delpharm.esacp.service.mapper.EtapeValidationMapper;
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
 * Integration tests for the {@link EtapeValidationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtapeValidationResourceIT {

    private static final String DEFAULT_ETAPE_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_ETAPE_VALIDATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etape-validations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtapeValidationRepository etapeValidationRepository;

    @Autowired
    private EtapeValidationMapper etapeValidationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtapeValidationMockMvc;

    private EtapeValidation etapeValidation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapeValidation createEntity(EntityManager em) {
        EtapeValidation etapeValidation = new EtapeValidation().etapeValidation(DEFAULT_ETAPE_VALIDATION);
        return etapeValidation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapeValidation createUpdatedEntity(EntityManager em) {
        EtapeValidation etapeValidation = new EtapeValidation().etapeValidation(UPDATED_ETAPE_VALIDATION);
        return etapeValidation;
    }

    @BeforeEach
    public void initTest() {
        etapeValidation = createEntity(em);
    }

    @Test
    @Transactional
    void createEtapeValidation() throws Exception {
        int databaseSizeBeforeCreate = etapeValidationRepository.findAll().size();
        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);
        restEtapeValidationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeCreate + 1);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getEtapeValidation()).isEqualTo(DEFAULT_ETAPE_VALIDATION);
    }

    @Test
    @Transactional
    void createEtapeValidationWithExistingId() throws Exception {
        // Create the EtapeValidation with an existing ID
        etapeValidation.setId(1L);
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        int databaseSizeBeforeCreate = etapeValidationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapeValidationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtapeValidations() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        // Get all the etapeValidationList
        restEtapeValidationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapeValidation.getId().intValue())))
            .andExpect(jsonPath("$.[*].etapeValidation").value(hasItem(DEFAULT_ETAPE_VALIDATION)));
    }

    @Test
    @Transactional
    void getEtapeValidation() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        // Get the etapeValidation
        restEtapeValidationMockMvc
            .perform(get(ENTITY_API_URL_ID, etapeValidation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etapeValidation.getId().intValue()))
            .andExpect(jsonPath("$.etapeValidation").value(DEFAULT_ETAPE_VALIDATION));
    }

    @Test
    @Transactional
    void getNonExistingEtapeValidation() throws Exception {
        // Get the etapeValidation
        restEtapeValidationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtapeValidation() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();

        // Update the etapeValidation
        EtapeValidation updatedEtapeValidation = etapeValidationRepository.findById(etapeValidation.getId()).get();
        // Disconnect from session so that the updates on updatedEtapeValidation are not directly saved in db
        em.detach(updatedEtapeValidation);
        updatedEtapeValidation.etapeValidation(UPDATED_ETAPE_VALIDATION);
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(updatedEtapeValidation);

        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etapeValidationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isOk());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getEtapeValidation()).isEqualTo(UPDATED_ETAPE_VALIDATION);
    }

    @Test
    @Transactional
    void putNonExistingEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etapeValidationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtapeValidationWithPatch() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();

        // Update the etapeValidation using partial update
        EtapeValidation partialUpdatedEtapeValidation = new EtapeValidation();
        partialUpdatedEtapeValidation.setId(etapeValidation.getId());

        partialUpdatedEtapeValidation.etapeValidation(UPDATED_ETAPE_VALIDATION);

        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapeValidation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtapeValidation))
            )
            .andExpect(status().isOk());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getEtapeValidation()).isEqualTo(UPDATED_ETAPE_VALIDATION);
    }

    @Test
    @Transactional
    void fullUpdateEtapeValidationWithPatch() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();

        // Update the etapeValidation using partial update
        EtapeValidation partialUpdatedEtapeValidation = new EtapeValidation();
        partialUpdatedEtapeValidation.setId(etapeValidation.getId());

        partialUpdatedEtapeValidation.etapeValidation(UPDATED_ETAPE_VALIDATION);

        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapeValidation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtapeValidation))
            )
            .andExpect(status().isOk());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getEtapeValidation()).isEqualTo(UPDATED_ETAPE_VALIDATION);
    }

    @Test
    @Transactional
    void patchNonExistingEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etapeValidationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // Create the EtapeValidation
        EtapeValidationDTO etapeValidationDTO = etapeValidationMapper.toDto(etapeValidation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtapeValidation() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeDelete = etapeValidationRepository.findAll().size();

        // Delete the etapeValidation
        restEtapeValidationMockMvc
            .perform(delete(ENTITY_API_URL_ID, etapeValidation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
