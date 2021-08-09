package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.TypeAction;
import fr.delpharm.esacp.repository.TypeActionRepository;
import fr.delpharm.esacp.service.dto.TypeActionDTO;
import fr.delpharm.esacp.service.mapper.TypeActionMapper;
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
 * Integration tests for the {@link TypeActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeActionResourceIT {

    private static final String DEFAULT_TYPE_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ACTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeActionRepository typeActionRepository;

    @Autowired
    private TypeActionMapper typeActionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeActionMockMvc;

    private TypeAction typeAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAction createEntity(EntityManager em) {
        TypeAction typeAction = new TypeAction().typeAction(DEFAULT_TYPE_ACTION);
        return typeAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAction createUpdatedEntity(EntityManager em) {
        TypeAction typeAction = new TypeAction().typeAction(UPDATED_TYPE_ACTION);
        return typeAction;
    }

    @BeforeEach
    public void initTest() {
        typeAction = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeAction() throws Exception {
        int databaseSizeBeforeCreate = typeActionRepository.findAll().size();
        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);
        restTypeActionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeActionDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getTypeAction()).isEqualTo(DEFAULT_TYPE_ACTION);
    }

    @Test
    @Transactional
    void createTypeActionWithExistingId() throws Exception {
        // Create the TypeAction with an existing ID
        typeAction.setId(1L);
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        int databaseSizeBeforeCreate = typeActionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeActionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeActions() throws Exception {
        // Initialize the database
        typeActionRepository.saveAndFlush(typeAction);

        // Get all the typeActionList
        restTypeActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeAction").value(hasItem(DEFAULT_TYPE_ACTION)));
    }

    @Test
    @Transactional
    void getTypeAction() throws Exception {
        // Initialize the database
        typeActionRepository.saveAndFlush(typeAction);

        // Get the typeAction
        restTypeActionMockMvc
            .perform(get(ENTITY_API_URL_ID, typeAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeAction.getId().intValue()))
            .andExpect(jsonPath("$.typeAction").value(DEFAULT_TYPE_ACTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeAction() throws Exception {
        // Get the typeAction
        restTypeActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeAction() throws Exception {
        // Initialize the database
        typeActionRepository.saveAndFlush(typeAction);

        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();

        // Update the typeAction
        TypeAction updatedTypeAction = typeActionRepository.findById(typeAction.getId()).get();
        // Disconnect from session so that the updates on updatedTypeAction are not directly saved in db
        em.detach(updatedTypeAction);
        updatedTypeAction.typeAction(UPDATED_TYPE_ACTION);
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(updatedTypeAction);

        restTypeActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeActionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeActionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getTypeAction()).isEqualTo(UPDATED_TYPE_ACTION);
    }

    @Test
    @Transactional
    void putNonExistingTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();
        typeAction.setId(count.incrementAndGet());

        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeActionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();
        typeAction.setId(count.incrementAndGet());

        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();
        typeAction.setId(count.incrementAndGet());

        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeActionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeActionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeActionWithPatch() throws Exception {
        // Initialize the database
        typeActionRepository.saveAndFlush(typeAction);

        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();

        // Update the typeAction using partial update
        TypeAction partialUpdatedTypeAction = new TypeAction();
        partialUpdatedTypeAction.setId(typeAction.getId());

        partialUpdatedTypeAction.typeAction(UPDATED_TYPE_ACTION);

        restTypeActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAction))
            )
            .andExpect(status().isOk());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getTypeAction()).isEqualTo(UPDATED_TYPE_ACTION);
    }

    @Test
    @Transactional
    void fullUpdateTypeActionWithPatch() throws Exception {
        // Initialize the database
        typeActionRepository.saveAndFlush(typeAction);

        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();

        // Update the typeAction using partial update
        TypeAction partialUpdatedTypeAction = new TypeAction();
        partialUpdatedTypeAction.setId(typeAction.getId());

        partialUpdatedTypeAction.typeAction(UPDATED_TYPE_ACTION);

        restTypeActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAction))
            )
            .andExpect(status().isOk());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
        TypeAction testTypeAction = typeActionList.get(typeActionList.size() - 1);
        assertThat(testTypeAction.getTypeAction()).isEqualTo(UPDATED_TYPE_ACTION);
    }

    @Test
    @Transactional
    void patchNonExistingTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();
        typeAction.setId(count.incrementAndGet());

        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeActionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();
        typeAction.setId(count.incrementAndGet());

        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeAction() throws Exception {
        int databaseSizeBeforeUpdate = typeActionRepository.findAll().size();
        typeAction.setId(count.incrementAndGet());

        // Create the TypeAction
        TypeActionDTO typeActionDTO = typeActionMapper.toDto(typeAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeActionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAction in the database
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeAction() throws Exception {
        // Initialize the database
        typeActionRepository.saveAndFlush(typeAction);

        int databaseSizeBeforeDelete = typeActionRepository.findAll().size();

        // Delete the typeAction
        restTypeActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeAction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeAction> typeActionList = typeActionRepository.findAll();
        assertThat(typeActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
