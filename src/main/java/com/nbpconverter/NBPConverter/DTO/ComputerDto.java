package com.nbpconverter.NBPConverter.DTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ComputerDto implements Serializable {
    private String name;
    private LocalDate accountingDate;
    private double costUSD;
    private double costPLN;


}