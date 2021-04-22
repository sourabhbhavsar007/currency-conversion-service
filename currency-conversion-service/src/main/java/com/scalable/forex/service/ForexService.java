package com.scalable.forex.service;

import reactor.core.publisher.Mono;

public interface ForexService {

	Mono<Object> fetchAllExchangeRates(String accessKey);

	Mono<Object> convertCurrencies(String accessKey, String baseCurrency, String targetCurrency, String amount);

}
