package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

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
    public Flux<BitcoinPrice> retrieveByTimestamp(LocalDateTime timestamp, Pageable pageable) {
        return repository.findByTimestamp(timestamp, pageable);
    }
}
