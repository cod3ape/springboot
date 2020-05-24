package com.cod3ape.currencyconversion;

import java.math.BigDecimal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		//return new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity, quantity, 0);
		/*
		String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		Map<String, String>urlVariables = new HashMap<String, String>();
		urlVariables.put("from", from);
		urlVariables.put("to", to);		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(url,
				CurrencyConversionBean.class, urlVariables);
		CurrencyConversionBean response = responseEntity.getBody();		
		*/
		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);		
		CurrencyConversionBean bean= new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), 
				quantity, quantity.multiply(response.getConversionMultiple()), response.getPort() );
		logger.info("bean: {}", bean);
		return bean;
		
	}
	
}
