package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Repartition;
import fr.delpharm.esacp.repository.RepartitionRepository;
import fr.delpharm.esacp.service.dto.RepartitionDTO;
import fr.delpharm.esacp.service.mapper.RepartitionMapper;
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
 * Integration tests for the {@link RepartitionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RepartitionResourceIT {

    private static final String DEFAULT_REPARTITION = "AAAAAAAAAA";
    private static final String UPDATED_REPARTITION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/repartitions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RepartitionRepository repartitionRepository;

    @Autowired
    private RepartitionMapper repartitionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepartitionMockMvc;

    private Repartition repartition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repartition createEntity(EntityManager em) {
        Repartition repartition = new Repartition().repartition(DEFAULT_REPARTITION);
        return repartition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repartition createUpdatedEntity(EntityManager em) {
        Repartition repartition = new Repartition().repartition(UPDATED_REPARTITION);
        return repartition;
    }

    @BeforeEach
    public void initTest() {
        repartition = createEntity(em);
    }

    @Test
    @Transactional
    void createRepartition() throws Exception {
        int databaseSizeBeforeCreate = repartitionRepository.findAll().size();
        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);
        restRepartitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeCreate + 1);
        Repartition testRepartition = repartitionList.get(repartitionList.size() - 1);
        assertThat(testRepartition.getRepartition()).isEqualTo(DEFAULT_REPARTITION);
    }

    @Test
    @Transactional
    void createRepartitionWithExistingId() throws Exception {
        // Create the Repartition with an existing ID
        repartition.setId(1L);
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        int databaseSizeBeforeCreate = repartitionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepartitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRepartitions() throws Exception {
        // Initialize the database
        repartitionRepository.saveAndFlush(repartition);

        // Get all the repartitionList
        restRepartitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repartition.getId().intValue())))
            .andExpect(jsonPath("$.[*].repartition").value(hasItem(DEFAULT_REPARTITION)));
    }

    @Test
    @Transactional
    void getRepartition() throws Exception {
        // Initialize the database
        repartitionRepository.saveAndFlush(repartition);

        // Get the repartition
        restRepartitionMockMvc
            .perform(get(ENTITY_API_URL_ID, repartition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(repartition.getId().intValue()))
            .andExpect(jsonPath("$.repartition").value(DEFAULT_REPARTITION));
    }

    @Test
    @Transactional
    void getNonExistingRepartition() throws Exception {
        // Get the repartition
        restRepartitionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRepartition() throws Exception {
        // Initialize the database
        repartitionRepository.saveAndFlush(repartition);

        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();

        // Update the repartition
        Repartition updatedRepartition = repartitionRepository.findById(repartition.getId()).get();
        // Disconnect from session so that the updates on updatedRepartition are not directly saved in db
        em.detach(updatedRepartition);
        updatedRepartition.repartition(UPDATED_REPARTITION);
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(updatedRepartition);

        restRepartitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, repartitionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
        Repartition testRepartition = repartitionList.get(repartitionList.size() - 1);
        assertThat(testRepartition.getRepartition()).isEqualTo(UPDATED_REPARTITION);
    }

    @Test
    @Transactional
    void putNonExistingRepartition() throws Exception {
        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();
        repartition.setId(count.incrementAndGet());

        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepartitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, repartitionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRepartition() throws Exception {
        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();
        repartition.setId(count.incrementAndGet());

        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRepartition() throws Exception {
        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();
        repartition.setId(count.incrementAndGet());

        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartitionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(repartitionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRepartitionWithPatch() throws Exception {
        // Initialize the database
        repartitionRepository.saveAndFlush(repartition);

        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();

        // Update the repartition using partial update
        Repartition partialUpdatedRepartition = new Repartition();
        partialUpdatedRepartition.setId(repartition.getId());

        partialUpdatedRepartition.repartition(UPDATED_REPARTITION);

        restRepartitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepartition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepartition))
            )
            .andExpect(status().isOk());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
        Repartition testRepartition = repartitionList.get(repartitionList.size() - 1);
        assertThat(testRepartition.getRepartition()).isEqualTo(UPDATED_REPARTITION);
    }

    @Test
    @Transactional
    void fullUpdateRepartitionWithPatch() throws Exception {
        // Initialize the database
        repartitionRepository.saveAndFlush(repartition);

        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();

        // Update the repartition using partial update
        Repartition partialUpdatedRepartition = new Repartition();
        partialUpdatedRepartition.setId(repartition.getId());

        partialUpdatedRepartition.repartition(UPDATED_REPARTITION);

        restRepartitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepartition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepartition))
            )
            .andExpect(status().isOk());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
        Repartition testRepartition = repartitionList.get(repartitionList.size() - 1);
        assertThat(testRepartition.getRepartition()).isEqualTo(UPDATED_REPARTITION);
    }

    @Test
    @Transactional
    void patchNonExistingRepartition() throws Exception {
        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();
        repartition.setId(count.incrementAndGet());

        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepartitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, repartitionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRepartition() throws Exception {
        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();
        repartition.setId(count.incrementAndGet());

        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRepartition() throws Exception {
        int databaseSizeBeforeUpdate = repartitionRepository.findAll().size();
        repartition.setId(count.incrementAndGet());

        // Create the Repartition
        RepartitionDTO repartitionDTO = repartitionMapper.toDto(repartition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartitionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(repartitionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Repartition in the database
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRepartition() throws Exception {
        // Initialize the database
        repartitionRepository.saveAndFlush(repartition);

        int databaseSizeBeforeDelete = repartitionRepository.findAll().size();

        // Delete the repartition
        restRepartitionMockMvc
            .perform(delete(ENTITY_API_URL_ID, repartition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Repartition> repartitionList = repartitionRepository.findAll();
        assertThat(repartitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
