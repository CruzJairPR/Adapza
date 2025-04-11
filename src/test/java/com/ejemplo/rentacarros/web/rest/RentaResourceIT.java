package com.ejemplo.rentacarros.web.rest;

import static com.ejemplo.rentacarros.domain.RentaAsserts.*;
import static com.ejemplo.rentacarros.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ejemplo.rentacarros.web.rest.TestUtil.sameInstant;
import static com.ejemplo.rentacarros.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ejemplo.rentacarros.IntegrationTest;
import com.ejemplo.rentacarros.domain.Renta;
import com.ejemplo.rentacarros.repository.RentaRepository;
import com.ejemplo.rentacarros.service.dto.RentaDTO;
import com.ejemplo.rentacarros.service.mapper.RentaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link RentaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RentaResourceIT {

    private static final ZonedDateTime DEFAULT_FECHA_INICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_PRECIO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_TOTAL = new BigDecimal(2);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rentas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RentaRepository rentaRepository;

    @Autowired
    private RentaMapper rentaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentaMockMvc;

    private Renta renta;

    private Renta insertedRenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Renta createEntity() {
        return new Renta()
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .precioTotal(DEFAULT_PRECIO_TOTAL)
            .estado(DEFAULT_ESTADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Renta createUpdatedEntity() {
        return new Renta()
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .estado(UPDATED_ESTADO);
    }

    @BeforeEach
    void initTest() {
        renta = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRenta != null) {
            rentaRepository.delete(insertedRenta);
            insertedRenta = null;
        }
    }

    @Test
    @Transactional
    void createRenta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);
        var returnedRentaDTO = om.readValue(
            restRentaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RentaDTO.class
        );

        // Validate the Renta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRenta = rentaMapper.toEntity(returnedRentaDTO);
        assertRentaUpdatableFieldsEquals(returnedRenta, getPersistedRenta(returnedRenta));

        insertedRenta = returnedRenta;
    }

    @Test
    @Transactional
    void createRentaWithExistingId() throws Exception {
        // Create the Renta with an existing ID
        renta.setId(1L);
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        renta.setFechaInicio(null);

        // Create the Renta, which fails.
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        restRentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrecioTotalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        renta.setPrecioTotal(null);

        // Create the Renta, which fails.
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        restRentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        renta.setEstado(null);

        // Create the Renta, which fails.
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        restRentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRentas() throws Exception {
        // Initialize the database
        insertedRenta = rentaRepository.saveAndFlush(renta);

        // Get all the rentaList
        restRentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(renta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(sameInstant(DEFAULT_FECHA_INICIO))))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(sameInstant(DEFAULT_FECHA_FIN))))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(sameNumber(DEFAULT_PRECIO_TOTAL))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }

    @Test
    @Transactional
    void getRenta() throws Exception {
        // Initialize the database
        insertedRenta = rentaRepository.saveAndFlush(renta);

        // Get the renta
        restRentaMockMvc
            .perform(get(ENTITY_API_URL_ID, renta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(renta.getId().intValue()))
            .andExpect(jsonPath("$.fechaInicio").value(sameInstant(DEFAULT_FECHA_INICIO)))
            .andExpect(jsonPath("$.fechaFin").value(sameInstant(DEFAULT_FECHA_FIN)))
            .andExpect(jsonPath("$.precioTotal").value(sameNumber(DEFAULT_PRECIO_TOTAL)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingRenta() throws Exception {
        // Get the renta
        restRentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRenta() throws Exception {
        // Initialize the database
        insertedRenta = rentaRepository.saveAndFlush(renta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the renta
        Renta updatedRenta = rentaRepository.findById(renta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRenta are not directly saved in db
        em.detach(updatedRenta);
        updatedRenta.fechaInicio(UPDATED_FECHA_INICIO).fechaFin(UPDATED_FECHA_FIN).precioTotal(UPDATED_PRECIO_TOTAL).estado(UPDATED_ESTADO);
        RentaDTO rentaDTO = rentaMapper.toDto(updatedRenta);

        restRentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRentaToMatchAllProperties(updatedRenta);
    }

    @Test
    @Transactional
    void putNonExistingRenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        renta.setId(longCount.incrementAndGet());

        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        renta.setId(longCount.incrementAndGet());

        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        renta.setId(longCount.incrementAndGet());

        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRentaWithPatch() throws Exception {
        // Initialize the database
        insertedRenta = rentaRepository.saveAndFlush(renta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the renta using partial update
        Renta partialUpdatedRenta = new Renta();
        partialUpdatedRenta.setId(renta.getId());

        partialUpdatedRenta.fechaInicio(UPDATED_FECHA_INICIO).fechaFin(UPDATED_FECHA_FIN);

        restRentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRenta))
            )
            .andExpect(status().isOk());

        // Validate the Renta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRenta, renta), getPersistedRenta(renta));
    }

    @Test
    @Transactional
    void fullUpdateRentaWithPatch() throws Exception {
        // Initialize the database
        insertedRenta = rentaRepository.saveAndFlush(renta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the renta using partial update
        Renta partialUpdatedRenta = new Renta();
        partialUpdatedRenta.setId(renta.getId());

        partialUpdatedRenta
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .estado(UPDATED_ESTADO);

        restRentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRenta))
            )
            .andExpect(status().isOk());

        // Validate the Renta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentaUpdatableFieldsEquals(partialUpdatedRenta, getPersistedRenta(partialUpdatedRenta));
    }

    @Test
    @Transactional
    void patchNonExistingRenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        renta.setId(longCount.incrementAndGet());

        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rentaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        renta.setId(longCount.incrementAndGet());

        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRenta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        renta.setId(longCount.incrementAndGet());

        // Create the Renta
        RentaDTO rentaDTO = rentaMapper.toDto(renta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rentaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Renta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRenta() throws Exception {
        // Initialize the database
        insertedRenta = rentaRepository.saveAndFlush(renta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the renta
        restRentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, renta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rentaRepository.count();
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

    protected Renta getPersistedRenta(Renta renta) {
        return rentaRepository.findById(renta.getId()).orElseThrow();
    }

    protected void assertPersistedRentaToMatchAllProperties(Renta expectedRenta) {
        assertRentaAllPropertiesEquals(expectedRenta, getPersistedRenta(expectedRenta));
    }

    protected void assertPersistedRentaToMatchUpdatableProperties(Renta expectedRenta) {
        assertRentaAllUpdatablePropertiesEquals(expectedRenta, getPersistedRenta(expectedRenta));
    }
}
