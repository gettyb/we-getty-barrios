package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class BitcoinPriceServiceImpl  implements IBitcoinPriceService {
    @Autowired
    BitcoinPriceRepository repository;

    @Override
    public Flux<BitcoinPrice> retrieveAll() {
        return Flux.fromIterable(repository.findAll());
    }

    @Override
    public Mono<BitcoinPrice> retrieveByTimestamp(LocalDateTime timestamp) {
        return Optional.ofNullable(repository.findByTimestamp(timestamp)).map(Mono::just).orElseGet(() -> Mono.empty());
    }
}
