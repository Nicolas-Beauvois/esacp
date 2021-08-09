package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Actions;
import fr.delpharm.esacp.repository.ActionsRepository;
import fr.delpharm.esacp.service.dto.ActionsDTO;
import fr.delpharm.esacp.service.mapper.ActionsMapper;
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
 * Integration tests for the {@link ActionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActionsResourceIT {

    private static final String DEFAULT_REDACTEUR = "AAAAAAAAAA";
    private static final String UPDATED_REDACTEUR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTION_IMMEDIATE = false;
    private static final Boolean UPDATED_IS_ACTION_IMMEDIATE = true;

    private static final LocalDate DEFAULT_DATE_ET_HEURE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_REPONSE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_REPONSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DELAI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELAI = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_PILOTE = "AAAAAAAAAA";
    private static final String UPDATED_PILOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_PORTEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HSE = "AAAAAAAAAA";
    private static final String UPDATED_HSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_VALIDATION_HSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_VALIDATION_HSE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private ActionsMapper actionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionsMockMvc;

    private Actions actions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actions createEntity(EntityManager em) {
        Actions actions = new Actions()
            .redacteur(DEFAULT_REDACTEUR)
            .isActionImmediate(DEFAULT_IS_ACTION_IMMEDIATE)
            .dateEtHeureCreation(DEFAULT_DATE_ET_HEURE_CREATION)
            .titre(DEFAULT_TITRE)
            .descriptionAction(DEFAULT_DESCRIPTION_ACTION)
            .descriptionReponse(DEFAULT_DESCRIPTION_REPONSE)
            .delai(DEFAULT_DELAI)
            .etat(DEFAULT_ETAT)
            .pilote(DEFAULT_PILOTE)
            .dateEtHeureValidationPilote(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(DEFAULT_PORTEUR)
            .dateEtHeureValidationPorteur(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(DEFAULT_HSE)
            .dateEtHeureValidationHse(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE);
        return actions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actions createUpdatedEntity(EntityManager em) {
        Actions actions = new Actions()
            .redacteur(UPDATED_REDACTEUR)
            .isActionImmediate(UPDATED_IS_ACTION_IMMEDIATE)
            .dateEtHeureCreation(UPDATED_DATE_ET_HEURE_CREATION)
            .titre(UPDATED_TITRE)
            .descriptionAction(UPDATED_DESCRIPTION_ACTION)
            .descriptionReponse(UPDATED_DESCRIPTION_REPONSE)
            .delai(UPDATED_DELAI)
            .etat(UPDATED_ETAT)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
        return actions;
    }

    @BeforeEach
    public void initTest() {
        actions = createEntity(em);
    }

    @Test
    @Transactional
    void createActions() throws Exception {
        int databaseSizeBeforeCreate = actionsRepository.findAll().size();
        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);
        restActionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeCreate + 1);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getRedacteur()).isEqualTo(DEFAULT_REDACTEUR);
        assertThat(testActions.getIsActionImmediate()).isEqualTo(DEFAULT_IS_ACTION_IMMEDIATE);
        assertThat(testActions.getDateEtHeureCreation()).isEqualTo(DEFAULT_DATE_ET_HEURE_CREATION);
        assertThat(testActions.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testActions.getDescriptionAction()).isEqualTo(DEFAULT_DESCRIPTION_ACTION);
        assertThat(testActions.getDescriptionReponse()).isEqualTo(DEFAULT_DESCRIPTION_REPONSE);
        assertThat(testActions.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testActions.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testActions.getPilote()).isEqualTo(DEFAULT_PILOTE);
        assertThat(testActions.getDateEtHeureValidationPilote()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testActions.getPorteur()).isEqualTo(DEFAULT_PORTEUR);
        assertThat(testActions.getDateEtHeureValidationPorteur()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testActions.getHse()).isEqualTo(DEFAULT_HSE);
        assertThat(testActions.getDateEtHeureValidationHse()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE);
    }

    @Test
    @Transactional
    void createActionsWithExistingId() throws Exception {
        // Create the Actions with an existing ID
        actions.setId(1L);
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        int databaseSizeBeforeCreate = actionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        // Get all the actionsList
        restActionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actions.getId().intValue())))
            .andExpect(jsonPath("$.[*].redacteur").value(hasItem(DEFAULT_REDACTEUR)))
            .andExpect(jsonPath("$.[*].isActionImmediate").value(hasItem(DEFAULT_IS_ACTION_IMMEDIATE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateEtHeureCreation").value(hasItem(DEFAULT_DATE_ET_HEURE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].descriptionAction").value(hasItem(DEFAULT_DESCRIPTION_ACTION)))
            .andExpect(jsonPath("$.[*].descriptionReponse").value(hasItem(DEFAULT_DESCRIPTION_REPONSE)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].pilote").value(hasItem(DEFAULT_PILOTE)))
            .andExpect(jsonPath("$.[*].dateEtHeureValidationPilote").value(hasItem(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE.toString())))
            .andExpect(jsonPath("$.[*].porteur").value(hasItem(DEFAULT_PORTEUR)))
            .andExpect(jsonPath("$.[*].dateEtHeureValidationPorteur").value(hasItem(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR.toString())))
            .andExpect(jsonPath("$.[*].hse").value(hasItem(DEFAULT_HSE)))
            .andExpect(jsonPath("$.[*].dateEtHeureValidationHse").value(hasItem(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE.toString())));
    }

    @Test
    @Transactional
    void getActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        // Get the actions
        restActionsMockMvc
            .perform(get(ENTITY_API_URL_ID, actions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actions.getId().intValue()))
            .andExpect(jsonPath("$.redacteur").value(DEFAULT_REDACTEUR))
            .andExpect(jsonPath("$.isActionImmediate").value(DEFAULT_IS_ACTION_IMMEDIATE.booleanValue()))
            .andExpect(jsonPath("$.dateEtHeureCreation").value(DEFAULT_DATE_ET_HEURE_CREATION.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.descriptionAction").value(DEFAULT_DESCRIPTION_ACTION))
            .andExpect(jsonPath("$.descriptionReponse").value(DEFAULT_DESCRIPTION_REPONSE))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.pilote").value(DEFAULT_PILOTE))
            .andExpect(jsonPath("$.dateEtHeureValidationPilote").value(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE.toString()))
            .andExpect(jsonPath("$.porteur").value(DEFAULT_PORTEUR))
            .andExpect(jsonPath("$.dateEtHeureValidationPorteur").value(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR.toString()))
            .andExpect(jsonPath("$.hse").value(DEFAULT_HSE))
            .andExpect(jsonPath("$.dateEtHeureValidationHse").value(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActions() throws Exception {
        // Get the actions
        restActionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();

        // Update the actions
        Actions updatedActions = actionsRepository.findById(actions.getId()).get();
        // Disconnect from session so that the updates on updatedActions are not directly saved in db
        em.detach(updatedActions);
        updatedActions
            .redacteur(UPDATED_REDACTEUR)
            .isActionImmediate(UPDATED_IS_ACTION_IMMEDIATE)
            .dateEtHeureCreation(UPDATED_DATE_ET_HEURE_CREATION)
            .titre(UPDATED_TITRE)
            .descriptionAction(UPDATED_DESCRIPTION_ACTION)
            .descriptionReponse(UPDATED_DESCRIPTION_REPONSE)
            .delai(UPDATED_DELAI)
            .etat(UPDATED_ETAT)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
        ActionsDTO actionsDTO = actionsMapper.toDto(updatedActions);

        restActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getRedacteur()).isEqualTo(UPDATED_REDACTEUR);
        assertThat(testActions.getIsActionImmediate()).isEqualTo(UPDATED_IS_ACTION_IMMEDIATE);
        assertThat(testActions.getDateEtHeureCreation()).isEqualTo(UPDATED_DATE_ET_HEURE_CREATION);
        assertThat(testActions.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testActions.getDescriptionAction()).isEqualTo(UPDATED_DESCRIPTION_ACTION);
        assertThat(testActions.getDescriptionReponse()).isEqualTo(UPDATED_DESCRIPTION_REPONSE);
        assertThat(testActions.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testActions.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testActions.getPilote()).isEqualTo(UPDATED_PILOTE);
        assertThat(testActions.getDateEtHeureValidationPilote()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testActions.getPorteur()).isEqualTo(UPDATED_PORTEUR);
        assertThat(testActions.getDateEtHeureValidationPorteur()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testActions.getHse()).isEqualTo(UPDATED_HSE);
        assertThat(testActions.getDateEtHeureValidationHse()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
    }

    @Test
    @Transactional
    void putNonExistingActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActionsWithPatch() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();

        // Update the actions using partial update
        Actions partialUpdatedActions = new Actions();
        partialUpdatedActions.setId(actions.getId());

        partialUpdatedActions
            .dateEtHeureCreation(UPDATED_DATE_ET_HEURE_CREATION)
            .titre(UPDATED_TITRE)
            .descriptionAction(UPDATED_DESCRIPTION_ACTION)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);

        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActions))
            )
            .andExpect(status().isOk());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getRedacteur()).isEqualTo(DEFAULT_REDACTEUR);
        assertThat(testActions.getIsActionImmediate()).isEqualTo(DEFAULT_IS_ACTION_IMMEDIATE);
        assertThat(testActions.getDateEtHeureCreation()).isEqualTo(UPDATED_DATE_ET_HEURE_CREATION);
        assertThat(testActions.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testActions.getDescriptionAction()).isEqualTo(UPDATED_DESCRIPTION_ACTION);
        assertThat(testActions.getDescriptionReponse()).isEqualTo(DEFAULT_DESCRIPTION_REPONSE);
        assertThat(testActions.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testActions.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testActions.getPilote()).isEqualTo(DEFAULT_PILOTE);
        assertThat(testActions.getDateEtHeureValidationPilote()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testActions.getPorteur()).isEqualTo(UPDATED_PORTEUR);
        assertThat(testActions.getDateEtHeureValidationPorteur()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testActions.getHse()).isEqualTo(DEFAULT_HSE);
        assertThat(testActions.getDateEtHeureValidationHse()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
    }

    @Test
    @Transactional
    void fullUpdateActionsWithPatch() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();

        // Update the actions using partial update
        Actions partialUpdatedActions = new Actions();
        partialUpdatedActions.setId(actions.getId());

        partialUpdatedActions
            .redacteur(UPDATED_REDACTEUR)
            .isActionImmediate(UPDATED_IS_ACTION_IMMEDIATE)
            .dateEtHeureCreation(UPDATED_DATE_ET_HEURE_CREATION)
            .titre(UPDATED_TITRE)
            .descriptionAction(UPDATED_DESCRIPTION_ACTION)
            .descriptionReponse(UPDATED_DESCRIPTION_REPONSE)
            .delai(UPDATED_DELAI)
            .etat(UPDATED_ETAT)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);

        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActions))
            )
            .andExpect(status().isOk());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getRedacteur()).isEqualTo(UPDATED_REDACTEUR);
        assertThat(testActions.getIsActionImmediate()).isEqualTo(UPDATED_IS_ACTION_IMMEDIATE);
        assertThat(testActions.getDateEtHeureCreation()).isEqualTo(UPDATED_DATE_ET_HEURE_CREATION);
        assertThat(testActions.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testActions.getDescriptionAction()).isEqualTo(UPDATED_DESCRIPTION_ACTION);
        assertThat(testActions.getDescriptionReponse()).isEqualTo(UPDATED_DESCRIPTION_REPONSE);
        assertThat(testActions.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testActions.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testActions.getPilote()).isEqualTo(UPDATED_PILOTE);
        assertThat(testActions.getDateEtHeureValidationPilote()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testActions.getPorteur()).isEqualTo(UPDATED_PORTEUR);
        assertThat(testActions.getDateEtHeureValidationPorteur()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testActions.getHse()).isEqualTo(UPDATED_HSE);
        assertThat(testActions.getDateEtHeureValidationHse()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
    }

    @Test
    @Transactional
    void patchNonExistingActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, actionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // Create the Actions
        ActionsDTO actionsDTO = actionsMapper.toDto(actions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(actionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeDelete = actionsRepository.findAll().size();

        // Delete the actions
        restActionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, actions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
