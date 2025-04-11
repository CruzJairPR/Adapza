package com.ejemplo.rentacarros.service;

import com.ejemplo.rentacarros.domain.Combustible;
import com.ejemplo.rentacarros.repository.CombustibleRepository;
import com.ejemplo.rentacarros.service.dto.CombustibleDTO;
import com.ejemplo.rentacarros.service.mapper.CombustibleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ejemplo.rentacarros.domain.Combustible}.
 */
@Service
@Transactional
public class CombustibleService {

    private static final Logger LOG = LoggerFactory.getLogger(CombustibleService.class);

    private final CombustibleRepository combustibleRepository;

    private final CombustibleMapper combustibleMapper;

    public CombustibleService(CombustibleRepository combustibleRepository, CombustibleMapper combustibleMapper) {
        this.combustibleRepository = combustibleRepository;
        this.combustibleMapper = combustibleMapper;
    }

    /**
     * Save a combustible.
     *
     * @param combustibleDTO the entity to save.
     * @return the persisted entity.
     */
    public CombustibleDTO save(CombustibleDTO combustibleDTO) {
        LOG.debug("Request to save Combustible : {}", combustibleDTO);
        Combustible combustible = combustibleMapper.toEntity(combustibleDTO);
        combustible = combustibleRepository.save(combustible);
        return combustibleMapper.toDto(combustible);
    }

    /**
     * Update a combustible.
     *
     * @param combustibleDTO the entity to save.
     * @return the persisted entity.
     */
    public CombustibleDTO update(CombustibleDTO combustibleDTO) {
        LOG.debug("Request to update Combustible : {}", combustibleDTO);
        Combustible combustible = combustibleMapper.toEntity(combustibleDTO);
        combustible = combustibleRepository.save(combustible);
        return combustibleMapper.toDto(combustible);
    }

    /**
     * Partially update a combustible.
     *
     * @param combustibleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CombustibleDTO> partialUpdate(CombustibleDTO combustibleDTO) {
        LOG.debug("Request to partially update Combustible : {}", combustibleDTO);

        return combustibleRepository
            .findById(combustibleDTO.getId())
            .map(existingCombustible -> {
                combustibleMapper.partialUpdate(existingCombustible, combustibleDTO);

                return existingCombustible;
            })
            .map(combustibleRepository::save)
            .map(combustibleMapper::toDto);
    }

    /**
     * Get all the combustibles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CombustibleDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Combustibles");
        return combustibleRepository.findAll(pageable).map(combustibleMapper::toDto);
    }

    /**
     * Get one combustible by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CombustibleDTO> findOne(Long id) {
        LOG.debug("Request to get Combustible : {}", id);
        return combustibleRepository.findById(id).map(combustibleMapper::toDto);
    }

    /**
     * Delete the combustible by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Combustible : {}", id);
        combustibleRepository.deleteById(id);
    }
}
