package com.example.demo.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.example.demo.dao.SpotBook;
import org.springframework.data.repository.CrudRepository;

public interface SpotBookRepository extends CrudRepository<SpotBook, Long> {

    List<SpotBook> findAll();
    long countSpotBookByTimeFromBetweenOrTimeToBetween(Timestamp from,
                                                       Timestamp to,
                                                       Timestamp timeFrom,
                                                       Timestamp timeTo);
    Optional<SpotBook> findById(Long id);

    Optional<SpotBook> getById(Long id);
}
