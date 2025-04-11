package com.ejemplo.rentacarros.web.rest;

import static com.ejemplo.rentacarros.domain.MantenimientoAsserts.*;
import static com.ejemplo.rentacarros.web.rest.TestUtil.createUpdateProxyForBean;
import static com.ejemplo.rentacarros.web.rest.TestUtil.sameInstant;
import static com.ejemplo.rentacarros.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ejemplo.rentacarros.IntegrationTest;
import com.ejemplo.rentacarros.domain.Mantenimiento;
import com.ejemplo.rentacarros.repository.MantenimientoRepository;
import com.ejemplo.rentacarros.service.dto.MantenimientoDTO;
import com.ejemplo.rentacarros.service.mapper.MantenimientoMapper;
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
 * Integration tests for the {@link MantenimientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MantenimientoResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_COSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_COSTO = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/mantenimientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MantenimientoMapper mantenimientoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMantenimientoMockMvc;

    private Mantenimiento mantenimiento;

    private Mantenimiento insertedMantenimiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mantenimiento createEntity() {
        return new Mantenimiento().tipo(DEFAULT_TIPO).descripcion(DEFAULT_DESCRIPCION).fecha(DEFAULT_FECHA).costo(DEFAULT_COSTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mantenimiento createUpdatedEntity() {
        return new Mantenimiento().tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).fecha(UPDATED_FECHA).costo(UPDATED_COSTO);
    }

    @BeforeEach
    void initTest() {
        mantenimiento = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMantenimiento != null) {
            mantenimientoRepository.delete(insertedMantenimiento);
            insertedMantenimiento = null;
        }
    }

    @Test
    @Transactional
    void createMantenimiento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);
        var returnedMantenimientoDTO = om.readValue(
            restMantenimientoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mantenimientoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MantenimientoDTO.class
        );

        // Validate the Mantenimiento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMantenimiento = mantenimientoMapper.toEntity(returnedMantenimientoDTO);
        assertMantenimientoUpdatableFieldsEquals(returnedMantenimiento, getPersistedMantenimiento(returnedMantenimiento));

        insertedMantenimiento = returnedMantenimiento;
    }

    @Test
    @Transactional
    void createMantenimientoWithExistingId() throws Exception {
        // Create the Mantenimiento with an existing ID
        mantenimiento.setId(1L);
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMantenimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mantenimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mantenimiento.setTipo(null);

        // Create the Mantenimiento, which fails.
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        restMantenimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mantenimientoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mantenimiento.setFecha(null);

        // Create the Mantenimiento, which fails.
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        restMantenimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mantenimientoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMantenimientos() throws Exception {
        // Initialize the database
        insertedMantenimiento = mantenimientoRepository.saveAndFlush(mantenimiento);

        // Get all the mantenimientoList
        restMantenimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mantenimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(sameNumber(DEFAULT_COSTO))));
    }

    @Test
    @Transactional
    void getMantenimiento() throws Exception {
        // Initialize the database
        insertedMantenimiento = mantenimientoRepository.saveAndFlush(mantenimiento);

        // Get the mantenimiento
        restMantenimientoMockMvc
            .perform(get(ENTITY_API_URL_ID, mantenimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mantenimiento.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.costo").value(sameNumber(DEFAULT_COSTO)));
    }

    @Test
    @Transactional
    void getNonExistingMantenimiento() throws Exception {
        // Get the mantenimiento
        restMantenimientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMantenimiento() throws Exception {
        // Initialize the database
        insertedMantenimiento = mantenimientoRepository.saveAndFlush(mantenimiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mantenimiento
        Mantenimiento updatedMantenimiento = mantenimientoRepository.findById(mantenimiento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMantenimiento are not directly saved in db
        em.detach(updatedMantenimiento);
        updatedMantenimiento.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).fecha(UPDATED_FECHA).costo(UPDATED_COSTO);
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(updatedMantenimiento);

        restMantenimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mantenimientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mantenimientoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMantenimientoToMatchAllProperties(updatedMantenimiento);
    }

    @Test
    @Transactional
    void putNonExistingMantenimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mantenimiento.setId(longCount.incrementAndGet());

        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMantenimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mantenimientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mantenimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMantenimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mantenimiento.setId(longCount.incrementAndGet());

        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMantenimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mantenimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMantenimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mantenimiento.setId(longCount.incrementAndGet());

        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMantenimientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mantenimientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMantenimientoWithPatch() throws Exception {
        // Initialize the database
        insertedMantenimiento = mantenimientoRepository.saveAndFlush(mantenimiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mantenimiento using partial update
        Mantenimiento partialUpdatedMantenimiento = new Mantenimiento();
        partialUpdatedMantenimiento.setId(mantenimiento.getId());

        partialUpdatedMantenimiento.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).fecha(UPDATED_FECHA);

        restMantenimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMantenimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMantenimiento))
            )
            .andExpect(status().isOk());

        // Validate the Mantenimiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMantenimientoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMantenimiento, mantenimiento),
            getPersistedMantenimiento(mantenimiento)
        );
    }

    @Test
    @Transactional
    void fullUpdateMantenimientoWithPatch() throws Exception {
        // Initialize the database
        insertedMantenimiento = mantenimientoRepository.saveAndFlush(mantenimiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mantenimiento using partial update
        Mantenimiento partialUpdatedMantenimiento = new Mantenimiento();
        partialUpdatedMantenimiento.setId(mantenimiento.getId());

        partialUpdatedMantenimiento.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).fecha(UPDATED_FECHA).costo(UPDATED_COSTO);

        restMantenimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMantenimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMantenimiento))
            )
            .andExpect(status().isOk());

        // Validate the Mantenimiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMantenimientoUpdatableFieldsEquals(partialUpdatedMantenimiento, getPersistedMantenimiento(partialUpdatedMantenimiento));
    }

    @Test
    @Transactional
    void patchNonExistingMantenimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mantenimiento.setId(longCount.incrementAndGet());

        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMantenimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mantenimientoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mantenimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMantenimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mantenimiento.setId(longCount.incrementAndGet());

        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMantenimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mantenimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMantenimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mantenimiento.setId(longCount.incrementAndGet());

        // Create the Mantenimiento
        MantenimientoDTO mantenimientoDTO = mantenimientoMapper.toDto(mantenimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMantenimientoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mantenimientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mantenimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMantenimiento() throws Exception {
        // Initialize the database
        insertedMantenimiento = mantenimientoRepository.saveAndFlush(mantenimiento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mantenimiento
        restMantenimientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, mantenimiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mantenimientoRepository.count();
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

    protected Mantenimiento getPersistedMantenimiento(Mantenimiento mantenimiento) {
        return mantenimientoRepository.findById(mantenimiento.getId()).orElseThrow();
    }

    protected void assertPersistedMantenimientoToMatchAllProperties(Mantenimiento expectedMantenimiento) {
        assertMantenimientoAllPropertiesEquals(expectedMantenimiento, getPersistedMantenimiento(expectedMantenimiento));
    }

    protected void assertPersistedMantenimientoToMatchUpdatableProperties(Mantenimiento expectedMantenimiento) {
        assertMantenimientoAllUpdatablePropertiesEquals(expectedMantenimiento, getPersistedMantenimiento(expectedMantenimiento));
    }
}
