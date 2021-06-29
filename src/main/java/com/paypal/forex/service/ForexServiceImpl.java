package com.paypal.forex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ForexServiceImpl implements ForexService {
	
	
	private static final String EXCHANGE_RATES_API_MIME_TYPE = "text/plain;charset=UTF-8";//"application/json";
    private static final String EXCHANGE_RATES_API_BASE_URL = "https://blockchain.info/tobtc";//"https://msmaster.qa.paypal.com:14828/v1/pricing/cryptocurrency-prices";
    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(ForexServiceImpl.class);
    private static final String accessKey = "F84264D3-3489-450A-890F-221FD89BBF0F";
    private final WebClient webClient;
    
    
    public ForexServiceImpl() {
    	
        this.webClient =  WebClient.builder()
                .baseUrl(EXCHANGE_RATES_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, EXCHANGE_RATES_API_MIME_TYPE)
                //.defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                //.defaultHeader("X-CoinAPI-Key", accessKey)
                //.secure(webClient.configuration().sslProvider())
                .build();
        
    }
		

	@Override
	public Mono<Object> fetchExchangeRates(String accessKey, String baseCurrency,String targetCurrency) {
		
		logger.info("Creating uri and sending request to exchangerates api...");
		
		return webClient.get()
                .uri("?asset_symbol="+baseCurrency+"&fiat_currency_code="+targetCurrency)
                  .retrieve()
                  .bodyToMono(Object.class).log()
                  .onErrorResume(e -> Mono.just("Error occurred :  " + e.getMessage())); //fallback
		
	}


	@Override
	public Mono<Object> fetchAllExchangeRates(String accessKey) {
		
		logger.info("Creating uri and sending request to fetch all exchange rates...");
		
		return webClient.get()
                .uri("latest?access_key="+accessKey)
                  .retrieve()
                  .bodyToMono(Object.class).log()
                  .onErrorResume(e -> Mono.just("Error occurred :  " + e.getMessage()));
		
	}

	@Override
	public Mono<String> getBitcoinsRequiredForCollateral(String accessKey, String dollars) {
		return webClient.get()
				.uri("?currency=USD&value=" + dollars)
				.retrieve()
				.bodyToMono(String.class).log()
				.onErrorResume(e -> Mono.just("Error occurred :  " + e.getMessage())); //fallback
	}

}
