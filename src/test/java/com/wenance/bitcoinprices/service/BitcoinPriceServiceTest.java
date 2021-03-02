package com.wenance.bitcoinprices.service;

import com.wenance.bitcoinprices.BaseTests;
import com.wenance.bitcoinprices.model.BitcoinDetail;
import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class BitcoinPriceServiceTest extends BaseTests {

    @MockBean
    BitcoinPriceRepository repository;

    @Autowired
    private IBitcoinPriceService bitcoinPriceService;

    @Test
    public void testAvgBetweenExistingTimestampsShouldNotBeEmpty(){
        final LocalDateTime TIMESTAMP_EMPTY= LocalDateTime.now();
        final LocalDateTime VALID_TIMESTAMP_ONE= TIMESTAMP_EMPTY.plusSeconds(10);
        final LocalDateTime VALID_TIMESTAMP_TWO= TIMESTAMP_EMPTY.plusSeconds(20);
        final BitcoinPrice  PRICE_FOR_TIMESTAMP_ONE = BitcoinPrice.builder()
                                                            .id("1")
                                                            .btcarsDetail(BitcoinDetail.builder().purchase_price("1").build())
                                                            .build();
        final BitcoinPrice  PRICE_FOR_TIMESTAMP_TWO = BitcoinPrice.builder().id("2")
                .btcarsDetail(BitcoinDetail.builder().purchase_price("2").build())
                .build();

        final Pageable UNPAGED= Pageable.unpaged();

        when(repository.findByTimestamp(VALID_TIMESTAMP_ONE,UNPAGED)).thenReturn(Flux.just(PRICE_FOR_TIMESTAMP_ONE));
        when(repository.findByTimestamp(VALID_TIMESTAMP_TWO,UNPAGED)).thenReturn(Flux.just(PRICE_FOR_TIMESTAMP_TWO));

        Mono<BigDecimal> avgResult= bitcoinPriceService.avg(Arrays.asList(VALID_TIMESTAMP_ONE,VALID_TIMESTAMP_TWO));
        assertNotEquals(avgResult,Mono.empty());
        assertEquals(avgResult.block(),new BigDecimal(1.5));
    }
    @Test
    public void testAvgWithAnyTimestampDataEmptyShouldBeExtractedFromCalculation(){
        final LocalDateTime TIMESTAMP_EMPTY= LocalDateTime.now();
        final LocalDateTime VALID_TIMESTAMP_ONE= TIMESTAMP_EMPTY.plusSeconds(10);
        final LocalDateTime VALID_TIMESTAMP_TWO= TIMESTAMP_EMPTY.plusSeconds(20);
        final BitcoinPrice  PRICE_FOR_TIMESTAMP_ONE = BitcoinPrice.builder()
                .id("1")
                .btcarsDetail(BitcoinDetail.builder().purchase_price("1").build())
                .build();
        final BitcoinPrice  PRICE_FOR_TIMESTAMP_TWO = BitcoinPrice.builder().id("2")
                .btcarsDetail(BitcoinDetail.builder().purchase_price("2").build())
                .build();

        final Pageable UNPAGED= Pageable.unpaged();

        when(repository.findByTimestamp(TIMESTAMP_EMPTY,UNPAGED)).thenReturn(Flux.empty());
        when(repository.findByTimestamp(VALID_TIMESTAMP_ONE,UNPAGED)).thenReturn(Flux.just(PRICE_FOR_TIMESTAMP_ONE));
        when(repository.findByTimestamp(VALID_TIMESTAMP_TWO,UNPAGED)).thenReturn(Flux.just(PRICE_FOR_TIMESTAMP_TWO));

        Mono<BigDecimal> avgResult= bitcoinPriceService.avg(Arrays.asList(TIMESTAMP_EMPTY,VALID_TIMESTAMP_ONE,VALID_TIMESTAMP_TWO));
        assertNotEquals(avgResult,Mono.empty());
        assertEquals(avgResult.block(),new BigDecimal(1.5));
        verify(repository, times(1)).findByTimestamp(eq(TIMESTAMP_EMPTY),any(Pageable.class));
    }

    @Test
    public void testRetrieveAllShouldGetValues(){
        final BitcoinPrice  PRICE_FOR_TIMESTAMP_ONE = BitcoinPrice.builder()
                .id("1")
                .btcarsDetail(BitcoinDetail.builder().purchase_price("1").build())
                .build();
        final BitcoinPrice  PRICE_FOR_TIMESTAMP_TWO = BitcoinPrice.builder().id("2")
                .btcarsDetail(BitcoinDetail.builder().purchase_price("2").build())
                .build();

        final Pageable UNPAGED= Pageable.unpaged();
        final Flux<BitcoinPrice> expectedFlux= Flux.fromIterable(Arrays.asList(PRICE_FOR_TIMESTAMP_ONE,PRICE_FOR_TIMESTAMP_TWO));

        when(repository.findByIdNotNull(UNPAGED)).thenReturn(expectedFlux);

        Flux<BitcoinPrice> allPrices= bitcoinPriceService.retrieveAll(UNPAGED);

        assertNotEquals(allPrices,Flux.empty());
        verify(repository, times(1)).findByIdNotNull(any(Pageable.class));
    }


}
