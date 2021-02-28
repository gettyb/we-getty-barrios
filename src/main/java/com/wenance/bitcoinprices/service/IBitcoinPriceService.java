package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface IBitcoinPriceService {
    Flux<BitcoinPrice> retrieveAll();
    Mono<BitcoinPrice> retrieveByTimestamp(LocalDateTime timestamp);
}
