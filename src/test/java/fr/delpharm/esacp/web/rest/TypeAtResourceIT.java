package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.TypeAt;
import fr.delpharm.esacp.repository.TypeAtRepository;
import fr.delpharm.esacp.service.dto.TypeAtDTO;
import fr.delpharm.esacp.service.mapper.TypeAtMapper;
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
 * Integration tests for the {@link TypeAtResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeAtResourceIT {

    private static final String DEFAULT_TYPE_AT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_AT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-ats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeAtRepository typeAtRepository;

    @Autowired
    private TypeAtMapper typeAtMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeAtMockMvc;

    private TypeAt typeAt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAt createEntity(EntityManager em) {
        TypeAt typeAt = new TypeAt().typeAt(DEFAULT_TYPE_AT);
        return typeAt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAt createUpdatedEntity(EntityManager em) {
        TypeAt typeAt = new TypeAt().typeAt(UPDATED_TYPE_AT);
        return typeAt;
    }

    @BeforeEach
    public void initTest() {
        typeAt = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeAt() throws Exception {
        int databaseSizeBeforeCreate = typeAtRepository.findAll().size();
        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);
        restTypeAtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeAtDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAt testTypeAt = typeAtList.get(typeAtList.size() - 1);
        assertThat(testTypeAt.getTypeAt()).isEqualTo(DEFAULT_TYPE_AT);
    }

    @Test
    @Transactional
    void createTypeAtWithExistingId() throws Exception {
        // Create the TypeAt with an existing ID
        typeAt.setId(1L);
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        int databaseSizeBeforeCreate = typeAtRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAtMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeAtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeAts() throws Exception {
        // Initialize the database
        typeAtRepository.saveAndFlush(typeAt);

        // Get all the typeAtList
        restTypeAtMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAt.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeAt").value(hasItem(DEFAULT_TYPE_AT)));
    }

    @Test
    @Transactional
    void getTypeAt() throws Exception {
        // Initialize the database
        typeAtRepository.saveAndFlush(typeAt);

        // Get the typeAt
        restTypeAtMockMvc
            .perform(get(ENTITY_API_URL_ID, typeAt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeAt.getId().intValue()))
            .andExpect(jsonPath("$.typeAt").value(DEFAULT_TYPE_AT));
    }

    @Test
    @Transactional
    void getNonExistingTypeAt() throws Exception {
        // Get the typeAt
        restTypeAtMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeAt() throws Exception {
        // Initialize the database
        typeAtRepository.saveAndFlush(typeAt);

        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();

        // Update the typeAt
        TypeAt updatedTypeAt = typeAtRepository.findById(typeAt.getId()).get();
        // Disconnect from session so that the updates on updatedTypeAt are not directly saved in db
        em.detach(updatedTypeAt);
        updatedTypeAt.typeAt(UPDATED_TYPE_AT);
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(updatedTypeAt);

        restTypeAtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeAtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAtDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
        TypeAt testTypeAt = typeAtList.get(typeAtList.size() - 1);
        assertThat(testTypeAt.getTypeAt()).isEqualTo(UPDATED_TYPE_AT);
    }

    @Test
    @Transactional
    void putNonExistingTypeAt() throws Exception {
        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();
        typeAt.setId(count.incrementAndGet());

        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeAtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeAt() throws Exception {
        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();
        typeAt.setId(count.incrementAndGet());

        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAtMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeAt() throws Exception {
        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();
        typeAt.setId(count.incrementAndGet());

        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAtMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeAtDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeAtWithPatch() throws Exception {
        // Initialize the database
        typeAtRepository.saveAndFlush(typeAt);

        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();

        // Update the typeAt using partial update
        TypeAt partialUpdatedTypeAt = new TypeAt();
        partialUpdatedTypeAt.setId(typeAt.getId());

        restTypeAtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAt))
            )
            .andExpect(status().isOk());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
        TypeAt testTypeAt = typeAtList.get(typeAtList.size() - 1);
        assertThat(testTypeAt.getTypeAt()).isEqualTo(DEFAULT_TYPE_AT);
    }

    @Test
    @Transactional
    void fullUpdateTypeAtWithPatch() throws Exception {
        // Initialize the database
        typeAtRepository.saveAndFlush(typeAt);

        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();

        // Update the typeAt using partial update
        TypeAt partialUpdatedTypeAt = new TypeAt();
        partialUpdatedTypeAt.setId(typeAt.getId());

        partialUpdatedTypeAt.typeAt(UPDATED_TYPE_AT);

        restTypeAtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAt))
            )
            .andExpect(status().isOk());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
        TypeAt testTypeAt = typeAtList.get(typeAtList.size() - 1);
        assertThat(testTypeAt.getTypeAt()).isEqualTo(UPDATED_TYPE_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTypeAt() throws Exception {
        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();
        typeAt.setId(count.incrementAndGet());

        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeAtDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeAt() throws Exception {
        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();
        typeAt.setId(count.incrementAndGet());

        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAtMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeAt() throws Exception {
        int databaseSizeBeforeUpdate = typeAtRepository.findAll().size();
        typeAt.setId(count.incrementAndGet());

        // Create the TypeAt
        TypeAtDTO typeAtDTO = typeAtMapper.toDto(typeAt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAtMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeAtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAt in the database
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeAt() throws Exception {
        // Initialize the database
        typeAtRepository.saveAndFlush(typeAt);

        int databaseSizeBeforeDelete = typeAtRepository.findAll().size();

        // Delete the typeAt
        restTypeAtMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeAt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeAt> typeAtList = typeAtRepository.findAll();
        assertThat(typeAtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
