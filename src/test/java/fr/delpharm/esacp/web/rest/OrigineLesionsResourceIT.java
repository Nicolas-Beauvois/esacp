package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.OrigineLesions;
import fr.delpharm.esacp.repository.OrigineLesionsRepository;
import fr.delpharm.esacp.service.dto.OrigineLesionsDTO;
import fr.delpharm.esacp.service.mapper.OrigineLesionsMapper;
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
 * Integration tests for the {@link OrigineLesionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrigineLesionsResourceIT {

    private static final String DEFAULT_ORIGINE_LESIONS = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE_LESIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/origine-lesions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrigineLesionsRepository origineLesionsRepository;

    @Autowired
    private OrigineLesionsMapper origineLesionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrigineLesionsMockMvc;

    private OrigineLesions origineLesions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigineLesions createEntity(EntityManager em) {
        OrigineLesions origineLesions = new OrigineLesions().origineLesions(DEFAULT_ORIGINE_LESIONS);
        return origineLesions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigineLesions createUpdatedEntity(EntityManager em) {
        OrigineLesions origineLesions = new OrigineLesions().origineLesions(UPDATED_ORIGINE_LESIONS);
        return origineLesions;
    }

    @BeforeEach
    public void initTest() {
        origineLesions = createEntity(em);
    }

    @Test
    @Transactional
    void createOrigineLesions() throws Exception {
        int databaseSizeBeforeCreate = origineLesionsRepository.findAll().size();
        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);
        restOrigineLesionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeCreate + 1);
        OrigineLesions testOrigineLesions = origineLesionsList.get(origineLesionsList.size() - 1);
        assertThat(testOrigineLesions.getOrigineLesions()).isEqualTo(DEFAULT_ORIGINE_LESIONS);
    }

    @Test
    @Transactional
    void createOrigineLesionsWithExistingId() throws Exception {
        // Create the OrigineLesions with an existing ID
        origineLesions.setId(1L);
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        int databaseSizeBeforeCreate = origineLesionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrigineLesionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrigineLesions() throws Exception {
        // Initialize the database
        origineLesionsRepository.saveAndFlush(origineLesions);

        // Get all the origineLesionsList
        restOrigineLesionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origineLesions.getId().intValue())))
            .andExpect(jsonPath("$.[*].origineLesions").value(hasItem(DEFAULT_ORIGINE_LESIONS)));
    }

    @Test
    @Transactional
    void getOrigineLesions() throws Exception {
        // Initialize the database
        origineLesionsRepository.saveAndFlush(origineLesions);

        // Get the origineLesions
        restOrigineLesionsMockMvc
            .perform(get(ENTITY_API_URL_ID, origineLesions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(origineLesions.getId().intValue()))
            .andExpect(jsonPath("$.origineLesions").value(DEFAULT_ORIGINE_LESIONS));
    }

    @Test
    @Transactional
    void getNonExistingOrigineLesions() throws Exception {
        // Get the origineLesions
        restOrigineLesionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrigineLesions() throws Exception {
        // Initialize the database
        origineLesionsRepository.saveAndFlush(origineLesions);

        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();

        // Update the origineLesions
        OrigineLesions updatedOrigineLesions = origineLesionsRepository.findById(origineLesions.getId()).get();
        // Disconnect from session so that the updates on updatedOrigineLesions are not directly saved in db
        em.detach(updatedOrigineLesions);
        updatedOrigineLesions.origineLesions(UPDATED_ORIGINE_LESIONS);
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(updatedOrigineLesions);

        restOrigineLesionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, origineLesionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
        OrigineLesions testOrigineLesions = origineLesionsList.get(origineLesionsList.size() - 1);
        assertThat(testOrigineLesions.getOrigineLesions()).isEqualTo(UPDATED_ORIGINE_LESIONS);
    }

    @Test
    @Transactional
    void putNonExistingOrigineLesions() throws Exception {
        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();
        origineLesions.setId(count.incrementAndGet());

        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigineLesionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, origineLesionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrigineLesions() throws Exception {
        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();
        origineLesions.setId(count.incrementAndGet());

        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrigineLesionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrigineLesions() throws Exception {
        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();
        origineLesions.setId(count.incrementAndGet());

        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrigineLesionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrigineLesionsWithPatch() throws Exception {
        // Initialize the database
        origineLesionsRepository.saveAndFlush(origineLesions);

        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();

        // Update the origineLesions using partial update
        OrigineLesions partialUpdatedOrigineLesions = new OrigineLesions();
        partialUpdatedOrigineLesions.setId(origineLesions.getId());

        restOrigineLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrigineLesions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrigineLesions))
            )
            .andExpect(status().isOk());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
        OrigineLesions testOrigineLesions = origineLesionsList.get(origineLesionsList.size() - 1);
        assertThat(testOrigineLesions.getOrigineLesions()).isEqualTo(DEFAULT_ORIGINE_LESIONS);
    }

    @Test
    @Transactional
    void fullUpdateOrigineLesionsWithPatch() throws Exception {
        // Initialize the database
        origineLesionsRepository.saveAndFlush(origineLesions);

        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();

        // Update the origineLesions using partial update
        OrigineLesions partialUpdatedOrigineLesions = new OrigineLesions();
        partialUpdatedOrigineLesions.setId(origineLesions.getId());

        partialUpdatedOrigineLesions.origineLesions(UPDATED_ORIGINE_LESIONS);

        restOrigineLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrigineLesions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrigineLesions))
            )
            .andExpect(status().isOk());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
        OrigineLesions testOrigineLesions = origineLesionsList.get(origineLesionsList.size() - 1);
        assertThat(testOrigineLesions.getOrigineLesions()).isEqualTo(UPDATED_ORIGINE_LESIONS);
    }

    @Test
    @Transactional
    void patchNonExistingOrigineLesions() throws Exception {
        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();
        origineLesions.setId(count.incrementAndGet());

        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigineLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, origineLesionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrigineLesions() throws Exception {
        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();
        origineLesions.setId(count.incrementAndGet());

        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrigineLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrigineLesions() throws Exception {
        int databaseSizeBeforeUpdate = origineLesionsRepository.findAll().size();
        origineLesions.setId(count.incrementAndGet());

        // Create the OrigineLesions
        OrigineLesionsDTO origineLesionsDTO = origineLesionsMapper.toDto(origineLesions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrigineLesionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(origineLesionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrigineLesions in the database
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrigineLesions() throws Exception {
        // Initialize the database
        origineLesionsRepository.saveAndFlush(origineLesions);

        int databaseSizeBeforeDelete = origineLesionsRepository.findAll().size();

        // Delete the origineLesions
        restOrigineLesionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, origineLesions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrigineLesions> origineLesionsList = origineLesionsRepository.findAll();
        assertThat(origineLesionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
