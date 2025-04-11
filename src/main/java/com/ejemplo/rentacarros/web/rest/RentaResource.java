package com.ejemplo.rentacarros.web.rest;

import com.ejemplo.rentacarros.repository.RentaRepository;
import com.ejemplo.rentacarros.service.RentaService;
import com.ejemplo.rentacarros.service.dto.RentaDTO;
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
 * REST controller for managing {@link com.ejemplo.rentacarros.domain.Renta}.
 */
@RestController
@RequestMapping("/api/rentas")
public class RentaResource {

    private static final Logger LOG = LoggerFactory.getLogger(RentaResource.class);

    private static final String ENTITY_NAME = "renta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RentaService rentaService;

    private final RentaRepository rentaRepository;

    public RentaResource(RentaService rentaService, RentaRepository rentaRepository) {
        this.rentaService = rentaService;
        this.rentaRepository = rentaRepository;
    }

    /**
     * {@code POST  /rentas} : Create a new renta.
     *
     * @param rentaDTO the rentaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentaDTO, or with status {@code 400 (Bad Request)} if the renta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RentaDTO> createRenta(@Valid @RequestBody RentaDTO rentaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Renta : {}", rentaDTO);
        if (rentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new renta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rentaDTO = rentaService.save(rentaDTO);
        return ResponseEntity.created(new URI("/api/rentas/" + rentaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rentaDTO.getId().toString()))
            .body(rentaDTO);
    }

    /**
     * {@code PUT  /rentas/:id} : Updates an existing renta.
     *
     * @param id the id of the rentaDTO to save.
     * @param rentaDTO the rentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentaDTO,
     * or with status {@code 400 (Bad Request)} if the rentaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RentaDTO> updateRenta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RentaDTO rentaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Renta : {}, {}", id, rentaDTO);
        if (rentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rentaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rentaDTO = rentaService.update(rentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rentaDTO.getId().toString()))
            .body(rentaDTO);
    }

    /**
     * {@code PATCH  /rentas/:id} : Partial updates given fields of an existing renta, field will ignore if it is null
     *
     * @param id the id of the rentaDTO to save.
     * @param rentaDTO the rentaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentaDTO,
     * or with status {@code 400 (Bad Request)} if the rentaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rentaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rentaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RentaDTO> partialUpdateRenta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RentaDTO rentaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Renta partially : {}, {}", id, rentaDTO);
        if (rentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rentaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RentaDTO> result = rentaService.partialUpdate(rentaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rentaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rentas} : get all the rentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rentas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RentaDTO>> getAllRentas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Rentas");
        Page<RentaDTO> page = rentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rentas/:id} : get the "id" renta.
     *
     * @param id the id of the rentaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RentaDTO> getRenta(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Renta : {}", id);
        Optional<RentaDTO> rentaDTO = rentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rentaDTO);
    }

    /**
     * {@code DELETE  /rentas/:id} : delete the "id" renta.
     *
     * @param id the id of the rentaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRenta(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Renta : {}", id);
        rentaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
