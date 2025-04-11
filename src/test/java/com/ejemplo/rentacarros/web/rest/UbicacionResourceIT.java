package com.ejemplo.rentacarros.web.rest;

import static com.ejemplo.rentacarros.domain.UbicacionAsserts.*;
import static com.ejemplo.rentacarros.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ejemplo.rentacarros.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ejemplo.rentacarros.IntegrationTest;
import com.ejemplo.rentacarros.domain.Ubicacion;
import com.ejemplo.rentacarros.repository.UbicacionRepository;
import com.ejemplo.rentacarros.service.dto.UbicacionDTO;
import com.ejemplo.rentacarros.service.mapper.UbicacionMapper;
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
 * Integration tests for the {@link UbicacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UbicacionResourceIT {

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/ubicacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private UbicacionMapper ubicacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUbicacionMockMvc;

    private Ubicacion ubicacion;

    private Ubicacion insertedUbicacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ubicacion createEntity() {
        return new Ubicacion().latitud(DEFAULT_LATITUD).longitud(DEFAULT_LONGITUD).timestamp(DEFAULT_TIMESTAMP);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ubicacion createUpdatedEntity() {
        return new Ubicacion().latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD).timestamp(UPDATED_TIMESTAMP);
    }

    @BeforeEach
    void initTest() {
        ubicacion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUbicacion != null) {
            ubicacionRepository.delete(insertedUbicacion);
            insertedUbicacion = null;
        }
    }

    @Test
    @Transactional
    void createUbicacion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);
        var returnedUbicacionDTO = om.readValue(
            restUbicacionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UbicacionDTO.class
        );

        // Validate the Ubicacion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUbicacion = ubicacionMapper.toEntity(returnedUbicacionDTO);
        assertUbicacionUpdatableFieldsEquals(returnedUbicacion, getPersistedUbicacion(returnedUbicacion));

        insertedUbicacion = returnedUbicacion;
    }

    @Test
    @Transactional
    void createUbicacionWithExistingId() throws Exception {
        // Create the Ubicacion with an existing ID
        ubicacion.setId(1L);
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUbicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLatitudIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ubicacion.setLatitud(null);

        // Create the Ubicacion, which fails.
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        restUbicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ubicacion.setLongitud(null);

        // Create the Ubicacion, which fails.
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        restUbicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ubicacion.setTimestamp(null);

        // Create the Ubicacion, which fails.
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        restUbicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUbicacions() throws Exception {
        // Initialize the database
        insertedUbicacion = ubicacionRepository.saveAndFlush(ubicacion);

        // Get all the ubicacionList
        restUbicacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ubicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @Test
    @Transactional
    void getUbicacion() throws Exception {
        // Initialize the database
        insertedUbicacion = ubicacionRepository.saveAndFlush(ubicacion);

        // Get the ubicacion
        restUbicacionMockMvc
            .perform(get(ENTITY_API_URL_ID, ubicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ubicacion.getId().intValue()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    void getNonExistingUbicacion() throws Exception {
        // Get the ubicacion
        restUbicacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUbicacion() throws Exception {
        // Initialize the database
        insertedUbicacion = ubicacionRepository.saveAndFlush(ubicacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ubicacion
        Ubicacion updatedUbicacion = ubicacionRepository.findById(ubicacion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUbicacion are not directly saved in db
        em.detach(updatedUbicacion);
        updatedUbicacion.latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD).timestamp(UPDATED_TIMESTAMP);
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(updatedUbicacion);

        restUbicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ubicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ubicacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUbicacionToMatchAllProperties(updatedUbicacion);
    }

    @Test
    @Transactional
    void putNonExistingUbicacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacion.setId(longCount.incrementAndGet());

        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ubicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ubicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUbicacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacion.setId(longCount.incrementAndGet());

        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ubicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUbicacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacion.setId(longCount.incrementAndGet());

        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUbicacionWithPatch() throws Exception {
        // Initialize the database
        insertedUbicacion = ubicacionRepository.saveAndFlush(ubicacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ubicacion using partial update
        Ubicacion partialUpdatedUbicacion = new Ubicacion();
        partialUpdatedUbicacion.setId(ubicacion.getId());

        partialUpdatedUbicacion.latitud(UPDATED_LATITUD).timestamp(UPDATED_TIMESTAMP);

        restUbicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUbicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUbicacion))
            )
            .andExpect(status().isOk());

        // Validate the Ubicacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUbicacionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUbicacion, ubicacion),
            getPersistedUbicacion(ubicacion)
        );
    }

    @Test
    @Transactional
    void fullUpdateUbicacionWithPatch() throws Exception {
        // Initialize the database
        insertedUbicacion = ubicacionRepository.saveAndFlush(ubicacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ubicacion using partial update
        Ubicacion partialUpdatedUbicacion = new Ubicacion();
        partialUpdatedUbicacion.setId(ubicacion.getId());

        partialUpdatedUbicacion.latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD).timestamp(UPDATED_TIMESTAMP);

        restUbicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUbicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUbicacion))
            )
            .andExpect(status().isOk());

        // Validate the Ubicacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUbicacionUpdatableFieldsEquals(partialUpdatedUbicacion, getPersistedUbicacion(partialUpdatedUbicacion));
    }

    @Test
    @Transactional
    void patchNonExistingUbicacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacion.setId(longCount.incrementAndGet());

        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ubicacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ubicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUbicacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacion.setId(longCount.incrementAndGet());

        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ubicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUbicacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacion.setId(longCount.incrementAndGet());

        // Create the Ubicacion
        UbicacionDTO ubicacionDTO = ubicacionMapper.toDto(ubicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ubicacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ubicacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUbicacion() throws Exception {
        // Initialize the database
        insertedUbicacion = ubicacionRepository.saveAndFlush(ubicacion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ubicacion
        restUbicacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, ubicacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ubicacionRepository.count();
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

    protected Ubicacion getPersistedUbicacion(Ubicacion ubicacion) {
        return ubicacionRepository.findById(ubicacion.getId()).orElseThrow();
    }

    protected void assertPersistedUbicacionToMatchAllProperties(Ubicacion expectedUbicacion) {
        assertUbicacionAllPropertiesEquals(expectedUbicacion, getPersistedUbicacion(expectedUbicacion));
    }

    protected void assertPersistedUbicacionToMatchUpdatableProperties(Ubicacion expectedUbicacion) {
        assertUbicacionAllUpdatablePropertiesEquals(expectedUbicacion, getPersistedUbicacion(expectedUbicacion));
    }
}
