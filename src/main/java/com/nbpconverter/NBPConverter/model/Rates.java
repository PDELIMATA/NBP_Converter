package com.nbpconverter.NBPConverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rates {
    private String no;
    private LocalDate effectiveDate;
    private double bid;
    private double ask;
}
