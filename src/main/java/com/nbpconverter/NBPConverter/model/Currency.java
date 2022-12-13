package com.nbpconverter.NBPConverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private String table;
    private String currency;
    private String code;
    private List<Rates> rates;

}
