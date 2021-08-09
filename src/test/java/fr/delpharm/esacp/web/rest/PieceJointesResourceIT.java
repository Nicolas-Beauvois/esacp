package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.PieceJointes;
import fr.delpharm.esacp.repository.PieceJointesRepository;
import fr.delpharm.esacp.service.dto.PieceJointesDTO;
import fr.delpharm.esacp.service.mapper.PieceJointesMapper;
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
 * Integration tests for the {@link PieceJointesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PieceJointesResourceIT {

    private static final String DEFAULT_PJ = "AAAAAAAAAA";
    private static final String UPDATED_PJ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/piece-jointes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PieceJointesRepository pieceJointesRepository;

    @Autowired
    private PieceJointesMapper pieceJointesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPieceJointesMockMvc;

    private PieceJointes pieceJointes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PieceJointes createEntity(EntityManager em) {
        PieceJointes pieceJointes = new PieceJointes().pj(DEFAULT_PJ);
        return pieceJointes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PieceJointes createUpdatedEntity(EntityManager em) {
        PieceJointes pieceJointes = new PieceJointes().pj(UPDATED_PJ);
        return pieceJointes;
    }

    @BeforeEach
    public void initTest() {
        pieceJointes = createEntity(em);
    }

    @Test
    @Transactional
    void createPieceJointes() throws Exception {
        int databaseSizeBeforeCreate = pieceJointesRepository.findAll().size();
        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);
        restPieceJointesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeCreate + 1);
        PieceJointes testPieceJointes = pieceJointesList.get(pieceJointesList.size() - 1);
        assertThat(testPieceJointes.getPj()).isEqualTo(DEFAULT_PJ);
    }

    @Test
    @Transactional
    void createPieceJointesWithExistingId() throws Exception {
        // Create the PieceJointes with an existing ID
        pieceJointes.setId(1L);
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        int databaseSizeBeforeCreate = pieceJointesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceJointesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPieceJointes() throws Exception {
        // Initialize the database
        pieceJointesRepository.saveAndFlush(pieceJointes);

        // Get all the pieceJointesList
        restPieceJointesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceJointes.getId().intValue())))
            .andExpect(jsonPath("$.[*].pj").value(hasItem(DEFAULT_PJ)));
    }

    @Test
    @Transactional
    void getPieceJointes() throws Exception {
        // Initialize the database
        pieceJointesRepository.saveAndFlush(pieceJointes);

        // Get the pieceJointes
        restPieceJointesMockMvc
            .perform(get(ENTITY_API_URL_ID, pieceJointes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pieceJointes.getId().intValue()))
            .andExpect(jsonPath("$.pj").value(DEFAULT_PJ));
    }

    @Test
    @Transactional
    void getNonExistingPieceJointes() throws Exception {
        // Get the pieceJointes
        restPieceJointesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPieceJointes() throws Exception {
        // Initialize the database
        pieceJointesRepository.saveAndFlush(pieceJointes);

        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();

        // Update the pieceJointes
        PieceJointes updatedPieceJointes = pieceJointesRepository.findById(pieceJointes.getId()).get();
        // Disconnect from session so that the updates on updatedPieceJointes are not directly saved in db
        em.detach(updatedPieceJointes);
        updatedPieceJointes.pj(UPDATED_PJ);
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(updatedPieceJointes);

        restPieceJointesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pieceJointesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isOk());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
        PieceJointes testPieceJointes = pieceJointesList.get(pieceJointesList.size() - 1);
        assertThat(testPieceJointes.getPj()).isEqualTo(UPDATED_PJ);
    }

    @Test
    @Transactional
    void putNonExistingPieceJointes() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();
        pieceJointes.setId(count.incrementAndGet());

        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPieceJointesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pieceJointesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPieceJointes() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();
        pieceJointes.setId(count.incrementAndGet());

        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPieceJointesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPieceJointes() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();
        pieceJointes.setId(count.incrementAndGet());

        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPieceJointesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePieceJointesWithPatch() throws Exception {
        // Initialize the database
        pieceJointesRepository.saveAndFlush(pieceJointes);

        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();

        // Update the pieceJointes using partial update
        PieceJointes partialUpdatedPieceJointes = new PieceJointes();
        partialUpdatedPieceJointes.setId(pieceJointes.getId());

        partialUpdatedPieceJointes.pj(UPDATED_PJ);

        restPieceJointesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPieceJointes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPieceJointes))
            )
            .andExpect(status().isOk());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
        PieceJointes testPieceJointes = pieceJointesList.get(pieceJointesList.size() - 1);
        assertThat(testPieceJointes.getPj()).isEqualTo(UPDATED_PJ);
    }

    @Test
    @Transactional
    void fullUpdatePieceJointesWithPatch() throws Exception {
        // Initialize the database
        pieceJointesRepository.saveAndFlush(pieceJointes);

        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();

        // Update the pieceJointes using partial update
        PieceJointes partialUpdatedPieceJointes = new PieceJointes();
        partialUpdatedPieceJointes.setId(pieceJointes.getId());

        partialUpdatedPieceJointes.pj(UPDATED_PJ);

        restPieceJointesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPieceJointes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPieceJointes))
            )
            .andExpect(status().isOk());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
        PieceJointes testPieceJointes = pieceJointesList.get(pieceJointesList.size() - 1);
        assertThat(testPieceJointes.getPj()).isEqualTo(UPDATED_PJ);
    }

    @Test
    @Transactional
    void patchNonExistingPieceJointes() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();
        pieceJointes.setId(count.incrementAndGet());

        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPieceJointesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pieceJointesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPieceJointes() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();
        pieceJointes.setId(count.incrementAndGet());

        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPieceJointesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPieceJointes() throws Exception {
        int databaseSizeBeforeUpdate = pieceJointesRepository.findAll().size();
        pieceJointes.setId(count.incrementAndGet());

        // Create the PieceJointes
        PieceJointesDTO pieceJointesDTO = pieceJointesMapper.toDto(pieceJointes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPieceJointesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pieceJointesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PieceJointes in the database
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePieceJointes() throws Exception {
        // Initialize the database
        pieceJointesRepository.saveAndFlush(pieceJointes);

        int databaseSizeBeforeDelete = pieceJointesRepository.findAll().size();

        // Delete the pieceJointes
        restPieceJointesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pieceJointes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PieceJointes> pieceJointesList = pieceJointesRepository.findAll();
        assertThat(pieceJointesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
