package com.nbpconverter.NBPConverter.repository;

import com.nbpconverter.NBPConverter.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    @Query("select c from Computer c where c.name LIKE %?1%")
    List<Computer> findComputersByName(String name);

    @Query("select c from Computer c where c.accountingDate = ?1")
    List<Computer> findComputersByAccounting_date(LocalDate accounting);
}
