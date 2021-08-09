package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.FicheSuiviSante;
import fr.delpharm.esacp.repository.FicheSuiviSanteRepository;
import fr.delpharm.esacp.service.dto.FicheSuiviSanteDTO;
import fr.delpharm.esacp.service.mapper.FicheSuiviSanteMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FicheSuiviSanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FicheSuiviSanteResourceIT {

    private static final Boolean DEFAULT_SUIVI_INDIVIDUEL = false;
    private static final Boolean UPDATED_SUIVI_INDIVIDUEL = true;

    private static final String DEFAULT_MEDECIN_DU_TRAVAIL = "AAAAAAAAAA";
    private static final String UPDATED_MEDECIN_DU_TRAVAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEDEDEBUT_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEDEBUT_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEDEFIN_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEFIN_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROPOSITION_MEDECIN_DU_TRAVAIL = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSITION_MEDECIN_DU_TRAVAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_A_REVOIR_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_A_REVOIR_LE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/fiche-suivi-santes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FicheSuiviSanteRepository ficheSuiviSanteRepository;

    @Autowired
    private FicheSuiviSanteMapper ficheSuiviSanteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFicheSuiviSanteMockMvc;

    private FicheSuiviSante ficheSuiviSante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FicheSuiviSante createEntity(EntityManager em) {
        FicheSuiviSante ficheSuiviSante = new FicheSuiviSante()
            .suiviIndividuel(DEFAULT_SUIVI_INDIVIDUEL)
            .medecinDuTravail(DEFAULT_MEDECIN_DU_TRAVAIL)
            .dateDeCreation(DEFAULT_DATE_DE_CREATION)
            .datededebutAT(DEFAULT_DATEDEDEBUT_AT)
            .datedefinAT(DEFAULT_DATEDEFIN_AT)
            .propositionMedecinDuTravail(DEFAULT_PROPOSITION_MEDECIN_DU_TRAVAIL)
            .aRevoirLe(DEFAULT_A_REVOIR_LE);
        return ficheSuiviSante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FicheSuiviSante createUpdatedEntity(EntityManager em) {
        FicheSuiviSante ficheSuiviSante = new FicheSuiviSante()
            .suiviIndividuel(UPDATED_SUIVI_INDIVIDUEL)
            .medecinDuTravail(UPDATED_MEDECIN_DU_TRAVAIL)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .datededebutAT(UPDATED_DATEDEDEBUT_AT)
            .datedefinAT(UPDATED_DATEDEFIN_AT)
            .propositionMedecinDuTravail(UPDATED_PROPOSITION_MEDECIN_DU_TRAVAIL)
            .aRevoirLe(UPDATED_A_REVOIR_LE);
        return ficheSuiviSante;
    }

    @BeforeEach
    public void initTest() {
        ficheSuiviSante = createEntity(em);
    }

    @Test
    @Transactional
    void createFicheSuiviSante() throws Exception {
        int databaseSizeBeforeCreate = ficheSuiviSanteRepository.findAll().size();
        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);
        restFicheSuiviSanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeCreate + 1);
        FicheSuiviSante testFicheSuiviSante = ficheSuiviSanteList.get(ficheSuiviSanteList.size() - 1);
        assertThat(testFicheSuiviSante.getSuiviIndividuel()).isEqualTo(DEFAULT_SUIVI_INDIVIDUEL);
        assertThat(testFicheSuiviSante.getMedecinDuTravail()).isEqualTo(DEFAULT_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getDateDeCreation()).isEqualTo(DEFAULT_DATE_DE_CREATION);
        assertThat(testFicheSuiviSante.getDatededebutAT()).isEqualTo(DEFAULT_DATEDEDEBUT_AT);
        assertThat(testFicheSuiviSante.getDatedefinAT()).isEqualTo(DEFAULT_DATEDEFIN_AT);
        assertThat(testFicheSuiviSante.getPropositionMedecinDuTravail()).isEqualTo(DEFAULT_PROPOSITION_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getaRevoirLe()).isEqualTo(DEFAULT_A_REVOIR_LE);
    }

    @Test
    @Transactional
    void createFicheSuiviSanteWithExistingId() throws Exception {
        // Create the FicheSuiviSante with an existing ID
        ficheSuiviSante.setId(1L);
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        int databaseSizeBeforeCreate = ficheSuiviSanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheSuiviSanteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFicheSuiviSantes() throws Exception {
        // Initialize the database
        ficheSuiviSanteRepository.saveAndFlush(ficheSuiviSante);

        // Get all the ficheSuiviSanteList
        restFicheSuiviSanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheSuiviSante.getId().intValue())))
            .andExpect(jsonPath("$.[*].suiviIndividuel").value(hasItem(DEFAULT_SUIVI_INDIVIDUEL.booleanValue())))
            .andExpect(jsonPath("$.[*].medecinDuTravail").value(hasItem(DEFAULT_MEDECIN_DU_TRAVAIL)))
            .andExpect(jsonPath("$.[*].dateDeCreation").value(hasItem(DEFAULT_DATE_DE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].datededebutAT").value(hasItem(DEFAULT_DATEDEDEBUT_AT.toString())))
            .andExpect(jsonPath("$.[*].datedefinAT").value(hasItem(DEFAULT_DATEDEFIN_AT.toString())))
            .andExpect(jsonPath("$.[*].propositionMedecinDuTravail").value(hasItem(DEFAULT_PROPOSITION_MEDECIN_DU_TRAVAIL)))
            .andExpect(jsonPath("$.[*].aRevoirLe").value(hasItem(DEFAULT_A_REVOIR_LE.toString())));
    }

    @Test
    @Transactional
    void getFicheSuiviSante() throws Exception {
        // Initialize the database
        ficheSuiviSanteRepository.saveAndFlush(ficheSuiviSante);

        // Get the ficheSuiviSante
        restFicheSuiviSanteMockMvc
            .perform(get(ENTITY_API_URL_ID, ficheSuiviSante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ficheSuiviSante.getId().intValue()))
            .andExpect(jsonPath("$.suiviIndividuel").value(DEFAULT_SUIVI_INDIVIDUEL.booleanValue()))
            .andExpect(jsonPath("$.medecinDuTravail").value(DEFAULT_MEDECIN_DU_TRAVAIL))
            .andExpect(jsonPath("$.dateDeCreation").value(DEFAULT_DATE_DE_CREATION.toString()))
            .andExpect(jsonPath("$.datededebutAT").value(DEFAULT_DATEDEDEBUT_AT.toString()))
            .andExpect(jsonPath("$.datedefinAT").value(DEFAULT_DATEDEFIN_AT.toString()))
            .andExpect(jsonPath("$.propositionMedecinDuTravail").value(DEFAULT_PROPOSITION_MEDECIN_DU_TRAVAIL))
            .andExpect(jsonPath("$.aRevoirLe").value(DEFAULT_A_REVOIR_LE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFicheSuiviSante() throws Exception {
        // Get the ficheSuiviSante
        restFicheSuiviSanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFicheSuiviSante() throws Exception {
        // Initialize the database
        ficheSuiviSanteRepository.saveAndFlush(ficheSuiviSante);

        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();

        // Update the ficheSuiviSante
        FicheSuiviSante updatedFicheSuiviSante = ficheSuiviSanteRepository.findById(ficheSuiviSante.getId()).get();
        // Disconnect from session so that the updates on updatedFicheSuiviSante are not directly saved in db
        em.detach(updatedFicheSuiviSante);
        updatedFicheSuiviSante
            .suiviIndividuel(UPDATED_SUIVI_INDIVIDUEL)
            .medecinDuTravail(UPDATED_MEDECIN_DU_TRAVAIL)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .datededebutAT(UPDATED_DATEDEDEBUT_AT)
            .datedefinAT(UPDATED_DATEDEFIN_AT)
            .propositionMedecinDuTravail(UPDATED_PROPOSITION_MEDECIN_DU_TRAVAIL)
            .aRevoirLe(UPDATED_A_REVOIR_LE);
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(updatedFicheSuiviSante);

        restFicheSuiviSanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ficheSuiviSanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isOk());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
        FicheSuiviSante testFicheSuiviSante = ficheSuiviSanteList.get(ficheSuiviSanteList.size() - 1);
        assertThat(testFicheSuiviSante.getSuiviIndividuel()).isEqualTo(UPDATED_SUIVI_INDIVIDUEL);
        assertThat(testFicheSuiviSante.getMedecinDuTravail()).isEqualTo(UPDATED_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testFicheSuiviSante.getDatededebutAT()).isEqualTo(UPDATED_DATEDEDEBUT_AT);
        assertThat(testFicheSuiviSante.getDatedefinAT()).isEqualTo(UPDATED_DATEDEFIN_AT);
        assertThat(testFicheSuiviSante.getPropositionMedecinDuTravail()).isEqualTo(UPDATED_PROPOSITION_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getaRevoirLe()).isEqualTo(UPDATED_A_REVOIR_LE);
    }

    @Test
    @Transactional
    void putNonExistingFicheSuiviSante() throws Exception {
        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();
        ficheSuiviSante.setId(count.incrementAndGet());

        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheSuiviSanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ficheSuiviSanteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFicheSuiviSante() throws Exception {
        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();
        ficheSuiviSante.setId(count.incrementAndGet());

        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheSuiviSanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFicheSuiviSante() throws Exception {
        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();
        ficheSuiviSante.setId(count.incrementAndGet());

        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheSuiviSanteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFicheSuiviSanteWithPatch() throws Exception {
        // Initialize the database
        ficheSuiviSanteRepository.saveAndFlush(ficheSuiviSante);

        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();

        // Update the ficheSuiviSante using partial update
        FicheSuiviSante partialUpdatedFicheSuiviSante = new FicheSuiviSante();
        partialUpdatedFicheSuiviSante.setId(ficheSuiviSante.getId());

        partialUpdatedFicheSuiviSante
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .datededebutAT(UPDATED_DATEDEDEBUT_AT)
            .aRevoirLe(UPDATED_A_REVOIR_LE);

        restFicheSuiviSanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFicheSuiviSante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFicheSuiviSante))
            )
            .andExpect(status().isOk());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
        FicheSuiviSante testFicheSuiviSante = ficheSuiviSanteList.get(ficheSuiviSanteList.size() - 1);
        assertThat(testFicheSuiviSante.getSuiviIndividuel()).isEqualTo(DEFAULT_SUIVI_INDIVIDUEL);
        assertThat(testFicheSuiviSante.getMedecinDuTravail()).isEqualTo(DEFAULT_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testFicheSuiviSante.getDatededebutAT()).isEqualTo(UPDATED_DATEDEDEBUT_AT);
        assertThat(testFicheSuiviSante.getDatedefinAT()).isEqualTo(DEFAULT_DATEDEFIN_AT);
        assertThat(testFicheSuiviSante.getPropositionMedecinDuTravail()).isEqualTo(DEFAULT_PROPOSITION_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getaRevoirLe()).isEqualTo(UPDATED_A_REVOIR_LE);
    }

    @Test
    @Transactional
    void fullUpdateFicheSuiviSanteWithPatch() throws Exception {
        // Initialize the database
        ficheSuiviSanteRepository.saveAndFlush(ficheSuiviSante);

        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();

        // Update the ficheSuiviSante using partial update
        FicheSuiviSante partialUpdatedFicheSuiviSante = new FicheSuiviSante();
        partialUpdatedFicheSuiviSante.setId(ficheSuiviSante.getId());

        partialUpdatedFicheSuiviSante
            .suiviIndividuel(UPDATED_SUIVI_INDIVIDUEL)
            .medecinDuTravail(UPDATED_MEDECIN_DU_TRAVAIL)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .datededebutAT(UPDATED_DATEDEDEBUT_AT)
            .datedefinAT(UPDATED_DATEDEFIN_AT)
            .propositionMedecinDuTravail(UPDATED_PROPOSITION_MEDECIN_DU_TRAVAIL)
            .aRevoirLe(UPDATED_A_REVOIR_LE);

        restFicheSuiviSanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFicheSuiviSante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFicheSuiviSante))
            )
            .andExpect(status().isOk());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
        FicheSuiviSante testFicheSuiviSante = ficheSuiviSanteList.get(ficheSuiviSanteList.size() - 1);
        assertThat(testFicheSuiviSante.getSuiviIndividuel()).isEqualTo(UPDATED_SUIVI_INDIVIDUEL);
        assertThat(testFicheSuiviSante.getMedecinDuTravail()).isEqualTo(UPDATED_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testFicheSuiviSante.getDatededebutAT()).isEqualTo(UPDATED_DATEDEDEBUT_AT);
        assertThat(testFicheSuiviSante.getDatedefinAT()).isEqualTo(UPDATED_DATEDEFIN_AT);
        assertThat(testFicheSuiviSante.getPropositionMedecinDuTravail()).isEqualTo(UPDATED_PROPOSITION_MEDECIN_DU_TRAVAIL);
        assertThat(testFicheSuiviSante.getaRevoirLe()).isEqualTo(UPDATED_A_REVOIR_LE);
    }

    @Test
    @Transactional
    void patchNonExistingFicheSuiviSante() throws Exception {
        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();
        ficheSuiviSante.setId(count.incrementAndGet());

        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheSuiviSanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ficheSuiviSanteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFicheSuiviSante() throws Exception {
        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();
        ficheSuiviSante.setId(count.incrementAndGet());

        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheSuiviSanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFicheSuiviSante() throws Exception {
        int databaseSizeBeforeUpdate = ficheSuiviSanteRepository.findAll().size();
        ficheSuiviSante.setId(count.incrementAndGet());

        // Create the FicheSuiviSante
        FicheSuiviSanteDTO ficheSuiviSanteDTO = ficheSuiviSanteMapper.toDto(ficheSuiviSante);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFicheSuiviSanteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ficheSuiviSanteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FicheSuiviSante in the database
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFicheSuiviSante() throws Exception {
        // Initialize the database
        ficheSuiviSanteRepository.saveAndFlush(ficheSuiviSante);

        int databaseSizeBeforeDelete = ficheSuiviSanteRepository.findAll().size();

        // Delete the ficheSuiviSante
        restFicheSuiviSanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ficheSuiviSante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FicheSuiviSante> ficheSuiviSanteList = ficheSuiviSanteRepository.findAll();
        assertThat(ficheSuiviSanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
