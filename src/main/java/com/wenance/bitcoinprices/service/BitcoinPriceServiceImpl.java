package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class BitcoinPriceServiceImpl  implements IBitcoinPriceService {
    @Autowired
    BitcoinPriceRepository repository;

    @Override
    public Flux<BitcoinPrice> retrieveAll(Pageable pageable) {
        return repository.findByIdNotNull(pageable);
    }

    @Override
    //Todo ver de devolver una excepción de que no existe
    public Flux<BitcoinPrice> retrieveByTimestamp(LocalDateTime timestamp, Pageable pageable) {
        return repository.findByTimestamp(timestamp, pageable);
    }

    @Override
    public Mono<BigDecimal> avg(List<LocalDateTime> timestamps) {
        Flux<BigDecimal> prices=Flux.fromIterable(timestamps)
                .flatMap(time -> retrieveByTimestamp(time, Pageable.unpaged()))
                .map(bitcoinPrice -> new BigDecimal(bitcoinPrice.getBtcarsDetail().getPurchase_price()));

        return MathFlux.averageBigDecimal(prices);
    }
}
