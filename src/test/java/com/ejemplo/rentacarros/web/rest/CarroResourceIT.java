package com.ejemplo.rentacarros.web.rest;

import static com.ejemplo.rentacarros.domain.CarroAsserts.*;
import static com.ejemplo.rentacarros.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ejemplo.rentacarros.IntegrationTest;
import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.repository.CarroRepository;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.service.mapper.CarroMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link CarroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarroResourceIT {

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANIO = 1;
    private static final Integer UPDATED_ANIO = 2;

    private static final String DEFAULT_PLACAS = "AAAAAAAAAA";
    private static final String UPDATED_PLACAS = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_KILOMETRAJE = 1;
    private static final Integer UPDATED_KILOMETRAJE = 2;

    private static final String ENTITY_API_URL = "/api/carros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private CarroMapper carroMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarroMockMvc;

    private Carro carro;

    private Carro insertedCarro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carro createEntity() {
        return new Carro()
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .anio(DEFAULT_ANIO)
            .placas(DEFAULT_PLACAS)
            .color(DEFAULT_COLOR)
            .tipo(DEFAULT_TIPO)
            .estado(DEFAULT_ESTADO)
            .kilometraje(DEFAULT_KILOMETRAJE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carro createUpdatedEntity() {
        return new Carro()
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .anio(UPDATED_ANIO)
            .placas(UPDATED_PLACAS)
            .color(UPDATED_COLOR)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .kilometraje(UPDATED_KILOMETRAJE);
    }

    @BeforeEach
    void initTest() {
        carro = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCarro != null) {
            carroRepository.delete(insertedCarro);
            insertedCarro = null;
        }
    }

    @Test
    @Transactional
    void createCarro() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);
        var returnedCarroDTO = om.readValue(
            restCarroMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarroDTO.class
        );

        // Validate the Carro in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarro = carroMapper.toEntity(returnedCarroDTO);
        assertCarroUpdatableFieldsEquals(returnedCarro, getPersistedCarro(returnedCarro));

        insertedCarro = returnedCarro;
    }

    @Test
    @Transactional
    void createCarroWithExistingId() throws Exception {
        // Create the Carro with an existing ID
        carro.setId(1L);
        CarroDTO carroDTO = carroMapper.toDto(carro);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarcaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carro.setMarca(null);

        // Create the Carro, which fails.
        CarroDTO carroDTO = carroMapper.toDto(carro);

        restCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModeloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carro.setModelo(null);

        // Create the Carro, which fails.
        CarroDTO carroDTO = carroMapper.toDto(carro);

        restCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carro.setAnio(null);

        // Create the Carro, which fails.
        CarroDTO carroDTO = carroMapper.toDto(carro);

        restCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPlacasIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carro.setPlacas(null);

        // Create the Carro, which fails.
        CarroDTO carroDTO = carroMapper.toDto(carro);

        restCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carro.setEstado(null);

        // Create the Carro, which fails.
        CarroDTO carroDTO = carroMapper.toDto(carro);

        restCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarros() throws Exception {
        // Initialize the database
        insertedCarro = carroRepository.saveAndFlush(carro);

        // Get all the carroList
        restCarroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carro.getId().intValue())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].placas").value(hasItem(DEFAULT_PLACAS)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].kilometraje").value(hasItem(DEFAULT_KILOMETRAJE)));
    }

    @Test
    @Transactional
    void getCarro() throws Exception {
        // Initialize the database
        insertedCarro = carroRepository.saveAndFlush(carro);

        // Get the carro
        restCarroMockMvc
            .perform(get(ENTITY_API_URL_ID, carro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carro.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO))
            .andExpect(jsonPath("$.placas").value(DEFAULT_PLACAS))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.kilometraje").value(DEFAULT_KILOMETRAJE));
    }

    @Test
    @Transactional
    void getNonExistingCarro() throws Exception {
        // Get the carro
        restCarroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarro() throws Exception {
        // Initialize the database
        insertedCarro = carroRepository.saveAndFlush(carro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carro
        Carro updatedCarro = carroRepository.findById(carro.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCarro are not directly saved in db
        em.detach(updatedCarro);
        updatedCarro
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .anio(UPDATED_ANIO)
            .placas(UPDATED_PLACAS)
            .color(UPDATED_COLOR)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .kilometraje(UPDATED_KILOMETRAJE);
        CarroDTO carroDTO = carroMapper.toDto(updatedCarro);

        restCarroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carroDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO))
            )
            .andExpect(status().isOk());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarroToMatchAllProperties(updatedCarro);
    }

    @Test
    @Transactional
    void putNonExistingCarro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carro.setId(longCount.incrementAndGet());

        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carroDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carro.setId(longCount.incrementAndGet());

        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carro.setId(longCount.incrementAndGet());

        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarroWithPatch() throws Exception {
        // Initialize the database
        insertedCarro = carroRepository.saveAndFlush(carro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carro using partial update
        Carro partialUpdatedCarro = new Carro();
        partialUpdatedCarro.setId(carro.getId());

        partialUpdatedCarro.anio(UPDATED_ANIO).color(UPDATED_COLOR).tipo(UPDATED_TIPO).estado(UPDATED_ESTADO);

        restCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarro))
            )
            .andExpect(status().isOk());

        // Validate the Carro in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarroUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCarro, carro), getPersistedCarro(carro));
    }

    @Test
    @Transactional
    void fullUpdateCarroWithPatch() throws Exception {
        // Initialize the database
        insertedCarro = carroRepository.saveAndFlush(carro);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carro using partial update
        Carro partialUpdatedCarro = new Carro();
        partialUpdatedCarro.setId(carro.getId());

        partialUpdatedCarro
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .anio(UPDATED_ANIO)
            .placas(UPDATED_PLACAS)
            .color(UPDATED_COLOR)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .kilometraje(UPDATED_KILOMETRAJE);

        restCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarro.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarro))
            )
            .andExpect(status().isOk());

        // Validate the Carro in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarroUpdatableFieldsEquals(partialUpdatedCarro, getPersistedCarro(partialUpdatedCarro));
    }

    @Test
    @Transactional
    void patchNonExistingCarro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carro.setId(longCount.incrementAndGet());

        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carroDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carro.setId(longCount.incrementAndGet());

        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarro() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carro.setId(longCount.incrementAndGet());

        // Create the Carro
        CarroDTO carroDTO = carroMapper.toDto(carro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carro in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarro() throws Exception {
        // Initialize the database
        insertedCarro = carroRepository.saveAndFlush(carro);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carro
        restCarroMockMvc
            .perform(delete(ENTITY_API_URL_ID, carro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carroRepository.count();
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

    protected Carro getPersistedCarro(Carro carro) {
        return carroRepository.findById(carro.getId()).orElseThrow();
    }

    protected void assertPersistedCarroToMatchAllProperties(Carro expectedCarro) {
        assertCarroAllPropertiesEquals(expectedCarro, getPersistedCarro(expectedCarro));
    }

    protected void assertPersistedCarroToMatchUpdatableProperties(Carro expectedCarro) {
        assertCarroAllUpdatablePropertiesEquals(expectedCarro, getPersistedCarro(expectedCarro));
    }
}
