package com.nbpconverter.NBPConverter.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComputerDto implements Serializable {
    private String name;
    private LocalDate accounting_date;
    private double cost_USD;
    private double cost_PLN;


}