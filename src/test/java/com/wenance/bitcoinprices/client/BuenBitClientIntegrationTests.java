package com.wenance.bitcoinprices.client;

import com.wenance.bitcoinprices.BaseTests;
import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@Slf4j
public class BuenBitClientIntegrationTests extends BaseTests {

    @Autowired
    private BitcoinPriceRepository repository;

    @Autowired
    private BuenBitClient buenBitClient;

    @Test
    public void justChecking() throws InterruptedException {
        //TODO check why this bean is null
        log.info("Aca -> ",buenBitClient);
        Thread.sleep(10000);
        //List<BitcoinPrice> first= repository.findByIdNotNull(PageRequest.of(0,1)).collectList().block();
        //assertNotEquals(first.size(),0);
    }
}
