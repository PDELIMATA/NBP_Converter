package com.nbpconverter.NBPConverter.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "faktura")
public class InvoiceXmlDto {

    @JacksonXmlProperty(localName = "komputer")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<ComputerXmlDto> computerList;

}
