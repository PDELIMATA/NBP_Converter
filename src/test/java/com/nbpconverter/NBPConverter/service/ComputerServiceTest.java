package com.nbpconverter.NBPConverter.service;

import com.nbpconverter.NBPConverter.DTO.ComputerDto;
import com.nbpconverter.NBPConverter.DTO.InvoiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ComputerServiceTest {


    private final ComputerService computerServiceMock = mock(ComputerService.class);


    private List<ComputerDto> createComputersDtoListToTest() {

        ComputerDto first = new ComputerDto();
        first.setName("komputer 1");
        first.setCostUSD(345);

        ComputerDto second = new ComputerDto();
        second.setName("komputer 2");
        second.setCostUSD(543);

        ComputerDto third = new ComputerDto();
        third.setName("komputer 3");
        third.setCostUSD(346);

        return List.of(first, second, third);

    }

    public InvoiceDto generateInvoiceToTest(List<ComputerDto> computerList, LocalDate accountingDate) {

        InvoiceDto invoice = new InvoiceDto();
        invoice.setComputerList(computerList);
        invoice.setAccountingDate(accountingDate);

        return invoice;
    }

    @Test
    void testIfReturnAllComputers() {
        //given
        List<ComputerDto> computersListToTest = createComputersDtoListToTest();
        when(computerServiceMock.getAllComputers()).thenReturn(computersListToTest);

        //when
        List<ComputerDto> allComputers = computerServiceMock.getAllComputers();

        //then
        assertThat(allComputers).hasSize(3)
                .contains(computersListToTest.get(0), computersListToTest.get(1), computersListToTest.get(2));
    }

    @Test
    void testIfConvertProperly() {

        //given
        List<ComputerDto> computersListToTest = createComputersDtoListToTest();
        when(computerServiceMock.getExchangeRateFromDate(LocalDate.of(2022, Month.JANUARY, 03))).thenReturn(4.1006087);
        when(computerServiceMock.getExchangeRateFromDate(LocalDate.of(2022, Month.JANUARY, 10))).thenReturn(4.05350725);

        //when
        InvoiceDto invoice03 = generateInvoiceToTest(computersListToTest, LocalDate.of(2022, Month.JANUARY, 03));
        InvoiceDto invoice10 = generateInvoiceToTest(computersListToTest, LocalDate.of(2022, Month.JANUARY, 10));

        double exchnageFrom03 = computerServiceMock.getExchangeRateFromDate(invoice03.getAccountingDate());
        double exchangeFrom10 = computerServiceMock.getExchangeRateFromDate(invoice10.getAccountingDate());


        //then
        assertThat(ComputerService.round(invoice03.getComputerList().get(0).getCostUSD() * exchnageFrom03, 2)).isEqualTo(1414.71);
        assertThat(ComputerService.round(invoice10.getComputerList().get(0).getCostUSD() * exchangeFrom10, 2)).isEqualTo(1398.46);

    }

}
