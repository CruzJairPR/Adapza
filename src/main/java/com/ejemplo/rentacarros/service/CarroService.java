package com.ejemplo.rentacarros.service;

import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.repository.CarroRepository;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.service.mapper.CarroMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ejemplo.rentacarros.domain.Carro}.
 */
@Service
@Transactional
public class CarroService {

    private static final Logger LOG = LoggerFactory.getLogger(CarroService.class);

    private final CarroRepository carroRepository;

    private final CarroMapper carroMapper;

    public CarroService(CarroRepository carroRepository, CarroMapper carroMapper) {
        this.carroRepository = carroRepository;
        this.carroMapper = carroMapper;
    }

    /**
     * Save a carro.
     *
     * @param carroDTO the entity to save.
     * @return the persisted entity.
     */
    public CarroDTO save(CarroDTO carroDTO) {
        LOG.debug("Request to save Carro : {}", carroDTO);
        Carro carro = carroMapper.toEntity(carroDTO);
        carro = carroRepository.save(carro);
        return carroMapper.toDto(carro);
    }

    /**
     * Update a carro.
     *
     * @param carroDTO the entity to save.
     * @return the persisted entity.
     */
    public CarroDTO update(CarroDTO carroDTO) {
        LOG.debug("Request to update Carro : {}", carroDTO);
        Carro carro = carroMapper.toEntity(carroDTO);
        carro = carroRepository.save(carro);
        return carroMapper.toDto(carro);
    }

    /**
     * Partially update a carro.
     *
     * @param carroDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CarroDTO> partialUpdate(CarroDTO carroDTO) {
        LOG.debug("Request to partially update Carro : {}", carroDTO);

        return carroRepository
            .findById(carroDTO.getId())
            .map(existingCarro -> {
                carroMapper.partialUpdate(existingCarro, carroDTO);

                return existingCarro;
            })
            .map(carroRepository::save)
            .map(carroMapper::toDto);
    }

    /**
     * Get all the carros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CarroDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Carros");
        return carroRepository.findAll(pageable).map(carroMapper::toDto);
    }

    /**
     * Get one carro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarroDTO> findOne(Long id) {
        LOG.debug("Request to get Carro : {}", id);
        return carroRepository.findById(id).map(carroMapper::toDto);
    }

    /**
     * Delete the carro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Carro : {}", id);
        carroRepository.deleteById(id);
    }
}
