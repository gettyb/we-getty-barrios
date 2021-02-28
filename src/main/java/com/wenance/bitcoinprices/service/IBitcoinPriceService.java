package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface IBitcoinPriceService {
    Flux<BitcoinPrice> retrieveAll(Pageable pageable);
    Flux<BitcoinPrice> retrieveByTimestamp(LocalDateTime timestamp,Pageable pageable);
}
