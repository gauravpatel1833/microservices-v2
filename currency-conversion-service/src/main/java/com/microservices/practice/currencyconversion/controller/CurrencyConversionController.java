package com.microservices.practice.currencyconversion.controller;

import com.microservices.practice.currencyconversion.entity.CurrencyConversion;
import com.microservices.practice.currencyconversion.proxy.CurrencyExchangeProxy;
import com.microservices.practice.currencyconversion.vo.CurrencyExchangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeProxy proxy;

    /*Invocation using Rest Template*/
    @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        HashMap<String ,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyExchangeVO> response = restTemplate.getForEntity("http://CURRENCY-EXCHANGE/currency-exchange/from/{from}/to/{to}", CurrencyExchangeVO.class,uriVariables);
        CurrencyExchangeVO currencyExchangeVO = response.getBody();

        return new CurrencyConversion(currencyExchangeVO.getId(),
                from, to, quantity,
                currencyExchangeVO.getConversionMultiple(),
                quantity.multiply(currencyExchangeVO.getConversionMultiple()), currencyExchangeVO.getEnvironment()+" using Rest Template");

    }

    /*Invocation using feign*/
    /*Common use of feign is to reduce code it works like soap client with multiple microservices call the code with
    * rest template get increased*/
    @GetMapping("/feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionUsingFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        CurrencyExchangeVO currencyExchangeVO = proxy.getCurrencyExchange(from,to);

        return new CurrencyConversion(currencyExchangeVO.getId(),
                from, to, quantity,
                currencyExchangeVO.getConversionMultiple(),
                quantity.multiply(currencyExchangeVO.getConversionMultiple()), currencyExchangeVO.getEnvironment()+ " using feign");

    }
}
