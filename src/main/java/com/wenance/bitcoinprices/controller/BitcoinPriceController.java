package com.wenance.bitcoinprices.controller;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.service.IBitcoinPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bitcoin-prices")
public class BitcoinPriceController {

    @Autowired
    private IBitcoinPriceService iBitcoinPriceService;

    @GetMapping
    //TODO falta permitir que no envie nada y tome el default
    public ResponseEntity<Flux<BitcoinPrice>> retrieve(@RequestParam("page") int pageIndex,
                                                       @RequestParam("size") int pageSize){
        log.info("Retrieving All Bitcoin Prices with page {} and size {}", pageIndex, pageSize);
        return ResponseEntity.ok(iBitcoinPriceService.retrieveAll(PageRequest.of(pageIndex, pageSize)));
    }
    @GetMapping("/{timestamp}")
    public ResponseEntity<Flux<BitcoinPrice>> retrieveByTimestamp(@PathVariable
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                LocalDateTime timestamp){
        log.info("Retrieving Bitcoin Price by Timestamp: "+ timestamp.format(DateTimeFormatter.ISO_DATE_TIME));
        //TODO revisar paginación
        return ResponseEntity.ok(iBitcoinPriceService.retrieveByTimestamp(timestamp,PageRequest.of(0, 1)));
    }

    @GetMapping("/avg")
    public ResponseEntity<Mono<BigDecimal>> avg(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       List<LocalDateTime> timestamp){
        log.info("Retrieving Avg Bitcoin Price between Timestamps: {}", timestamp);
        return ResponseEntity.ok(iBitcoinPriceService.avg(timestamp));
    }
}
