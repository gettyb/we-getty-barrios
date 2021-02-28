package com.wenance.bitcoinprices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BitcoinObject {
    private BitcoinDetail daiars;
    private BitcoinDetail daiusd;
    private BitcoinDetail btcars;
}
