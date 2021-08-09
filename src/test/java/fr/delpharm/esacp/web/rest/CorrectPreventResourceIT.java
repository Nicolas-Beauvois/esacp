package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.CorrectPrevent;
import fr.delpharm.esacp.repository.CorrectPreventRepository;
import fr.delpharm.esacp.service.dto.CorrectPreventDTO;
import fr.delpharm.esacp.service.mapper.CorrectPreventMapper;
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
 * Integration tests for the {@link CorrectPreventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CorrectPreventResourceIT {

    private static final String DEFAULT_CORRECT_PREVENT = "AAAAAAAAAA";
    private static final String UPDATED_CORRECT_PREVENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/correct-prevents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CorrectPreventRepository correctPreventRepository;

    @Autowired
    private CorrectPreventMapper correctPreventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorrectPreventMockMvc;

    private CorrectPrevent correctPrevent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorrectPrevent createEntity(EntityManager em) {
        CorrectPrevent correctPrevent = new CorrectPrevent().correctPrevent(DEFAULT_CORRECT_PREVENT);
        return correctPrevent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorrectPrevent createUpdatedEntity(EntityManager em) {
        CorrectPrevent correctPrevent = new CorrectPrevent().correctPrevent(UPDATED_CORRECT_PREVENT);
        return correctPrevent;
    }

    @BeforeEach
    public void initTest() {
        correctPrevent = createEntity(em);
    }

    @Test
    @Transactional
    void createCorrectPrevent() throws Exception {
        int databaseSizeBeforeCreate = correctPreventRepository.findAll().size();
        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);
        restCorrectPreventMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeCreate + 1);
        CorrectPrevent testCorrectPrevent = correctPreventList.get(correctPreventList.size() - 1);
        assertThat(testCorrectPrevent.getCorrectPrevent()).isEqualTo(DEFAULT_CORRECT_PREVENT);
    }

    @Test
    @Transactional
    void createCorrectPreventWithExistingId() throws Exception {
        // Create the CorrectPrevent with an existing ID
        correctPrevent.setId(1L);
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        int databaseSizeBeforeCreate = correctPreventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrectPreventMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCorrectPrevents() throws Exception {
        // Initialize the database
        correctPreventRepository.saveAndFlush(correctPrevent);

        // Get all the correctPreventList
        restCorrectPreventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correctPrevent.getId().intValue())))
            .andExpect(jsonPath("$.[*].correctPrevent").value(hasItem(DEFAULT_CORRECT_PREVENT)));
    }

    @Test
    @Transactional
    void getCorrectPrevent() throws Exception {
        // Initialize the database
        correctPreventRepository.saveAndFlush(correctPrevent);

        // Get the correctPrevent
        restCorrectPreventMockMvc
            .perform(get(ENTITY_API_URL_ID, correctPrevent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(correctPrevent.getId().intValue()))
            .andExpect(jsonPath("$.correctPrevent").value(DEFAULT_CORRECT_PREVENT));
    }

    @Test
    @Transactional
    void getNonExistingCorrectPrevent() throws Exception {
        // Get the correctPrevent
        restCorrectPreventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCorrectPrevent() throws Exception {
        // Initialize the database
        correctPreventRepository.saveAndFlush(correctPrevent);

        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();

        // Update the correctPrevent
        CorrectPrevent updatedCorrectPrevent = correctPreventRepository.findById(correctPrevent.getId()).get();
        // Disconnect from session so that the updates on updatedCorrectPrevent are not directly saved in db
        em.detach(updatedCorrectPrevent);
        updatedCorrectPrevent.correctPrevent(UPDATED_CORRECT_PREVENT);
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(updatedCorrectPrevent);

        restCorrectPreventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, correctPreventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isOk());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
        CorrectPrevent testCorrectPrevent = correctPreventList.get(correctPreventList.size() - 1);
        assertThat(testCorrectPrevent.getCorrectPrevent()).isEqualTo(UPDATED_CORRECT_PREVENT);
    }

    @Test
    @Transactional
    void putNonExistingCorrectPrevent() throws Exception {
        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();
        correctPrevent.setId(count.incrementAndGet());

        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrectPreventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, correctPreventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCorrectPrevent() throws Exception {
        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();
        correctPrevent.setId(count.incrementAndGet());

        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrectPreventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCorrectPrevent() throws Exception {
        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();
        correctPrevent.setId(count.incrementAndGet());

        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrectPreventMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCorrectPreventWithPatch() throws Exception {
        // Initialize the database
        correctPreventRepository.saveAndFlush(correctPrevent);

        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();

        // Update the correctPrevent using partial update
        CorrectPrevent partialUpdatedCorrectPrevent = new CorrectPrevent();
        partialUpdatedCorrectPrevent.setId(correctPrevent.getId());

        restCorrectPreventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorrectPrevent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCorrectPrevent))
            )
            .andExpect(status().isOk());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
        CorrectPrevent testCorrectPrevent = correctPreventList.get(correctPreventList.size() - 1);
        assertThat(testCorrectPrevent.getCorrectPrevent()).isEqualTo(DEFAULT_CORRECT_PREVENT);
    }

    @Test
    @Transactional
    void fullUpdateCorrectPreventWithPatch() throws Exception {
        // Initialize the database
        correctPreventRepository.saveAndFlush(correctPrevent);

        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();

        // Update the correctPrevent using partial update
        CorrectPrevent partialUpdatedCorrectPrevent = new CorrectPrevent();
        partialUpdatedCorrectPrevent.setId(correctPrevent.getId());

        partialUpdatedCorrectPrevent.correctPrevent(UPDATED_CORRECT_PREVENT);

        restCorrectPreventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorrectPrevent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCorrectPrevent))
            )
            .andExpect(status().isOk());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
        CorrectPrevent testCorrectPrevent = correctPreventList.get(correctPreventList.size() - 1);
        assertThat(testCorrectPrevent.getCorrectPrevent()).isEqualTo(UPDATED_CORRECT_PREVENT);
    }

    @Test
    @Transactional
    void patchNonExistingCorrectPrevent() throws Exception {
        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();
        correctPrevent.setId(count.incrementAndGet());

        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrectPreventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, correctPreventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCorrectPrevent() throws Exception {
        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();
        correctPrevent.setId(count.incrementAndGet());

        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrectPreventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCorrectPrevent() throws Exception {
        int databaseSizeBeforeUpdate = correctPreventRepository.findAll().size();
        correctPrevent.setId(count.incrementAndGet());

        // Create the CorrectPrevent
        CorrectPreventDTO correctPreventDTO = correctPreventMapper.toDto(correctPrevent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorrectPreventMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(correctPreventDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CorrectPrevent in the database
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCorrectPrevent() throws Exception {
        // Initialize the database
        correctPreventRepository.saveAndFlush(correctPrevent);

        int databaseSizeBeforeDelete = correctPreventRepository.findAll().size();

        // Delete the correctPrevent
        restCorrectPreventMockMvc
            .perform(delete(ENTITY_API_URL_ID, correctPrevent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CorrectPrevent> correctPreventList = correctPreventRepository.findAll();
        assertThat(correctPreventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
