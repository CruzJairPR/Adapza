package com.ejemplo.rentacarros.service;

import com.ejemplo.rentacarros.domain.Renta;
import com.ejemplo.rentacarros.repository.RentaRepository;
import com.ejemplo.rentacarros.service.dto.RentaDTO;
import com.ejemplo.rentacarros.service.mapper.RentaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ejemplo.rentacarros.domain.Renta}.
 */
@Service
@Transactional
public class RentaService {

    private static final Logger LOG = LoggerFactory.getLogger(RentaService.class);

    private final RentaRepository rentaRepository;

    private final RentaMapper rentaMapper;

    public RentaService(RentaRepository rentaRepository, RentaMapper rentaMapper) {
        this.rentaRepository = rentaRepository;
        this.rentaMapper = rentaMapper;
    }

    /**
     * Save a renta.
     *
     * @param rentaDTO the entity to save.
     * @return the persisted entity.
     */
    public RentaDTO save(RentaDTO rentaDTO) {
        LOG.debug("Request to save Renta : {}", rentaDTO);
        Renta renta = rentaMapper.toEntity(rentaDTO);
        renta = rentaRepository.save(renta);
        return rentaMapper.toDto(renta);
    }

    /**
     * Update a renta.
     *
     * @param rentaDTO the entity to save.
     * @return the persisted entity.
     */
    public RentaDTO update(RentaDTO rentaDTO) {
        LOG.debug("Request to update Renta : {}", rentaDTO);
        Renta renta = rentaMapper.toEntity(rentaDTO);
        renta = rentaRepository.save(renta);
        return rentaMapper.toDto(renta);
    }

    /**
     * Partially update a renta.
     *
     * @param rentaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RentaDTO> partialUpdate(RentaDTO rentaDTO) {
        LOG.debug("Request to partially update Renta : {}", rentaDTO);

        return rentaRepository
            .findById(rentaDTO.getId())
            .map(existingRenta -> {
                rentaMapper.partialUpdate(existingRenta, rentaDTO);

                return existingRenta;
            })
            .map(rentaRepository::save)
            .map(rentaMapper::toDto);
    }

    /**
     * Get all the rentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RentaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Rentas");
        return rentaRepository.findAll(pageable).map(rentaMapper::toDto);
    }

    /**
     * Get one renta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RentaDTO> findOne(Long id) {
        LOG.debug("Request to get Renta : {}", id);
        return rentaRepository.findById(id).map(rentaMapper::toDto);
    }

    /**
     * Delete the renta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Renta : {}", id);
        rentaRepository.deleteById(id);
    }
}
