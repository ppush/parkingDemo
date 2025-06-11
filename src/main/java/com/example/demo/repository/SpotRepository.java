package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotState;
import org.springframework.data.repository.CrudRepository;

public interface SpotRepository extends CrudRepository<Spot, Long> {

    List<Spot> findAll();

    List<Spot> findAllByState(SpotState state);
    List<Spot> findByState(SpotState state);
    List<Spot> findByStateAndId(SpotState state, Long id);

    Optional<Spot> findById(Long id);

}
