package com.wenance.bitcoinprices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BitcoinInfo {
    private BitcoinObject object;
    private List<String> errors;

}

