package com.nbpconverter.NBPConverter.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.nbpconverter.NBPConverter.model.Computer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComputerXmlDto {
    @JacksonXmlProperty(localName = "nazwa")
    private String name;
    @JacksonXmlProperty(localName = "data_księgowania")
    private String accountingDate;
    @JacksonXmlProperty(localName = "koszt_USD")
    private double usdPrice;
    @JacksonXmlProperty(localName = "koszt_PLN")
    private double plnPrice;

    public static ComputerXmlDto of(Computer computerModel) {
        String date = computerModel.getAccountingDate().toString();

        return ComputerXmlDto.builder()
                .name(computerModel.getName())
                .usdPrice(computerModel.getCostUSD())
                .plnPrice(computerModel.getCostPLN())
                .accountingDate(date)
                .build();
    }

    public static List<ComputerXmlDto> ofList(List<Computer> computerModelList) {

        List<ComputerXmlDto> computerXmlDtos = new ArrayList<>();

        for (Computer computerModel : computerModelList) {
            computerXmlDtos.add(ComputerXmlDto.of(computerModel));
        }

        return computerXmlDtos;
    }

}


