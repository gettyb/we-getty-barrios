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
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/bitcoin-prices")
public class BitcoinPriceController {

    @Autowired
    private IBitcoinPriceService iBitcoinPriceService;

    @GetMapping
    public ResponseEntity<Flux<BitcoinPrice>> retrieve(@RequestParam("page") int pageIndex,
                                                       @RequestParam("size") int pageSize){
        log.info("Retrieving All Bitcoin Prices");
        return ResponseEntity.ok(iBitcoinPriceService.retrieveAll(PageRequest.of(pageIndex, pageSize)));
    }
    @GetMapping("/{timestamp}")
    public ResponseEntity<Flux<BitcoinPrice>> retrieveByTimestamp(@PathVariable
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                LocalDateTime timestamp){
        log.info("Retrieving Bitcoin Price by Timestamp: "+ timestamp);
        return ResponseEntity.ok(iBitcoinPriceService.retrieveByTimestamp(timestamp,PageRequest.of(0, 1)));
    }
}
