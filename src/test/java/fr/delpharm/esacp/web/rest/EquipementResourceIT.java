package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Equipement;
import fr.delpharm.esacp.repository.EquipementRepository;
import fr.delpharm.esacp.service.dto.EquipementDTO;
import fr.delpharm.esacp.service.mapper.EquipementMapper;
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
 * Integration tests for the {@link EquipementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipementResourceIT {

    private static final String DEFAULT_EQUIPEMENT = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private EquipementMapper equipementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipementMockMvc;

    private Equipement equipement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipement createEntity(EntityManager em) {
        Equipement equipement = new Equipement().equipement(DEFAULT_EQUIPEMENT);
        return equipement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipement createUpdatedEntity(EntityManager em) {
        Equipement equipement = new Equipement().equipement(UPDATED_EQUIPEMENT);
        return equipement;
    }

    @BeforeEach
    public void initTest() {
        equipement = createEntity(em);
    }

    @Test
    @Transactional
    void createEquipement() throws Exception {
        int databaseSizeBeforeCreate = equipementRepository.findAll().size();
        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);
        restEquipementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipementDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeCreate + 1);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getEquipement()).isEqualTo(DEFAULT_EQUIPEMENT);
    }

    @Test
    @Transactional
    void createEquipementWithExistingId() throws Exception {
        // Create the Equipement with an existing ID
        equipement.setId(1L);
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        int databaseSizeBeforeCreate = equipementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEquipements() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        // Get all the equipementList
        restEquipementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipement.getId().intValue())))
            .andExpect(jsonPath("$.[*].equipement").value(hasItem(DEFAULT_EQUIPEMENT)));
    }

    @Test
    @Transactional
    void getEquipement() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        // Get the equipement
        restEquipementMockMvc
            .perform(get(ENTITY_API_URL_ID, equipement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipement.getId().intValue()))
            .andExpect(jsonPath("$.equipement").value(DEFAULT_EQUIPEMENT));
    }

    @Test
    @Transactional
    void getNonExistingEquipement() throws Exception {
        // Get the equipement
        restEquipementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEquipement() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement
        Equipement updatedEquipement = equipementRepository.findById(equipement.getId()).get();
        // Disconnect from session so that the updates on updatedEquipement are not directly saved in db
        em.detach(updatedEquipement);
        updatedEquipement.equipement(UPDATED_EQUIPEMENT);
        EquipementDTO equipementDTO = equipementMapper.toDto(updatedEquipement);

        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getEquipement()).isEqualTo(UPDATED_EQUIPEMENT);
    }

    @Test
    @Transactional
    void putNonExistingEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(count.incrementAndGet());

        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(count.incrementAndGet());

        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(equipementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(count.incrementAndGet());

        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(equipementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipementWithPatch() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement using partial update
        Equipement partialUpdatedEquipement = new Equipement();
        partialUpdatedEquipement.setId(equipement.getId());

        partialUpdatedEquipement.equipement(UPDATED_EQUIPEMENT);

        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipement))
            )
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getEquipement()).isEqualTo(UPDATED_EQUIPEMENT);
    }

    @Test
    @Transactional
    void fullUpdateEquipementWithPatch() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement using partial update
        Equipement partialUpdatedEquipement = new Equipement();
        partialUpdatedEquipement.setId(equipement.getId());

        partialUpdatedEquipement.equipement(UPDATED_EQUIPEMENT);

        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEquipement))
            )
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getEquipement()).isEqualTo(UPDATED_EQUIPEMENT);
    }

    @Test
    @Transactional
    void patchNonExistingEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(count.incrementAndGet());

        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(count.incrementAndGet());

        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(equipementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();
        equipement.setId(count.incrementAndGet());

        // Create the Equipement
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(equipementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipement() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        int databaseSizeBeforeDelete = equipementRepository.findAll().size();

        // Delete the equipement
        restEquipementMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
