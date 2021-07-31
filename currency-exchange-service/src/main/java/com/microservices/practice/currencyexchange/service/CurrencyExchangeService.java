package com.microservices.practice.currencyexchange.service;

import com.microservices.practice.currencyexchange.entity.CurrencyExchange;
import com.microservices.practice.currencyexchange.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository exchangeRepository;

    public CurrencyExchange findByFromAndTo(String from, String to) {
        return exchangeRepository.findByFromAndTo(from,to);
    }
}
