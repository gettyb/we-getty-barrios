package com.wenance.bitcoinprices.controller;

import com.wenance.bitcoinprices.BaseTests;
import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.service.BitcoinPriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BitcoinPriceControllerTest extends BaseTests{

    @MockBean
    private BitcoinPriceServiceImpl bitcoinPriceService;

    @Autowired
    private BitcoinPriceController bitcoinPriceController;

    @LocalServerPort
    int randomServerPort;

    @Test
    public void whenRetrieveAll_shouldReturnOk(){
        //Given
        BitcoinPrice expectedPrice= BitcoinPrice.builder().build();
        //When
        Mockito.when(bitcoinPriceService.retrieveAll(any(Pageable.class))).thenReturn(Flux.just(expectedPrice));
        //Then
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:"+randomServerPort)
                .build()
                .get()
                .uri("/bitcoin-prices")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(BitcoinPrice.class);

        verify(bitcoinPriceService, times(1)).retrieveAll(PageRequest.of(0,10));
    }


    @Test
    public void whenRetrieveExistingTimestamp_shouldReturnOk(){
        //Given
        LocalDateTime timestamp=LocalDateTime.now();
        String timestampStr= timestamp.format(DateTimeFormatter.ISO_DATE_TIME);
        BitcoinPrice expectedPrice= BitcoinPrice.builder().build();
        //When
        Mockito.when(bitcoinPriceService.retrieveByTimestamp(any(LocalDateTime.class),any(Pageable.class))).thenReturn(Flux.just(expectedPrice));
        //Then
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:"+randomServerPort)
                .build()
                .get()
                .uri("/bitcoin-prices/{timestamp}",timestampStr)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(BitcoinPrice.class);

        verify(bitcoinPriceService, times(1)).retrieveByTimestamp(timestamp,PageRequest.of(0,1));
    }

    @Test
    public void whenRetrieveUnexistingTimestamp_shouldReturnNotFound(){
        //Given
        String timestamp= LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        //When
        Mockito.when(bitcoinPriceService.retrieveByTimestamp(any(LocalDateTime.class),any(Pageable.class))).thenReturn(Flux.empty());
        //Then
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:"+randomServerPort)
                .build()
                .get()
                .uri("/bitcoin-prices/{timestamp}",timestamp)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void whenAvgWithSeveralTimestamps_shouldReturnValue(){
        //Given
        DateTimeFormatter formatter= DateTimeFormatter.ISO_DATE_TIME;
        List<LocalDateTime> dateTimeList= Arrays.asList(LocalDateTime.now(),LocalDateTime.now().plusSeconds(10));
        String queryParams= dateTimeList.stream().map(t -> "timestamp="+t.format(formatter)).collect(Collectors.joining("&"));
        //When
        Mockito.when(bitcoinPriceService.avg(dateTimeList)).thenReturn(Mono.just(BigDecimal.ONE));
        //Then
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:"+randomServerPort)
                .build()
                .get()
                .uri("/bitcoin-prices/avg?"+queryParams)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BigDecimal.class);
        verify(bitcoinPriceService, times(1)).avg(dateTimeList);
    }
}
