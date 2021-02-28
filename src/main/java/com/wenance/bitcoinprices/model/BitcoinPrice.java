package com.wenance.bitcoinprices.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bitcoin_prices")
public class BitcoinPrice {

    //TODO ver de sustituir por lombok
    @Id
    private String id;

    @Indexed(unique=true)
    private LocalDateTime timestamp;
    private BitcoinDetail btcarsDetail;

    public BitcoinPrice() {}

    public BitcoinPrice(LocalDateTime timestamp, BitcoinDetail btcarsDetail) {
        this.timestamp = timestamp;
        this.btcarsDetail = btcarsDetail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BitcoinDetail getBtcarsDetail() {
        return btcarsDetail;
    }

    public void setBtcarsDetail(BitcoinDetail btcarsDetail) {
        this.btcarsDetail = btcarsDetail;
    }
}
