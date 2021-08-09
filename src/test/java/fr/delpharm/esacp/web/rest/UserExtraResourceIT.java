package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.UserExtra;
import fr.delpharm.esacp.repository.UserExtraRepository;
import fr.delpharm.esacp.service.dto.UserExtraDTO;
import fr.delpharm.esacp.service.mapper.UserExtraMapper;
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
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserExtraResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_REDACTEUR = false;
    private static final Boolean UPDATED_IS_REDACTEUR = true;

    private static final Boolean DEFAULT_IS_PILOTE = false;
    private static final Boolean UPDATED_IS_PILOTE = true;

    private static final Boolean DEFAULT_IS_PORTEUR = false;
    private static final Boolean UPDATED_IS_PORTEUR = true;

    private static final Boolean DEFAULT_IS_CODIR = false;
    private static final Boolean UPDATED_IS_CODIR = true;

    private static final Boolean DEFAULT_IS_HSE = false;
    private static final Boolean UPDATED_IS_HSE = true;

    private static final Boolean DEFAULT_IS_VALIDATEUR_HSE = false;
    private static final Boolean UPDATED_IS_VALIDATEUR_HSE = true;

    private static final String ENTITY_API_URL = "/api/user-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserExtraRepository userExtraRepository;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .matricule(DEFAULT_MATRICULE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .isRedacteur(DEFAULT_IS_REDACTEUR)
            .isPilote(DEFAULT_IS_PILOTE)
            .isPorteur(DEFAULT_IS_PORTEUR)
            .isCodir(DEFAULT_IS_CODIR)
            .isHse(DEFAULT_IS_HSE)
            .isValidateurHse(DEFAULT_IS_VALIDATEUR_HSE);
        return userExtra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra()
            .matricule(UPDATED_MATRICULE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .isRedacteur(UPDATED_IS_REDACTEUR)
            .isPilote(UPDATED_IS_PILOTE)
            .isPorteur(UPDATED_IS_PORTEUR)
            .isCodir(UPDATED_IS_CODIR)
            .isHse(UPDATED_IS_HSE)
            .isValidateurHse(UPDATED_IS_VALIDATEUR_HSE);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();
        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testUserExtra.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testUserExtra.getIsRedacteur()).isEqualTo(DEFAULT_IS_REDACTEUR);
        assertThat(testUserExtra.getIsPilote()).isEqualTo(DEFAULT_IS_PILOTE);
        assertThat(testUserExtra.getIsPorteur()).isEqualTo(DEFAULT_IS_PORTEUR);
        assertThat(testUserExtra.getIsCodir()).isEqualTo(DEFAULT_IS_CODIR);
        assertThat(testUserExtra.getIsHse()).isEqualTo(DEFAULT_IS_HSE);
        assertThat(testUserExtra.getIsValidateurHse()).isEqualTo(DEFAULT_IS_VALIDATEUR_HSE);
    }

    @Test
    @Transactional
    void createUserExtraWithExistingId() throws Exception {
        // Create the UserExtra with an existing ID
        userExtra.setId(1L);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].isRedacteur").value(hasItem(DEFAULT_IS_REDACTEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].isPilote").value(hasItem(DEFAULT_IS_PILOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].isPorteur").value(hasItem(DEFAULT_IS_PORTEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].isCodir").value(hasItem(DEFAULT_IS_CODIR.booleanValue())))
            .andExpect(jsonPath("$.[*].isHse").value(hasItem(DEFAULT_IS_HSE.booleanValue())))
            .andExpect(jsonPath("$.[*].isValidateurHse").value(hasItem(DEFAULT_IS_VALIDATEUR_HSE.booleanValue())));
    }

    @Test
    @Transactional
    void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.isRedacteur").value(DEFAULT_IS_REDACTEUR.booleanValue()))
            .andExpect(jsonPath("$.isPilote").value(DEFAULT_IS_PILOTE.booleanValue()))
            .andExpect(jsonPath("$.isPorteur").value(DEFAULT_IS_PORTEUR.booleanValue()))
            .andExpect(jsonPath("$.isCodir").value(DEFAULT_IS_CODIR.booleanValue()))
            .andExpect(jsonPath("$.isHse").value(DEFAULT_IS_HSE.booleanValue()))
            .andExpect(jsonPath("$.isValidateurHse").value(DEFAULT_IS_VALIDATEUR_HSE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra
            .matricule(UPDATED_MATRICULE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .isRedacteur(UPDATED_IS_REDACTEUR)
            .isPilote(UPDATED_IS_PILOTE)
            .isPorteur(UPDATED_IS_PORTEUR)
            .isCodir(UPDATED_IS_CODIR)
            .isHse(UPDATED_IS_HSE)
            .isValidateurHse(UPDATED_IS_VALIDATEUR_HSE);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(updatedUserExtra);

        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testUserExtra.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testUserExtra.getIsRedacteur()).isEqualTo(UPDATED_IS_REDACTEUR);
        assertThat(testUserExtra.getIsPilote()).isEqualTo(UPDATED_IS_PILOTE);
        assertThat(testUserExtra.getIsPorteur()).isEqualTo(UPDATED_IS_PORTEUR);
        assertThat(testUserExtra.getIsCodir()).isEqualTo(UPDATED_IS_CODIR);
        assertThat(testUserExtra.getIsHse()).isEqualTo(UPDATED_IS_HSE);
        assertThat(testUserExtra.getIsValidateurHse()).isEqualTo(UPDATED_IS_VALIDATEUR_HSE);
    }

    @Test
    @Transactional
    void putNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra
            .matricule(UPDATED_MATRICULE)
            .isPilote(UPDATED_IS_PILOTE)
            .isPorteur(UPDATED_IS_PORTEUR)
            .isCodir(UPDATED_IS_CODIR)
            .isHse(UPDATED_IS_HSE)
            .isValidateurHse(UPDATED_IS_VALIDATEUR_HSE);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testUserExtra.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testUserExtra.getIsRedacteur()).isEqualTo(DEFAULT_IS_REDACTEUR);
        assertThat(testUserExtra.getIsPilote()).isEqualTo(UPDATED_IS_PILOTE);
        assertThat(testUserExtra.getIsPorteur()).isEqualTo(UPDATED_IS_PORTEUR);
        assertThat(testUserExtra.getIsCodir()).isEqualTo(UPDATED_IS_CODIR);
        assertThat(testUserExtra.getIsHse()).isEqualTo(UPDATED_IS_HSE);
        assertThat(testUserExtra.getIsValidateurHse()).isEqualTo(UPDATED_IS_VALIDATEUR_HSE);
    }

    @Test
    @Transactional
    void fullUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra
            .matricule(UPDATED_MATRICULE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .isRedacteur(UPDATED_IS_REDACTEUR)
            .isPilote(UPDATED_IS_PILOTE)
            .isPorteur(UPDATED_IS_PORTEUR)
            .isCodir(UPDATED_IS_CODIR)
            .isHse(UPDATED_IS_HSE)
            .isValidateurHse(UPDATED_IS_VALIDATEUR_HSE);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testUserExtra.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testUserExtra.getIsRedacteur()).isEqualTo(UPDATED_IS_REDACTEUR);
        assertThat(testUserExtra.getIsPilote()).isEqualTo(UPDATED_IS_PILOTE);
        assertThat(testUserExtra.getIsPorteur()).isEqualTo(UPDATED_IS_PORTEUR);
        assertThat(testUserExtra.getIsCodir()).isEqualTo(UPDATED_IS_CODIR);
        assertThat(testUserExtra.getIsHse()).isEqualTo(UPDATED_IS_HSE);
        assertThat(testUserExtra.getIsValidateurHse()).isEqualTo(UPDATED_IS_VALIDATEUR_HSE);
    }

    @Test
    @Transactional
    void patchNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // Create the UserExtra
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExtraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
