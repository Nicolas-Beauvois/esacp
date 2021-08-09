package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.NatureAccident;
import fr.delpharm.esacp.repository.NatureAccidentRepository;
import fr.delpharm.esacp.service.dto.NatureAccidentDTO;
import fr.delpharm.esacp.service.mapper.NatureAccidentMapper;
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
 * Integration tests for the {@link NatureAccidentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatureAccidentResourceIT {

    private static final String DEFAULT_TYPE_NATURE_ACCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NATURE_ACCIDENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nature-accidents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureAccidentRepository natureAccidentRepository;

    @Autowired
    private NatureAccidentMapper natureAccidentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureAccidentMockMvc;

    private NatureAccident natureAccident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAccident createEntity(EntityManager em) {
        NatureAccident natureAccident = new NatureAccident().typeNatureAccident(DEFAULT_TYPE_NATURE_ACCIDENT);
        return natureAccident;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAccident createUpdatedEntity(EntityManager em) {
        NatureAccident natureAccident = new NatureAccident().typeNatureAccident(UPDATED_TYPE_NATURE_ACCIDENT);
        return natureAccident;
    }

    @BeforeEach
    public void initTest() {
        natureAccident = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureAccident() throws Exception {
        int databaseSizeBeforeCreate = natureAccidentRepository.findAll().size();
        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);
        restNatureAccidentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeCreate + 1);
        NatureAccident testNatureAccident = natureAccidentList.get(natureAccidentList.size() - 1);
        assertThat(testNatureAccident.getTypeNatureAccident()).isEqualTo(DEFAULT_TYPE_NATURE_ACCIDENT);
    }

    @Test
    @Transactional
    void createNatureAccidentWithExistingId() throws Exception {
        // Create the NatureAccident with an existing ID
        natureAccident.setId(1L);
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        int databaseSizeBeforeCreate = natureAccidentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureAccidentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureAccidents() throws Exception {
        // Initialize the database
        natureAccidentRepository.saveAndFlush(natureAccident);

        // Get all the natureAccidentList
        restNatureAccidentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureAccident.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeNatureAccident").value(hasItem(DEFAULT_TYPE_NATURE_ACCIDENT)));
    }

    @Test
    @Transactional
    void getNatureAccident() throws Exception {
        // Initialize the database
        natureAccidentRepository.saveAndFlush(natureAccident);

        // Get the natureAccident
        restNatureAccidentMockMvc
            .perform(get(ENTITY_API_URL_ID, natureAccident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureAccident.getId().intValue()))
            .andExpect(jsonPath("$.typeNatureAccident").value(DEFAULT_TYPE_NATURE_ACCIDENT));
    }

    @Test
    @Transactional
    void getNonExistingNatureAccident() throws Exception {
        // Get the natureAccident
        restNatureAccidentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureAccident() throws Exception {
        // Initialize the database
        natureAccidentRepository.saveAndFlush(natureAccident);

        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();

        // Update the natureAccident
        NatureAccident updatedNatureAccident = natureAccidentRepository.findById(natureAccident.getId()).get();
        // Disconnect from session so that the updates on updatedNatureAccident are not directly saved in db
        em.detach(updatedNatureAccident);
        updatedNatureAccident.typeNatureAccident(UPDATED_TYPE_NATURE_ACCIDENT);
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(updatedNatureAccident);

        restNatureAccidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureAccidentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isOk());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
        NatureAccident testNatureAccident = natureAccidentList.get(natureAccidentList.size() - 1);
        assertThat(testNatureAccident.getTypeNatureAccident()).isEqualTo(UPDATED_TYPE_NATURE_ACCIDENT);
    }

    @Test
    @Transactional
    void putNonExistingNatureAccident() throws Exception {
        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();
        natureAccident.setId(count.incrementAndGet());

        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAccidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureAccidentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureAccident() throws Exception {
        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();
        natureAccident.setId(count.incrementAndGet());

        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAccidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureAccident() throws Exception {
        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();
        natureAccident.setId(count.incrementAndGet());

        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAccidentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureAccidentWithPatch() throws Exception {
        // Initialize the database
        natureAccidentRepository.saveAndFlush(natureAccident);

        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();

        // Update the natureAccident using partial update
        NatureAccident partialUpdatedNatureAccident = new NatureAccident();
        partialUpdatedNatureAccident.setId(natureAccident.getId());

        partialUpdatedNatureAccident.typeNatureAccident(UPDATED_TYPE_NATURE_ACCIDENT);

        restNatureAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAccident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAccident))
            )
            .andExpect(status().isOk());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
        NatureAccident testNatureAccident = natureAccidentList.get(natureAccidentList.size() - 1);
        assertThat(testNatureAccident.getTypeNatureAccident()).isEqualTo(UPDATED_TYPE_NATURE_ACCIDENT);
    }

    @Test
    @Transactional
    void fullUpdateNatureAccidentWithPatch() throws Exception {
        // Initialize the database
        natureAccidentRepository.saveAndFlush(natureAccident);

        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();

        // Update the natureAccident using partial update
        NatureAccident partialUpdatedNatureAccident = new NatureAccident();
        partialUpdatedNatureAccident.setId(natureAccident.getId());

        partialUpdatedNatureAccident.typeNatureAccident(UPDATED_TYPE_NATURE_ACCIDENT);

        restNatureAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAccident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAccident))
            )
            .andExpect(status().isOk());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
        NatureAccident testNatureAccident = natureAccidentList.get(natureAccidentList.size() - 1);
        assertThat(testNatureAccident.getTypeNatureAccident()).isEqualTo(UPDATED_TYPE_NATURE_ACCIDENT);
    }

    @Test
    @Transactional
    void patchNonExistingNatureAccident() throws Exception {
        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();
        natureAccident.setId(count.incrementAndGet());

        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureAccidentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureAccident() throws Exception {
        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();
        natureAccident.setId(count.incrementAndGet());

        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureAccident() throws Exception {
        int databaseSizeBeforeUpdate = natureAccidentRepository.findAll().size();
        natureAccident.setId(count.incrementAndGet());

        // Create the NatureAccident
        NatureAccidentDTO natureAccidentDTO = natureAccidentMapper.toDto(natureAccident);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAccidentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAccident in the database
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureAccident() throws Exception {
        // Initialize the database
        natureAccidentRepository.saveAndFlush(natureAccident);

        int databaseSizeBeforeDelete = natureAccidentRepository.findAll().size();

        // Delete the natureAccident
        restNatureAccidentMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureAccident.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureAccident> natureAccidentList = natureAccidentRepository.findAll();
        assertThat(natureAccidentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
