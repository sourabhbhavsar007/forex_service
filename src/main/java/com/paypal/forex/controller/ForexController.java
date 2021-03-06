package com.paypal.forex.controller;

import java.text.ParseException;
import java.util.List;

import com.paypal.forex.entity.TransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import com.paypal.forex.service.ForexService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/exchangerate")
public class ForexController {
	
	private ForexService forexService;
    private Environment env;
    private static final Logger logger = LoggerFactory.getLogger(ForexController.class);
    
    
    @Autowired
    public ForexController(ForexService forexService, Environment env) {
		super();
		this.forexService = forexService;
		this.env = env;
	}


	@GetMapping("/USD/{dollars}")
	public Mono<String> displayExchangeRates(@PathVariable String dollars) throws ParseException {

		logger.info("Request to get number of bitcoins required for collateral against : " + dollars + " USD");
		String accessKey = env.getProperty("app.api.key");

		return forexService.getBitcoinsRequiredForCollateral(accessKey, dollars);

	}
    
    /**
     * This is our main API which passes date and base and target currency to fetch exchange rates from api.exchangerates.io 
     * 
     * We use WebClient to call exchange rates API. 
     * 
     *  From Postman, we call localhost:8080/exchangerate/BTC/USD and after processing, 
     *  we use web client to call external API.
     *  
     *  The web client API can be seen in logs : 
     *  https://rest.coinapi.io/v1/exchangerate/BTC/USD
     *
     *	However, since I use free and trial version of exchangerates.io, they have restricted this 
     *  request and we get response as 403 Forbidden access.
     *
     *
     *
     * @param baseCurrency
     * @param targetCurrency
     * @return
     * @throws ParseException
     */


	
    @GetMapping("/{baseCurrency}/{targetCurrency}")
	public Mono<Object> displayExchangeRates(@PathVariable String baseCurrency, @PathVariable String targetCurrency) throws ParseException {
    
    	logger.info("Request to ForexService for coversion between : " + baseCurrency + " -> " + targetCurrency);
    	String accessKey = env.getProperty("app.api.key");
    	
    	return forexService.fetchExchangeRates(accessKey, baseCurrency, targetCurrency);
    	
	}


    /**
     * This API is only accessible to trial users, so we will use this and display all exchange rates with base as Euro.
     * 
     * 
     * @param date
     * @param baseCurrency
     * @param targetCurrency
     * @return
     * @throws ParseException
     */
    
    @GetMapping("/getAllCurrencies")
	public Mono<Object> displayAllExchangeRates(){
    
    	logger.info("Getting access key and calling ForexService...");
    	String accessKey = env.getProperty("app.api.key");
    	
    	return forexService.fetchAllExchangeRates(accessKey);
    	
	}


	@PostMapping("/transaction")
	private void saveStudent(@RequestBody TransactionRecord record)
	{
		forexService.persistAudit(record);
	}

	@GetMapping("/notify/{currentBTC}")
	private List<TransactionRecord> getAllNotificationRecords(@PathVariable String currentBTC){
		double currentPrice = Double.parseDouble(currentBTC);
		return forexService.getAllNotificationRecords(currentPrice);
	}

}
