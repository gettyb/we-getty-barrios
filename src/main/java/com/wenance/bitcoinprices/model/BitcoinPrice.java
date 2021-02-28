package com.wenance.bitcoinprices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bitcoin_prices")
public class BitcoinPrice {

    @Id
    private String id;

    @Indexed(unique=true)
    private LocalDateTime timestamp;
    private BitcoinDetail btcarsDetail;

    public BitcoinPrice(LocalDateTime timestamp, BitcoinDetail btcarsDetail) {
        this.timestamp = timestamp;
        this.btcarsDetail = btcarsDetail;
    }
}
