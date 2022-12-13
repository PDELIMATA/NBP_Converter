package com.nbpconverter.NBPConverter.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto {

    private LocalDate accountingDate;
    private List<ComputerDto> computerList;

    public void addComputer(ComputerDto computerDto) {
        if (computerList == null) {
            computerList = new ArrayList<>();
        }
        this.computerList.add(computerDto);
    }
}
