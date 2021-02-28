package com.wenance.bitcoinprices.repository;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BitcoinPriceRepository extends MongoRepository<BitcoinPrice, String> {
        BitcoinPrice findByTimestamp(LocalDateTime timestamp);
}


