package com.ejemplo.rentacarros.web.rest;

import com.ejemplo.rentacarros.repository.MantenimientoRepository;
import com.ejemplo.rentacarros.service.MantenimientoService;
import com.ejemplo.rentacarros.service.dto.MantenimientoDTO;
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
 * REST controller for managing {@link com.ejemplo.rentacarros.domain.Mantenimiento}.
 */
@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoResource {

    private static final Logger LOG = LoggerFactory.getLogger(MantenimientoResource.class);

    private static final String ENTITY_NAME = "mantenimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MantenimientoService mantenimientoService;

    private final MantenimientoRepository mantenimientoRepository;

    public MantenimientoResource(MantenimientoService mantenimientoService, MantenimientoRepository mantenimientoRepository) {
        this.mantenimientoService = mantenimientoService;
        this.mantenimientoRepository = mantenimientoRepository;
    }

    /**
     * {@code POST  /mantenimientos} : Create a new mantenimiento.
     *
     * @param mantenimientoDTO the mantenimientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mantenimientoDTO, or with status {@code 400 (Bad Request)} if the mantenimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MantenimientoDTO> createMantenimiento(@Valid @RequestBody MantenimientoDTO mantenimientoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Mantenimiento : {}", mantenimientoDTO);
        if (mantenimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new mantenimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mantenimientoDTO = mantenimientoService.save(mantenimientoDTO);
        return ResponseEntity.created(new URI("/api/mantenimientos/" + mantenimientoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, mantenimientoDTO.getId().toString()))
            .body(mantenimientoDTO);
    }

    /**
     * {@code PUT  /mantenimientos/:id} : Updates an existing mantenimiento.
     *
     * @param id the id of the mantenimientoDTO to save.
     * @param mantenimientoDTO the mantenimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mantenimientoDTO,
     * or with status {@code 400 (Bad Request)} if the mantenimientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mantenimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> updateMantenimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MantenimientoDTO mantenimientoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Mantenimiento : {}, {}", id, mantenimientoDTO);
        if (mantenimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mantenimientoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mantenimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mantenimientoDTO = mantenimientoService.update(mantenimientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mantenimientoDTO.getId().toString()))
            .body(mantenimientoDTO);
    }

    /**
     * {@code PATCH  /mantenimientos/:id} : Partial updates given fields of an existing mantenimiento, field will ignore if it is null
     *
     * @param id the id of the mantenimientoDTO to save.
     * @param mantenimientoDTO the mantenimientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mantenimientoDTO,
     * or with status {@code 400 (Bad Request)} if the mantenimientoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mantenimientoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mantenimientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MantenimientoDTO> partialUpdateMantenimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MantenimientoDTO mantenimientoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Mantenimiento partially : {}, {}", id, mantenimientoDTO);
        if (mantenimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mantenimientoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mantenimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MantenimientoDTO> result = mantenimientoService.partialUpdate(mantenimientoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mantenimientoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mantenimientos} : get all the mantenimientos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mantenimientos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MantenimientoDTO>> getAllMantenimientos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Mantenimientos");
        Page<MantenimientoDTO> page = mantenimientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mantenimientos/:id} : get the "id" mantenimiento.
     *
     * @param id the id of the mantenimientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mantenimientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoDTO> getMantenimiento(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Mantenimiento : {}", id);
        Optional<MantenimientoDTO> mantenimientoDTO = mantenimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mantenimientoDTO);
    }

    /**
     * {@code DELETE  /mantenimientos/:id} : delete the "id" mantenimiento.
     *
     * @param id the id of the mantenimientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMantenimiento(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Mantenimiento : {}", id);
        mantenimientoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
