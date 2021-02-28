package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IBitcoinPriceService {
    Flux<BitcoinPrice> retrieveAll(Pageable pageable);
    Flux<BitcoinPrice> retrieveByTimestamp(LocalDateTime timestamp,Pageable pageable);
    Mono<BigDecimal> avg(List<LocalDateTime> dateTimes);
}
