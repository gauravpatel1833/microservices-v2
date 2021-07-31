package com.microservices.practice.currencyexchange.controller;

import com.microservices.practice.currencyexchange.entity.CurrencyExchange;
import com.microservices.practice.currencyexchange.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeService exchangeService;

    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange getCurrencyExchange(@PathVariable String from,
                                                @PathVariable String to){

        CurrencyExchange currencyExchange = exchangeService.findByFromAndTo(from,to);

        if(currencyExchange == null){
            throw new RuntimeException("Unable to find data for from :"+from
            +" and to:"+to);
        }

        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }
}
