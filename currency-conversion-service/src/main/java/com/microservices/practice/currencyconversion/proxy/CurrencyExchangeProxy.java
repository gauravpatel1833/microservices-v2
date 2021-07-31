package com.microservices.practice.currencyconversion.proxy;

import com.microservices.practice.currencyconversion.vo.CurrencyExchangeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*Name of service which you want to call and the host port details of the service to be consumed*/
/*Without Euraka service registry we need to provide URL with host port to connect
but if we are using eureka service registry then feign will connect to service with eureka naming service
also feign provide inbuilt spring cloud load balancer which is client side LB. In earlier spring version this was done by ribbon*/

/*@FeignClient(name = "currency-exchange",url = "localhost:8000")*/
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    /*Need to provide entire path url after host:port*/
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchangeVO getCurrencyExchange(@PathVariable("from") String from,
                                                  @PathVariable("to") String to);
}
