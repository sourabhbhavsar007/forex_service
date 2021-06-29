package com.paypal.forex.service;

import reactor.core.publisher.Mono;

public interface ForexService {

	Mono<Object> fetchExchangeRates(String apiKey, String baseCurrency, String targetCurrency);

	Mono<Object> fetchAllExchangeRates(String accessKey);

    Mono<String> getBitcoinsRequiredForCollateral(String accessKey, String dollars);
}
