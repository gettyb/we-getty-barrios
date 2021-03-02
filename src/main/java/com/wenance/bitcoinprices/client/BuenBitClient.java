package com.wenance.bitcoinprices.client;

import com.wenance.bitcoinprices.model.BitcoinDetail;
import com.wenance.bitcoinprices.model.BitcoinInfo;
import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

@Component
@Slf4j
public class BuenBitClient {

    @Autowired
    private BitcoinPriceRepository repository;
    @Autowired
    private WebClient client;

    @Autowired
    public void setRepository(BitcoinPriceRepository repository){
        this.repository = repository;
    }

    @Value( "${buenbit.url}" )
    private String buenBitUrl;

    @Value( "${buenbit.interval}" )
    private int buenBitInterval;

    private Flux flux;
    private Disposable disposable;

    public Mono<BitcoinDetail> getBitcoinPrices(long maxNumRetries){
        return client.get()
                .uri(buenBitUrl)
                .retrieve()
                .bodyToMono(BitcoinInfo.class)
                .map(bitcoinInfo -> bitcoinInfo.getObject().getBtcars())
                .retryWhen(Retry.maxInARow(maxNumRetries))
                .onErrorResume( e -> Mono.empty());
    }

    @PostConstruct
    void postConstruct(){
        log.info("@PostConstruct");
        Flux<LocalDateTime> localDateTimeFlux = Flux.interval(Duration.ofSeconds(buenBitInterval))
                .map(t -> LocalDateTime.now());

        Flux<BitcoinDetail> fluxCallApi= Flux.defer( () -> getBitcoinPrices(5)).repeat();
        flux= localDateTimeFlux.zipWith(fluxCallApi, (timestamp, bitcoinPriceDetail) -> repository.save(new BitcoinPrice(timestamp, bitcoinPriceDetail)).subscribe());
        disposable= flux.subscribe();
    }
    @PreDestroy
    void preDestroy(){
        log.info("@PreDestroy");
        disposable.dispose();
    }
}
