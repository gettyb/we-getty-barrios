package com.wenance.bitcoinprices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BitcoinDetail{
    private String currency;
    private String bid_currency;
    private String ask_currency;
    private String purchase_price;
    private String selling_price;
    private String market_identifier;

}
