package com.wenance.bitcoinprices.repository;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface BitcoinPriceRepository extends ReactiveMongoRepository<BitcoinPrice, String> {
        Flux<BitcoinPrice> findByTimestamp(LocalDateTime timestamp, Pageable page);
        Flux<BitcoinPrice> findByIdNotNull(Pageable pageable);
}


