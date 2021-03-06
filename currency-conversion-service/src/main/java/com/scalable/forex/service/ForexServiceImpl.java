package com.scalable.forex.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ForexServiceImpl implements ForexService {
	
	
	private static final String EXCHANGE_RATES_API_MIME_TYPE = "application/json";
    private static final String EXCHANGE_RATES_API_BASE_URL = "http://data.fixer.io/api/";
    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(ForexServiceImpl.class);
    
    private final WebClient webClient;
    
    public ForexServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl(EXCHANGE_RATES_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, EXCHANGE_RATES_API_MIME_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .build();
    }
		
    
	
    @Override
	public Mono<Object> convertCurrencies(String accessKey, String baseCurrency, String targetCurrency, String amount) {
		
		logger.info("Creating uri and sending request to exchange rates api...");
		
		return webClient.get()
                .uri("convert?access_key="+accessKey+"&from="+baseCurrency+"&to="+targetCurrency+"&amount="+amount)
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

}
