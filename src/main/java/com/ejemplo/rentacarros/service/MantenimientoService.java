package com.ejemplo.rentacarros.service;

import com.ejemplo.rentacarros.domain.Mantenimiento;
import com.ejemplo.rentacarros.repository.MantenimientoRepository;
import com.ejemplo.rentacarros.service.dto.MantenimientoDTO;
import com.ejemplo.rentacarros.service.mapper.MantenimientoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ejemplo.rentacarros.domain.Mantenimiento}.
 */
@Service
@Transactional
public class MantenimientoService {

    private static final Logger LOG = LoggerFactory.getLogger(MantenimientoService.class);

    private final MantenimientoRepository mantenimientoRepository;

    private final MantenimientoMapper mantenimientoMapper;

    public MantenimientoService(MantenimientoRepository mantenimientoRepository, MantenimientoMapper mantenimientoMapper) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.mantenimientoMapper = mantenimientoMapper;
    }

    /**
     * Save a mantenimiento.
     *
     * @param mantenimientoDTO the entity to save.
     * @return the persisted entity.
     */
    public MantenimientoDTO save(MantenimientoDTO mantenimientoDTO) {
        LOG.debug("Request to save Mantenimiento : {}", mantenimientoDTO);
        Mantenimiento mantenimiento = mantenimientoMapper.toEntity(mantenimientoDTO);
        mantenimiento = mantenimientoRepository.save(mantenimiento);
        return mantenimientoMapper.toDto(mantenimiento);
    }

    /**
     * Update a mantenimiento.
     *
     * @param mantenimientoDTO the entity to save.
     * @return the persisted entity.
     */
    public MantenimientoDTO update(MantenimientoDTO mantenimientoDTO) {
        LOG.debug("Request to update Mantenimiento : {}", mantenimientoDTO);
        Mantenimiento mantenimiento = mantenimientoMapper.toEntity(mantenimientoDTO);
        mantenimiento = mantenimientoRepository.save(mantenimiento);
        return mantenimientoMapper.toDto(mantenimiento);
    }

    /**
     * Partially update a mantenimiento.
     *
     * @param mantenimientoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MantenimientoDTO> partialUpdate(MantenimientoDTO mantenimientoDTO) {
        LOG.debug("Request to partially update Mantenimiento : {}", mantenimientoDTO);

        return mantenimientoRepository
            .findById(mantenimientoDTO.getId())
            .map(existingMantenimiento -> {
                mantenimientoMapper.partialUpdate(existingMantenimiento, mantenimientoDTO);

                return existingMantenimiento;
            })
            .map(mantenimientoRepository::save)
            .map(mantenimientoMapper::toDto);
    }

    /**
     * Get all the mantenimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MantenimientoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Mantenimientos");
        return mantenimientoRepository.findAll(pageable).map(mantenimientoMapper::toDto);
    }

    /**
     * Get one mantenimiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MantenimientoDTO> findOne(Long id) {
        LOG.debug("Request to get Mantenimiento : {}", id);
        return mantenimientoRepository.findById(id).map(mantenimientoMapper::toDto);
    }

    /**
     * Delete the mantenimiento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Mantenimiento : {}", id);
        mantenimientoRepository.deleteById(id);
    }
}
