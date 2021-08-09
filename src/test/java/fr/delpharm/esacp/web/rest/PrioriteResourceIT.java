package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Priorite;
import fr.delpharm.esacp.repository.PrioriteRepository;
import fr.delpharm.esacp.service.dto.PrioriteDTO;
import fr.delpharm.esacp.service.mapper.PrioriteMapper;
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
 * Integration tests for the {@link PrioriteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrioriteResourceIT {

    private static final String DEFAULT_PRIORITE = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCR_PRIORITE = "AAAAAAAAAA";
    private static final String UPDATED_ACCR_PRIORITE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/priorites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrioriteRepository prioriteRepository;

    @Autowired
    private PrioriteMapper prioriteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrioriteMockMvc;

    private Priorite priorite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Priorite createEntity(EntityManager em) {
        Priorite priorite = new Priorite().priorite(DEFAULT_PRIORITE).accrPriorite(DEFAULT_ACCR_PRIORITE);
        return priorite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Priorite createUpdatedEntity(EntityManager em) {
        Priorite priorite = new Priorite().priorite(UPDATED_PRIORITE).accrPriorite(UPDATED_ACCR_PRIORITE);
        return priorite;
    }

    @BeforeEach
    public void initTest() {
        priorite = createEntity(em);
    }

    @Test
    @Transactional
    void createPriorite() throws Exception {
        int databaseSizeBeforeCreate = prioriteRepository.findAll().size();
        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);
        restPrioriteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prioriteDTO)))
            .andExpect(status().isCreated());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeCreate + 1);
        Priorite testPriorite = prioriteList.get(prioriteList.size() - 1);
        assertThat(testPriorite.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testPriorite.getAccrPriorite()).isEqualTo(DEFAULT_ACCR_PRIORITE);
    }

    @Test
    @Transactional
    void createPrioriteWithExistingId() throws Exception {
        // Create the Priorite with an existing ID
        priorite.setId(1L);
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        int databaseSizeBeforeCreate = prioriteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrioriteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prioriteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPriorites() throws Exception {
        // Initialize the database
        prioriteRepository.saveAndFlush(priorite);

        // Get all the prioriteList
        restPrioriteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priorite.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].accrPriorite").value(hasItem(DEFAULT_ACCR_PRIORITE)));
    }

    @Test
    @Transactional
    void getPriorite() throws Exception {
        // Initialize the database
        prioriteRepository.saveAndFlush(priorite);

        // Get the priorite
        restPrioriteMockMvc
            .perform(get(ENTITY_API_URL_ID, priorite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priorite.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.accrPriorite").value(DEFAULT_ACCR_PRIORITE));
    }

    @Test
    @Transactional
    void getNonExistingPriorite() throws Exception {
        // Get the priorite
        restPrioriteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPriorite() throws Exception {
        // Initialize the database
        prioriteRepository.saveAndFlush(priorite);

        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();

        // Update the priorite
        Priorite updatedPriorite = prioriteRepository.findById(priorite.getId()).get();
        // Disconnect from session so that the updates on updatedPriorite are not directly saved in db
        em.detach(updatedPriorite);
        updatedPriorite.priorite(UPDATED_PRIORITE).accrPriorite(UPDATED_ACCR_PRIORITE);
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(updatedPriorite);

        restPrioriteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prioriteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prioriteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
        Priorite testPriorite = prioriteList.get(prioriteList.size() - 1);
        assertThat(testPriorite.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testPriorite.getAccrPriorite()).isEqualTo(UPDATED_ACCR_PRIORITE);
    }

    @Test
    @Transactional
    void putNonExistingPriorite() throws Exception {
        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();
        priorite.setId(count.incrementAndGet());

        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrioriteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prioriteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prioriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPriorite() throws Exception {
        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();
        priorite.setId(count.incrementAndGet());

        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrioriteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prioriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPriorite() throws Exception {
        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();
        priorite.setId(count.incrementAndGet());

        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrioriteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prioriteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrioriteWithPatch() throws Exception {
        // Initialize the database
        prioriteRepository.saveAndFlush(priorite);

        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();

        // Update the priorite using partial update
        Priorite partialUpdatedPriorite = new Priorite();
        partialUpdatedPriorite.setId(priorite.getId());

        partialUpdatedPriorite.priorite(UPDATED_PRIORITE).accrPriorite(UPDATED_ACCR_PRIORITE);

        restPrioriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriorite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPriorite))
            )
            .andExpect(status().isOk());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
        Priorite testPriorite = prioriteList.get(prioriteList.size() - 1);
        assertThat(testPriorite.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testPriorite.getAccrPriorite()).isEqualTo(UPDATED_ACCR_PRIORITE);
    }

    @Test
    @Transactional
    void fullUpdatePrioriteWithPatch() throws Exception {
        // Initialize the database
        prioriteRepository.saveAndFlush(priorite);

        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();

        // Update the priorite using partial update
        Priorite partialUpdatedPriorite = new Priorite();
        partialUpdatedPriorite.setId(priorite.getId());

        partialUpdatedPriorite.priorite(UPDATED_PRIORITE).accrPriorite(UPDATED_ACCR_PRIORITE);

        restPrioriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPriorite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPriorite))
            )
            .andExpect(status().isOk());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
        Priorite testPriorite = prioriteList.get(prioriteList.size() - 1);
        assertThat(testPriorite.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testPriorite.getAccrPriorite()).isEqualTo(UPDATED_ACCR_PRIORITE);
    }

    @Test
    @Transactional
    void patchNonExistingPriorite() throws Exception {
        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();
        priorite.setId(count.incrementAndGet());

        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrioriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prioriteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prioriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPriorite() throws Exception {
        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();
        priorite.setId(count.incrementAndGet());

        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrioriteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prioriteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPriorite() throws Exception {
        int databaseSizeBeforeUpdate = prioriteRepository.findAll().size();
        priorite.setId(count.incrementAndGet());

        // Create the Priorite
        PrioriteDTO prioriteDTO = prioriteMapper.toDto(priorite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrioriteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prioriteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Priorite in the database
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePriorite() throws Exception {
        // Initialize the database
        prioriteRepository.saveAndFlush(priorite);

        int databaseSizeBeforeDelete = prioriteRepository.findAll().size();

        // Delete the priorite
        restPrioriteMockMvc
            .perform(delete(ENTITY_API_URL_ID, priorite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Priorite> prioriteList = prioriteRepository.findAll();
        assertThat(prioriteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
