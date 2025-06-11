package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.example.demo.dao.SpotAction;
import com.example.demo.dao.SpotBook;
import org.springframework.data.repository.CrudRepository;

public interface SpotActionRepository extends CrudRepository<SpotAction, Long> {

    List<SpotAction> findAll();
    Optional<SpotAction> findById(Long id);

}
