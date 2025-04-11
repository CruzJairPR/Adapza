package com.ejemplo.rentacarros.web.rest;

import com.ejemplo.rentacarros.repository.CarroRepository;
import com.ejemplo.rentacarros.service.CarroService;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ejemplo.rentacarros.domain.Carro}.
 */
@RestController
@RequestMapping("/api/carros")
public class CarroResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarroResource.class);

    private static final String ENTITY_NAME = "carro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarroService carroService;

    private final CarroRepository carroRepository;

    public CarroResource(CarroService carroService, CarroRepository carroRepository) {
        this.carroService = carroService;
        this.carroRepository = carroRepository;
    }

    /**
     * {@code POST  /carros} : Create a new carro.
     *
     * @param carroDTO the carroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carroDTO, or with status {@code 400 (Bad Request)} if the carro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarroDTO> createCarro(@Valid @RequestBody CarroDTO carroDTO) throws URISyntaxException {
        LOG.debug("REST request to save Carro : {}", carroDTO);
        if (carroDTO.getId() != null) {
            throw new BadRequestAlertException("A new carro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carroDTO = carroService.save(carroDTO);
        return ResponseEntity.created(new URI("/api/carros/" + carroDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carroDTO.getId().toString()))
            .body(carroDTO);
    }

    /**
     * {@code PUT  /carros/:id} : Updates an existing carro.
     *
     * @param id the id of the carroDTO to save.
     * @param carroDTO the carroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carroDTO,
     * or with status {@code 400 (Bad Request)} if the carroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> updateCarro(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarroDTO carroDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Carro : {}, {}", id, carroDTO);
        if (carroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carroDTO = carroService.update(carroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carroDTO.getId().toString()))
            .body(carroDTO);
    }

    /**
     * {@code PATCH  /carros/:id} : Partial updates given fields of an existing carro, field will ignore if it is null
     *
     * @param id the id of the carroDTO to save.
     * @param carroDTO the carroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carroDTO,
     * or with status {@code 400 (Bad Request)} if the carroDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carroDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarroDTO> partialUpdateCarro(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarroDTO carroDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Carro partially : {}, {}", id, carroDTO);
        if (carroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarroDTO> result = carroService.partialUpdate(carroDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carroDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carros} : get all the carros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carros in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarroDTO>> getAllCarros(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Carros");
        Page<CarroDTO> page = carroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carros/:id} : get the "id" carro.
     *
     * @param id the id of the carroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> getCarro(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Carro : {}", id);
        Optional<CarroDTO> carroDTO = carroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carroDTO);
    }

    /**
     * {@code DELETE  /carros/:id} : delete the "id" carro.
     *
     * @param id the id of the carroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarro(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Carro : {}", id);
        carroService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
