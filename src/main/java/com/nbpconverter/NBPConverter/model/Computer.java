package com.nbpconverter.NBPConverter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "nazwa")
    public String name;
    @Column(name = "data_ksiegowania")
    public LocalDate accountingDate;
    @Column(name = "koszt_USD")
    public double costUSD;
    @Column(name = "koszt_PLN")
    public double costPLN;
}
