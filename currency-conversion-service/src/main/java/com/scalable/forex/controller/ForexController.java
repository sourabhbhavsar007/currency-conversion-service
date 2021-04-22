package com.scalable.forex.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;

import com.scalable.forex.service.ForexService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange-rate")
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
    
    /**
     * This is our main API which passes date and base and target currency to fetch exchange rates from data.fixer.io 
     * 
     * We use WebClient to call exchange rates API. 
     * 
     *  From Postman, we call http://localhost:8080/api/exchange-rate/GBP/USD/25 and after processing, 
     *  we use web client to call external API.
     *  
     *  The web client API can be seen in logs : 
     *  http://data.fixer.io/api/convert?access_key=d377affdb435bf5fbb10ee40cd7fb3f9&from=GBP&to=USD&amount=25
     *
     *	However, since I use free and trial version of data.fixer.io, they have restricted this 
     *  request and we get response as Access Restricted - Your current Subscription Plan does not support this API Function.
     *
     *
     *
     * 
     * @param base
     * @param target
     * @param units
     * @return
     * @throws ParseException
     */
    
    @GetMapping("/convert/{base}/{target}/{units}")
    public Mono<Object> convertCurrencies(@PathVariable String base, @PathVariable String target, @PathVariable String units) {
    	
    	logger.info("Getting access key and calling ForexService...");
    	String accessKey = env.getProperty("app.api.key");
    	
    	logger.info("Request to convert currency from : " + base + " -> " + target);
    	
    	return forexService.convertCurrencies(accessKey, base, target, units);
    }
	
    
    
    /**
     * This is the only API accessible to trial users, so we will use this and display all exchange rates with base as Euro.
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
    
    /**
     *  This endpoint redirects and returns to the public url which has interactive charts of exchange rates
     * @return
     */
    
    @GetMapping("/redirect")
    public RedirectView redirectWithUsingRedirectView() {
        return new RedirectView(env.getProperty("exchangeUrl"));
    }
    
    
    
	
}
