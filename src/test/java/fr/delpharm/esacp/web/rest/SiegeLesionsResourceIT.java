package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.SiegeLesions;
import fr.delpharm.esacp.repository.SiegeLesionsRepository;
import fr.delpharm.esacp.service.dto.SiegeLesionsDTO;
import fr.delpharm.esacp.service.mapper.SiegeLesionsMapper;
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
 * Integration tests for the {@link SiegeLesionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiegeLesionsResourceIT {

    private static final String DEFAULT_TYPE_SIEGE_DE_LESIONS = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SIEGE_DE_LESIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/siege-lesions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiegeLesionsRepository siegeLesionsRepository;

    @Autowired
    private SiegeLesionsMapper siegeLesionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiegeLesionsMockMvc;

    private SiegeLesions siegeLesions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiegeLesions createEntity(EntityManager em) {
        SiegeLesions siegeLesions = new SiegeLesions().typeSiegeDeLesions(DEFAULT_TYPE_SIEGE_DE_LESIONS);
        return siegeLesions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiegeLesions createUpdatedEntity(EntityManager em) {
        SiegeLesions siegeLesions = new SiegeLesions().typeSiegeDeLesions(UPDATED_TYPE_SIEGE_DE_LESIONS);
        return siegeLesions;
    }

    @BeforeEach
    public void initTest() {
        siegeLesions = createEntity(em);
    }

    @Test
    @Transactional
    void createSiegeLesions() throws Exception {
        int databaseSizeBeforeCreate = siegeLesionsRepository.findAll().size();
        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);
        restSiegeLesionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeCreate + 1);
        SiegeLesions testSiegeLesions = siegeLesionsList.get(siegeLesionsList.size() - 1);
        assertThat(testSiegeLesions.getTypeSiegeDeLesions()).isEqualTo(DEFAULT_TYPE_SIEGE_DE_LESIONS);
    }

    @Test
    @Transactional
    void createSiegeLesionsWithExistingId() throws Exception {
        // Create the SiegeLesions with an existing ID
        siegeLesions.setId(1L);
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        int databaseSizeBeforeCreate = siegeLesionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiegeLesionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSiegeLesions() throws Exception {
        // Initialize the database
        siegeLesionsRepository.saveAndFlush(siegeLesions);

        // Get all the siegeLesionsList
        restSiegeLesionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siegeLesions.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeSiegeDeLesions").value(hasItem(DEFAULT_TYPE_SIEGE_DE_LESIONS)));
    }

    @Test
    @Transactional
    void getSiegeLesions() throws Exception {
        // Initialize the database
        siegeLesionsRepository.saveAndFlush(siegeLesions);

        // Get the siegeLesions
        restSiegeLesionsMockMvc
            .perform(get(ENTITY_API_URL_ID, siegeLesions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siegeLesions.getId().intValue()))
            .andExpect(jsonPath("$.typeSiegeDeLesions").value(DEFAULT_TYPE_SIEGE_DE_LESIONS));
    }

    @Test
    @Transactional
    void getNonExistingSiegeLesions() throws Exception {
        // Get the siegeLesions
        restSiegeLesionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiegeLesions() throws Exception {
        // Initialize the database
        siegeLesionsRepository.saveAndFlush(siegeLesions);

        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();

        // Update the siegeLesions
        SiegeLesions updatedSiegeLesions = siegeLesionsRepository.findById(siegeLesions.getId()).get();
        // Disconnect from session so that the updates on updatedSiegeLesions are not directly saved in db
        em.detach(updatedSiegeLesions);
        updatedSiegeLesions.typeSiegeDeLesions(UPDATED_TYPE_SIEGE_DE_LESIONS);
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(updatedSiegeLesions);

        restSiegeLesionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siegeLesionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
        SiegeLesions testSiegeLesions = siegeLesionsList.get(siegeLesionsList.size() - 1);
        assertThat(testSiegeLesions.getTypeSiegeDeLesions()).isEqualTo(UPDATED_TYPE_SIEGE_DE_LESIONS);
    }

    @Test
    @Transactional
    void putNonExistingSiegeLesions() throws Exception {
        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();
        siegeLesions.setId(count.incrementAndGet());

        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiegeLesionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siegeLesionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiegeLesions() throws Exception {
        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();
        siegeLesions.setId(count.incrementAndGet());

        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiegeLesionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiegeLesions() throws Exception {
        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();
        siegeLesions.setId(count.incrementAndGet());

        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiegeLesionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiegeLesionsWithPatch() throws Exception {
        // Initialize the database
        siegeLesionsRepository.saveAndFlush(siegeLesions);

        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();

        // Update the siegeLesions using partial update
        SiegeLesions partialUpdatedSiegeLesions = new SiegeLesions();
        partialUpdatedSiegeLesions.setId(siegeLesions.getId());

        partialUpdatedSiegeLesions.typeSiegeDeLesions(UPDATED_TYPE_SIEGE_DE_LESIONS);

        restSiegeLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiegeLesions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiegeLesions))
            )
            .andExpect(status().isOk());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
        SiegeLesions testSiegeLesions = siegeLesionsList.get(siegeLesionsList.size() - 1);
        assertThat(testSiegeLesions.getTypeSiegeDeLesions()).isEqualTo(UPDATED_TYPE_SIEGE_DE_LESIONS);
    }

    @Test
    @Transactional
    void fullUpdateSiegeLesionsWithPatch() throws Exception {
        // Initialize the database
        siegeLesionsRepository.saveAndFlush(siegeLesions);

        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();

        // Update the siegeLesions using partial update
        SiegeLesions partialUpdatedSiegeLesions = new SiegeLesions();
        partialUpdatedSiegeLesions.setId(siegeLesions.getId());

        partialUpdatedSiegeLesions.typeSiegeDeLesions(UPDATED_TYPE_SIEGE_DE_LESIONS);

        restSiegeLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiegeLesions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiegeLesions))
            )
            .andExpect(status().isOk());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
        SiegeLesions testSiegeLesions = siegeLesionsList.get(siegeLesionsList.size() - 1);
        assertThat(testSiegeLesions.getTypeSiegeDeLesions()).isEqualTo(UPDATED_TYPE_SIEGE_DE_LESIONS);
    }

    @Test
    @Transactional
    void patchNonExistingSiegeLesions() throws Exception {
        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();
        siegeLesions.setId(count.incrementAndGet());

        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiegeLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siegeLesionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiegeLesions() throws Exception {
        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();
        siegeLesions.setId(count.incrementAndGet());

        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiegeLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiegeLesions() throws Exception {
        int databaseSizeBeforeUpdate = siegeLesionsRepository.findAll().size();
        siegeLesions.setId(count.incrementAndGet());

        // Create the SiegeLesions
        SiegeLesionsDTO siegeLesionsDTO = siegeLesionsMapper.toDto(siegeLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiegeLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siegeLesionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiegeLesions in the database
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiegeLesions() throws Exception {
        // Initialize the database
        siegeLesionsRepository.saveAndFlush(siegeLesions);

        int databaseSizeBeforeDelete = siegeLesionsRepository.findAll().size();

        // Delete the siegeLesions
        restSiegeLesionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, siegeLesions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiegeLesions> siegeLesionsList = siegeLesionsRepository.findAll();
        assertThat(siegeLesionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
