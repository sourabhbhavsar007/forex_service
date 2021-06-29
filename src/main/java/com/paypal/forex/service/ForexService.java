package com.paypal.forex.service;

import com.paypal.forex.entity.TransactionRecord;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ForexService {

	Mono<Object> fetchExchangeRates(String apiKey, String baseCurrency, String targetCurrency);

	Mono<Object> fetchAllExchangeRates(String accessKey);

    Mono<String> getBitcoinsRequiredForCollateral(String accessKey, String dollars);

	void persistAudit(TransactionRecord record);

	List<TransactionRecord> getAllNotificationRecords(double currentPrice);
}
