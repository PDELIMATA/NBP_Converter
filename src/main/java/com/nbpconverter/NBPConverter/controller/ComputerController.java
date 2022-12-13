package com.nbpconverter.NBPConverter.controller;

import com.nbpconverter.NBPConverter.DTO.ComputerDto;
import com.nbpconverter.NBPConverter.DTO.InvoiceDto;
import com.nbpconverter.NBPConverter.service.ComputerService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class ComputerController {

    private final ComputerService computerService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping("/")
    public String getAllComputers(Model model) {
        model.addAttribute("computers", computerService.getAllComputers());
        return "computers";
    }

    @GetMapping("/sorted/name")
    public String getSortedComputersByName(Model model, @Param("direction") String direction) {
        model.addAttribute("computers", computerService.getAllComputersSortedByName(direction));
        return "computers";
    }

    @GetMapping("/sorted/date")
    public String getSortedComputersByDate(Model model, @Param("direction") String direction) {
        model.addAttribute("computers", computerService.getAllComputersSortedByDate(direction));
        return "computers";
    }

    @GetMapping(value = "/keyword")
    public String filteredComputersByKeyword(Model model, @Param("keyword") String keyword) {
        model.addAttribute("computers", computerService.findComputersByName(keyword));
        model.addAttribute("keyword", keyword);
        return "computers";
    }

    @GetMapping(value = "/accounting")
    public String filteredComputersByAccountingDate(Model model, @Param("date") LocalDate date) {
        model.addAttribute("computers", computerService.findComputersByAccountingDate(date));
        model.addAttribute("date", date);
        return "computers";
    }

    @GetMapping("/computers")
    public String addComputersForm(Model model) {
        model.addAttribute("invoice", new InvoiceDto());
        return "add-computers";
    }

    @PostMapping(value = "/computers", params = {"addComputer"})
    public String addComputer(@ModelAttribute("invoice") InvoiceDto invoiceDto) {
        invoiceDto.addComputer(new ComputerDto());
        return "add-computers";
    }

    @PostMapping("/computers")
    public String saveComputersAndPrintInvoice(@ModelAttribute("invoice") InvoiceDto invoiceDto) {
        computerService.saveComputers(invoiceDto);
        computerService.printComputerInvoice();
        return "redirect:/";
    }

}
