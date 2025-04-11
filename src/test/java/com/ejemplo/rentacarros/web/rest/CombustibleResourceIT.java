package com.ejemplo.rentacarros.web.rest;

import static com.ejemplo.rentacarros.domain.CombustibleAsserts.*;
import static com.ejemplo.rentacarros.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ejemplo.rentacarros.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ejemplo.rentacarros.IntegrationTest;
import com.ejemplo.rentacarros.domain.Combustible;
import com.ejemplo.rentacarros.repository.CombustibleRepository;
import com.ejemplo.rentacarros.service.dto.CombustibleDTO;
import com.ejemplo.rentacarros.service.mapper.CombustibleMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CombustibleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CombustibleResourceIT {

    private static final Float DEFAULT_NIVEL_ACTUAL = 1F;
    private static final Float UPDATED_NIVEL_ACTUAL = 2F;

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_REGISTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_REGISTRO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/combustibles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CombustibleRepository combustibleRepository;

    @Autowired
    private CombustibleMapper combustibleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCombustibleMockMvc;

    private Combustible combustible;

    private Combustible insertedCombustible;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Combustible createEntity() {
        return new Combustible().nivelActual(DEFAULT_NIVEL_ACTUAL).tipo(DEFAULT_TIPO).fechaRegistro(DEFAULT_FECHA_REGISTRO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Combustible createUpdatedEntity() {
        return new Combustible().nivelActual(UPDATED_NIVEL_ACTUAL).tipo(UPDATED_TIPO).fechaRegistro(UPDATED_FECHA_REGISTRO);
    }

    @BeforeEach
    void initTest() {
        combustible = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCombustible != null) {
            combustibleRepository.delete(insertedCombustible);
            insertedCombustible = null;
        }
    }

    @Test
    @Transactional
    void createCombustible() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);
        var returnedCombustibleDTO = om.readValue(
            restCombustibleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combustibleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CombustibleDTO.class
        );

        // Validate the Combustible in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCombustible = combustibleMapper.toEntity(returnedCombustibleDTO);
        assertCombustibleUpdatableFieldsEquals(returnedCombustible, getPersistedCombustible(returnedCombustible));

        insertedCombustible = returnedCombustible;
    }

    @Test
    @Transactional
    void createCombustibleWithExistingId() throws Exception {
        // Create the Combustible with an existing ID
        combustible.setId(1L);
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCombustibleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combustibleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNivelActualIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        combustible.setNivelActual(null);

        // Create the Combustible, which fails.
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        restCombustibleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combustibleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaRegistroIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        combustible.setFechaRegistro(null);

        // Create the Combustible, which fails.
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        restCombustibleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combustibleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCombustibles() throws Exception {
        // Initialize the database
        insertedCombustible = combustibleRepository.saveAndFlush(combustible);

        // Get all the combustibleList
        restCombustibleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(combustible.getId().intValue())))
            .andExpect(jsonPath("$.[*].nivelActual").value(hasItem(DEFAULT_NIVEL_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(sameInstant(DEFAULT_FECHA_REGISTRO))));
    }

    @Test
    @Transactional
    void getCombustible() throws Exception {
        // Initialize the database
        insertedCombustible = combustibleRepository.saveAndFlush(combustible);

        // Get the combustible
        restCombustibleMockMvc
            .perform(get(ENTITY_API_URL_ID, combustible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(combustible.getId().intValue()))
            .andExpect(jsonPath("$.nivelActual").value(DEFAULT_NIVEL_ACTUAL.doubleValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.fechaRegistro").value(sameInstant(DEFAULT_FECHA_REGISTRO)));
    }

    @Test
    @Transactional
    void getNonExistingCombustible() throws Exception {
        // Get the combustible
        restCombustibleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCombustible() throws Exception {
        // Initialize the database
        insertedCombustible = combustibleRepository.saveAndFlush(combustible);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the combustible
        Combustible updatedCombustible = combustibleRepository.findById(combustible.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCombustible are not directly saved in db
        em.detach(updatedCombustible);
        updatedCombustible.nivelActual(UPDATED_NIVEL_ACTUAL).tipo(UPDATED_TIPO).fechaRegistro(UPDATED_FECHA_REGISTRO);
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(updatedCombustible);

        restCombustibleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, combustibleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(combustibleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCombustibleToMatchAllProperties(updatedCombustible);
    }

    @Test
    @Transactional
    void putNonExistingCombustible() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combustible.setId(longCount.incrementAndGet());

        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCombustibleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, combustibleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(combustibleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCombustible() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combustible.setId(longCount.incrementAndGet());

        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombustibleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(combustibleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCombustible() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combustible.setId(longCount.incrementAndGet());

        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombustibleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(combustibleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCombustibleWithPatch() throws Exception {
        // Initialize the database
        insertedCombustible = combustibleRepository.saveAndFlush(combustible);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the combustible using partial update
        Combustible partialUpdatedCombustible = new Combustible();
        partialUpdatedCombustible.setId(combustible.getId());

        partialUpdatedCombustible.nivelActual(UPDATED_NIVEL_ACTUAL);

        restCombustibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCombustible.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCombustible))
            )
            .andExpect(status().isOk());

        // Validate the Combustible in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCombustibleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCombustible, combustible),
            getPersistedCombustible(combustible)
        );
    }

    @Test
    @Transactional
    void fullUpdateCombustibleWithPatch() throws Exception {
        // Initialize the database
        insertedCombustible = combustibleRepository.saveAndFlush(combustible);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the combustible using partial update
        Combustible partialUpdatedCombustible = new Combustible();
        partialUpdatedCombustible.setId(combustible.getId());

        partialUpdatedCombustible.nivelActual(UPDATED_NIVEL_ACTUAL).tipo(UPDATED_TIPO).fechaRegistro(UPDATED_FECHA_REGISTRO);

        restCombustibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCombustible.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCombustible))
            )
            .andExpect(status().isOk());

        // Validate the Combustible in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCombustibleUpdatableFieldsEquals(partialUpdatedCombustible, getPersistedCombustible(partialUpdatedCombustible));
    }

    @Test
    @Transactional
    void patchNonExistingCombustible() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combustible.setId(longCount.incrementAndGet());

        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCombustibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, combustibleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(combustibleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCombustible() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combustible.setId(longCount.incrementAndGet());

        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombustibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(combustibleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCombustible() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        combustible.setId(longCount.incrementAndGet());

        // Create the Combustible
        CombustibleDTO combustibleDTO = combustibleMapper.toDto(combustible);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCombustibleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(combustibleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Combustible in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCombustible() throws Exception {
        // Initialize the database
        insertedCombustible = combustibleRepository.saveAndFlush(combustible);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the combustible
        restCombustibleMockMvc
            .perform(delete(ENTITY_API_URL_ID, combustible.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return combustibleRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Combustible getPersistedCombustible(Combustible combustible) {
        return combustibleRepository.findById(combustible.getId()).orElseThrow();
    }

    protected void assertPersistedCombustibleToMatchAllProperties(Combustible expectedCombustible) {
        assertCombustibleAllPropertiesEquals(expectedCombustible, getPersistedCombustible(expectedCombustible));
    }

    protected void assertPersistedCombustibleToMatchUpdatableProperties(Combustible expectedCombustible) {
        assertCombustibleAllUpdatablePropertiesEquals(expectedCombustible, getPersistedCombustible(expectedCombustible));
    }
}
