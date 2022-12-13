package com.nbpconverter.NBPConverter.setup;

import com.nbpconverter.NBPConverter.DTO.ComputerDto;
import com.nbpconverter.NBPConverter.DTO.InvoiceDto;

import java.time.LocalDate;
import java.util.List;

public class Setup {

    public static List<ComputerDto> generateComputers() {

        ComputerDto first = new ComputerDto();
        first.setName("komputer 1");
        first.setCost_USD(345);

        ComputerDto second = new ComputerDto();
        second.setName("komputer 2");
        second.setCost_USD(543);

        ComputerDto third = new ComputerDto();
        third.setName("komputer 3");
        third.setCost_USD(346);

        return List.of(first, second, third);

    }

    public static InvoiceDto generateInvoice(List<ComputerDto> computerDtoList, LocalDate accountingDate) {

        InvoiceDto invoice = new InvoiceDto();
        invoice.setComputerList(computerDtoList);
        invoice.setAccountingDate(accountingDate);

        return invoice;
    }
}
