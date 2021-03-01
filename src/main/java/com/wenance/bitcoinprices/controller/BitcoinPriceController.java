package com.wenance.bitcoinprices.controller;

import com.wenance.bitcoinprices.exception.TimestampNotFound;
import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.service.IBitcoinPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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
    public Flux<BitcoinPrice> retrieve(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){
        log.info("Retrieving All Bitcoin Prices with page {} and size {}", page, size);
        return iBitcoinPriceService.retrieveAll(PageRequest.of(page, size));
    }
    @GetMapping("/{timestamp}")
    public Flux<BitcoinPrice> retrieveByTimestamp( @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "1") int size,
                                                  @PathVariable
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                          LocalDateTime timestamp){
        log.info("Retrieving Bitcoin Price by Timestamp: "+ timestamp.format(DateTimeFormatter.ISO_DATE_TIME));
        return iBitcoinPriceService.retrieveByTimestamp(timestamp,PageRequest.of(page, size))
                .switchIfEmpty(Mono.error(new TimestampNotFound()));

    }

    @GetMapping("/avg")
    public Mono<BigDecimal> avg(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       List<LocalDateTime> timestamp){
        log.info("Retrieving Avg Bitcoin Price between Timestamps: {}", timestamp);
        return iBitcoinPriceService.avg(timestamp);
    }
}
