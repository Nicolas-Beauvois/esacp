package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Sexe;
import fr.delpharm.esacp.repository.SexeRepository;
import fr.delpharm.esacp.service.dto.SexeDTO;
import fr.delpharm.esacp.service.mapper.SexeMapper;
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
 * Integration tests for the {@link SexeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SexeResourceIT {

    private static final String DEFAULT_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sexes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SexeRepository sexeRepository;

    @Autowired
    private SexeMapper sexeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSexeMockMvc;

    private Sexe sexe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createEntity(EntityManager em) {
        Sexe sexe = new Sexe().sexe(DEFAULT_SEXE);
        return sexe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createUpdatedEntity(EntityManager em) {
        Sexe sexe = new Sexe().sexe(UPDATED_SEXE);
        return sexe;
    }

    @BeforeEach
    public void initTest() {
        sexe = createEntity(em);
    }

    @Test
    @Transactional
    void createSexe() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();
        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);
        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isCreated());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate + 1);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexe()).isEqualTo(DEFAULT_SEXE);
    }

    @Test
    @Transactional
    void createSexeWithExistingId() throws Exception {
        // Create the Sexe with an existing ID
        sexe.setId(1L);
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        int databaseSizeBeforeCreate = sexeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSexes() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sexe.getId().intValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)));
    }

    @Test
    @Transactional
    void getSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get the sexe
        restSexeMockMvc
            .perform(get(ENTITY_API_URL_ID, sexe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sexe.getId().intValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE));
    }

    @Test
    @Transactional
    void getNonExistingSexe() throws Exception {
        // Get the sexe
        restSexeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe
        Sexe updatedSexe = sexeRepository.findById(sexe.getId()).get();
        // Disconnect from session so that the updates on updatedSexe are not directly saved in db
        em.detach(updatedSexe);
        updatedSexe.sexe(UPDATED_SEXE);
        SexeDTO sexeDTO = sexeMapper.toDto(updatedSexe);

        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sexeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexe()).isEqualTo(UPDATED_SEXE);
    }

    @Test
    @Transactional
    void putNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sexeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSexeWithPatch() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe using partial update
        Sexe partialUpdatedSexe = new Sexe();
        partialUpdatedSexe.setId(sexe.getId());

        partialUpdatedSexe.sexe(UPDATED_SEXE);

        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSexe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexe()).isEqualTo(UPDATED_SEXE);
    }

    @Test
    @Transactional
    void fullUpdateSexeWithPatch() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe using partial update
        Sexe partialUpdatedSexe = new Sexe();
        partialUpdatedSexe.setId(sexe.getId());

        partialUpdatedSexe.sexe(UPDATED_SEXE);

        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSexe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getSexe()).isEqualTo(UPDATED_SEXE);
    }

    @Test
    @Transactional
    void patchNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sexeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sexeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // Create the Sexe
        SexeDTO sexeDTO = sexeMapper.toDto(sexe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sexeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeDelete = sexeRepository.findAll().size();

        // Delete the sexe
        restSexeMockMvc
            .perform(delete(ENTITY_API_URL_ID, sexe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
