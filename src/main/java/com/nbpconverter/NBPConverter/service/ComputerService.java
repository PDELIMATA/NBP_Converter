package com.nbpconverter.NBPConverter.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nbpconverter.NBPConverter.DTO.ComputerDto;
import com.nbpconverter.NBPConverter.DTO.ComputerXmlDto;
import com.nbpconverter.NBPConverter.DTO.InvoiceDto;
import com.nbpconverter.NBPConverter.DTO.InvoiceXmlDto;
import com.nbpconverter.NBPConverter.model.Computer;
import com.nbpconverter.NBPConverter.model.Currency;
import com.nbpconverter.NBPConverter.repository.ComputerRepository;
import com.nbpconverter.NBPConverter.setup.Setup;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ComputerService {
    private static final String URL = "http://api.nbp.pl/api/exchangerates/rates/c/usd/%s?format=json";
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    private final RestTemplate restTemplate;
    private final ComputerRepository computerRepository;
    private final ModelMapper modelMapper;

    public double getExchangeRateFromDate(LocalDate date) {
        String url = String.format(URL, date);
        return Objects.requireNonNull(restTemplate.getForObject(url, Currency.class)).getRates().get(0).getAsk();
    }


    public void saveComputers(InvoiceDto invoiceDto) {
        if (invoiceDto.getComputerList() != null) {
            for (ComputerDto computerDto : invoiceDto.getComputerList()) {
                Computer computer = modelMapper.map(computerDto, Computer.class);
                computer.setAccountingDate(invoiceDto.getAccountingDate());
                double exchangeRate = getExchangeRateFromDate(invoiceDto.getAccountingDate());
                computer.setCostPLN(round((computerDto.getCostUSD() * exchangeRate), 2));
                computer.setCostUSD(round(computerDto.getCostUSD(), 2));
                computerRepository.save(computer);
            }
        }
    }

    private void printXml(List<Computer> computerList) throws IOException {
        List<ComputerXmlDto> computerXmlList = ComputerXmlDto.ofList(computerList);
        InvoiceXmlDto invoiceDto = InvoiceXmlDto.builder().computerList(computerXmlList).build();
        XML_MAPPER.writeValue(new File("invoice.xml"), invoiceDto);
    }

    public void printComputerInvoice() {
        List<Computer> computers = Optional.of(computerRepository.findAll()).orElse(new ArrayList<>());
        try {
            printXml(computers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ComputerDto> getAllComputers() {
        List<Computer> computers = computerRepository.findAll();
        List<ComputerDto> computerDtos = computers.stream().map(computer -> modelMapper.map(computer, ComputerDto.class)).toList();
        return computerDtos;
    }

    public List<ComputerDto> getAllComputersSortedByName(String direction) {
        List<Computer> collect = new ArrayList<>();

        return getComputersSorted(direction, collect, Comparator.comparing(Computer::getName));
    }

    public List<ComputerDto> getAllComputersSortedByDate(String direction) {
        List<Computer> collect = new ArrayList<>();

        return getComputersSorted(direction, collect, Comparator.comparing(Computer::getAccountingDate));
    }

    private List<ComputerDto> getComputersSorted(String direction, List<Computer> collect, Comparator<Computer> comparing) {
        if (direction.equals("desc")) {
            collect = computerRepository.findAll().stream().sorted(comparing.reversed()).toList();
        } else if (direction.equals("asc")) {
            collect = computerRepository.findAll().stream().sorted(comparing).toList();
        }
        return collect.stream().map(computer -> modelMapper.map(computer, ComputerDto.class)).toList();
    }

    public List<ComputerDto> findComputersByName(String keyword) {
        List<Computer> computers = computerRepository.findComputersByName(keyword);
        return computers.stream().map(computer -> modelMapper.map(computer, ComputerDto.class)).toList();
    }

    public List<ComputerDto> findComputersByAccountingDate(LocalDate date) {
        List<Computer> computers = computerRepository.findComputersByAccounting_date(date);
        return computers.stream().map(computer -> modelMapper.map(computer, ComputerDto.class)).toList();
    }

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    @PostConstruct
    public void addComputersToDbOnStartProgramme() {
        List<ComputerDto> computerDtos = Setup.generateComputers();
        InvoiceDto invoice03 = Setup.generateInvoice(computerDtos, LocalDate.of(2022, Month.JANUARY, 03));
        InvoiceDto invoice10 = Setup.generateInvoice(computerDtos, LocalDate.of(2022, Month.JANUARY, 10));
        saveComputers(invoice03);
        saveComputers(invoice10);

    }

}
