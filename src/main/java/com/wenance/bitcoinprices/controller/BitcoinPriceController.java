package com.wenance.bitcoinprices.controller;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.service.IBitcoinPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/bitcoin-prices")
public class BitcoinPriceController {

    @Autowired
    private IBitcoinPriceService iBitcoinPriceService;

    //TODO Falta paginado
    @GetMapping
    public ResponseEntity<Flux<BitcoinPrice>> retrieve(){
        log.info("Retrieving All Bitcoin Prices");
        return ResponseEntity.ok(iBitcoinPriceService.retrieveAll());
    }
    @GetMapping("/{timestamp}")
    public ResponseEntity<Mono<BitcoinPrice>> retrieveByTimestamp(@PathVariable
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                LocalDateTime timestamp){
        log.info("Retrieving Bitcoin Price by Timestamp: "+ timestamp);
        return ResponseEntity.ok(iBitcoinPriceService.retrieveByTimestamp(timestamp));
    }
}
