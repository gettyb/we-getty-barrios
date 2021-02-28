package com.wenance.bitcoinprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.wenance.bitcoinprices.repository")
public class BitcoinPricesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinPricesServiceApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		return  WebClient.create("");
	}
}
