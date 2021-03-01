package com.wenance.bitcoinprices.client;

import com.wenance.bitcoinprices.model.BitcoinDetail;
import com.wenance.bitcoinprices.model.BitcoinInfo;
import com.wenance.bitcoinprices.model.BitcoinPrice;
import com.wenance.bitcoinprices.repository.BitcoinPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
public class BuenBitClient implements CommandLineRunner {

    @Autowired
    BitcoinPriceRepository repository;

    @Autowired
    private WebClient client;

    @Value( "${buenbit.url}" )
    private String buenBitUrl;

    public Mono<BitcoinDetail> getBitcoinPrices(long maxNumRetries){
        return client.get()
                .uri(buenBitUrl)
                .retrieve()
                .bodyToMono(BitcoinInfo.class)
                .map(bitcoinInfo -> bitcoinInfo.getObject().getBtcars())
                .retryWhen(Retry.maxInARow(maxNumRetries))
                .onErrorResume( e -> Mono.empty());
    }

    @Override
    public void run(String... args) {
        //TODO ver que función usar para que empiece ejecutando sin esperar los 10 segundos
        Flux<LocalDateTime> localDateTimeFlux = Flux.interval(Duration.ofSeconds(10))
                .map(t -> LocalDateTime.now());

        Flux<BitcoinDetail> fluxCallApi= Flux.defer( () -> getBitcoinPrices(5)).repeat();
        localDateTimeFlux.zipWith(fluxCallApi, (timestamp, bitcoinPriceDetail) -> repository.save(new BitcoinPrice(timestamp, bitcoinPriceDetail))
                .subscribe()).subscribe();
    }
}
