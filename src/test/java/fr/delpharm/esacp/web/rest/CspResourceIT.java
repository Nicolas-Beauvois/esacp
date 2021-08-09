package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Csp;
import fr.delpharm.esacp.repository.CspRepository;
import fr.delpharm.esacp.service.dto.CspDTO;
import fr.delpharm.esacp.service.mapper.CspMapper;
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
 * Integration tests for the {@link CspResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CspResourceIT {

    private static final String DEFAULT_CSP = "AAAAAAAAAA";
    private static final String UPDATED_CSP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/csps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CspRepository cspRepository;

    @Autowired
    private CspMapper cspMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCspMockMvc;

    private Csp csp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Csp createEntity(EntityManager em) {
        Csp csp = new Csp().csp(DEFAULT_CSP);
        return csp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Csp createUpdatedEntity(EntityManager em) {
        Csp csp = new Csp().csp(UPDATED_CSP);
        return csp;
    }

    @BeforeEach
    public void initTest() {
        csp = createEntity(em);
    }

    @Test
    @Transactional
    void createCsp() throws Exception {
        int databaseSizeBeforeCreate = cspRepository.findAll().size();
        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);
        restCspMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cspDTO)))
            .andExpect(status().isCreated());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeCreate + 1);
        Csp testCsp = cspList.get(cspList.size() - 1);
        assertThat(testCsp.getCsp()).isEqualTo(DEFAULT_CSP);
    }

    @Test
    @Transactional
    void createCspWithExistingId() throws Exception {
        // Create the Csp with an existing ID
        csp.setId(1L);
        CspDTO cspDTO = cspMapper.toDto(csp);

        int databaseSizeBeforeCreate = cspRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCspMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cspDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCsps() throws Exception {
        // Initialize the database
        cspRepository.saveAndFlush(csp);

        // Get all the cspList
        restCspMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(csp.getId().intValue())))
            .andExpect(jsonPath("$.[*].csp").value(hasItem(DEFAULT_CSP)));
    }

    @Test
    @Transactional
    void getCsp() throws Exception {
        // Initialize the database
        cspRepository.saveAndFlush(csp);

        // Get the csp
        restCspMockMvc
            .perform(get(ENTITY_API_URL_ID, csp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(csp.getId().intValue()))
            .andExpect(jsonPath("$.csp").value(DEFAULT_CSP));
    }

    @Test
    @Transactional
    void getNonExistingCsp() throws Exception {
        // Get the csp
        restCspMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCsp() throws Exception {
        // Initialize the database
        cspRepository.saveAndFlush(csp);

        int databaseSizeBeforeUpdate = cspRepository.findAll().size();

        // Update the csp
        Csp updatedCsp = cspRepository.findById(csp.getId()).get();
        // Disconnect from session so that the updates on updatedCsp are not directly saved in db
        em.detach(updatedCsp);
        updatedCsp.csp(UPDATED_CSP);
        CspDTO cspDTO = cspMapper.toDto(updatedCsp);

        restCspMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cspDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cspDTO))
            )
            .andExpect(status().isOk());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
        Csp testCsp = cspList.get(cspList.size() - 1);
        assertThat(testCsp.getCsp()).isEqualTo(UPDATED_CSP);
    }

    @Test
    @Transactional
    void putNonExistingCsp() throws Exception {
        int databaseSizeBeforeUpdate = cspRepository.findAll().size();
        csp.setId(count.incrementAndGet());

        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCspMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cspDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cspDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCsp() throws Exception {
        int databaseSizeBeforeUpdate = cspRepository.findAll().size();
        csp.setId(count.incrementAndGet());

        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCspMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cspDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCsp() throws Exception {
        int databaseSizeBeforeUpdate = cspRepository.findAll().size();
        csp.setId(count.incrementAndGet());

        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCspMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cspDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCspWithPatch() throws Exception {
        // Initialize the database
        cspRepository.saveAndFlush(csp);

        int databaseSizeBeforeUpdate = cspRepository.findAll().size();

        // Update the csp using partial update
        Csp partialUpdatedCsp = new Csp();
        partialUpdatedCsp.setId(csp.getId());

        restCspMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCsp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCsp))
            )
            .andExpect(status().isOk());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
        Csp testCsp = cspList.get(cspList.size() - 1);
        assertThat(testCsp.getCsp()).isEqualTo(DEFAULT_CSP);
    }

    @Test
    @Transactional
    void fullUpdateCspWithPatch() throws Exception {
        // Initialize the database
        cspRepository.saveAndFlush(csp);

        int databaseSizeBeforeUpdate = cspRepository.findAll().size();

        // Update the csp using partial update
        Csp partialUpdatedCsp = new Csp();
        partialUpdatedCsp.setId(csp.getId());

        partialUpdatedCsp.csp(UPDATED_CSP);

        restCspMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCsp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCsp))
            )
            .andExpect(status().isOk());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
        Csp testCsp = cspList.get(cspList.size() - 1);
        assertThat(testCsp.getCsp()).isEqualTo(UPDATED_CSP);
    }

    @Test
    @Transactional
    void patchNonExistingCsp() throws Exception {
        int databaseSizeBeforeUpdate = cspRepository.findAll().size();
        csp.setId(count.incrementAndGet());

        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCspMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cspDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cspDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCsp() throws Exception {
        int databaseSizeBeforeUpdate = cspRepository.findAll().size();
        csp.setId(count.incrementAndGet());

        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCspMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cspDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCsp() throws Exception {
        int databaseSizeBeforeUpdate = cspRepository.findAll().size();
        csp.setId(count.incrementAndGet());

        // Create the Csp
        CspDTO cspDTO = cspMapper.toDto(csp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCspMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cspDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Csp in the database
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCsp() throws Exception {
        // Initialize the database
        cspRepository.saveAndFlush(csp);

        int databaseSizeBeforeDelete = cspRepository.findAll().size();

        // Delete the csp
        restCspMockMvc.perform(delete(ENTITY_API_URL_ID, csp.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Csp> cspList = cspRepository.findAll();
        assertThat(cspList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
