package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Statut;
import fr.delpharm.esacp.repository.StatutRepository;
import fr.delpharm.esacp.service.dto.StatutDTO;
import fr.delpharm.esacp.service.mapper.StatutMapper;
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
 * Integration tests for the {@link StatutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatutResourceIT {

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/statuts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private StatutMapper statutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatutMockMvc;

    private Statut statut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statut createEntity(EntityManager em) {
        Statut statut = new Statut().statut(DEFAULT_STATUT);
        return statut;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statut createUpdatedEntity(EntityManager em) {
        Statut statut = new Statut().statut(UPDATED_STATUT);
        return statut;
    }

    @BeforeEach
    public void initTest() {
        statut = createEntity(em);
    }

    @Test
    @Transactional
    void createStatut() throws Exception {
        int databaseSizeBeforeCreate = statutRepository.findAll().size();
        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);
        restStatutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statutDTO)))
            .andExpect(status().isCreated());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeCreate + 1);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    void createStatutWithExistingId() throws Exception {
        // Create the Statut with an existing ID
        statut.setId(1L);
        StatutDTO statutDTO = statutMapper.toDto(statut);

        int databaseSizeBeforeCreate = statutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatuts() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        // Get all the statutList
        restStatutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statut.getId().intValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }

    @Test
    @Transactional
    void getStatut() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        // Get the statut
        restStatutMockMvc
            .perform(get(ENTITY_API_URL_ID, statut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statut.getId().intValue()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }

    @Test
    @Transactional
    void getNonExistingStatut() throws Exception {
        // Get the statut
        restStatutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatut() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // Update the statut
        Statut updatedStatut = statutRepository.findById(statut.getId()).get();
        // Disconnect from session so that the updates on updatedStatut are not directly saved in db
        em.detach(updatedStatut);
        updatedStatut.statut(UPDATED_STATUT);
        StatutDTO statutDTO = statutMapper.toDto(updatedStatut);

        restStatutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statutDTO))
            )
            .andExpect(status().isOk());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void putNonExistingStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();
        statut.setId(count.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();
        statut.setId(count.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();
        statut.setId(count.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statutDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatutWithPatch() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // Update the statut using partial update
        Statut partialUpdatedStatut = new Statut();
        partialUpdatedStatut.setId(statut.getId());

        partialUpdatedStatut.statut(UPDATED_STATUT);

        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatut.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatut))
            )
            .andExpect(status().isOk());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void fullUpdateStatutWithPatch() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        int databaseSizeBeforeUpdate = statutRepository.findAll().size();

        // Update the statut using partial update
        Statut partialUpdatedStatut = new Statut();
        partialUpdatedStatut.setId(statut.getId());

        partialUpdatedStatut.statut(UPDATED_STATUT);

        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatut.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatut))
            )
            .andExpect(status().isOk());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
        Statut testStatut = statutList.get(statutList.size() - 1);
        assertThat(testStatut.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    void patchNonExistingStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();
        statut.setId(count.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();
        statut.setId(count.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatut() throws Exception {
        int databaseSizeBeforeUpdate = statutRepository.findAll().size();
        statut.setId(count.incrementAndGet());

        // Create the Statut
        StatutDTO statutDTO = statutMapper.toDto(statut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatutMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(statutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statut in the database
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatut() throws Exception {
        // Initialize the database
        statutRepository.saveAndFlush(statut);

        int databaseSizeBeforeDelete = statutRepository.findAll().size();

        // Delete the statut
        restStatutMockMvc
            .perform(delete(ENTITY_API_URL_ID, statut.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Statut> statutList = statutRepository.findAll();
        assertThat(statutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
