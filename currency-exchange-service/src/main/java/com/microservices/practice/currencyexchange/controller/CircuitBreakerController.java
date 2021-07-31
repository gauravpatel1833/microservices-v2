package com.microservices.practice.currencyexchange.controller;

import com.netflix.discovery.util.RateLimiter;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);


    /*Resilience4j Features
            1. Retry - Configure max auto retry through config else default is 3 and can define default respone
                as part of fallback method for different type of errors
            2. Circuit Breaker - Multiple hit on the api are failing so you can define thresholds limit or time.
               Based on that circuit will be on closed(request are working),semi-open(half fail half working)
               and open state(no actual call will be made CircuitBreaker return default response). It will keep retry
                after regular interval to check if circuit is closed.
            3. RateLimiter - It is define max no of API hit can be done in given time.
                You can define in config in 10s only 2 request. So for the rest of request it will throw error of ratelimiter.
            4. Bulkhead - It is used to define maximum number of concurrent calls at a time*/

    @GetMapping("/sampleApi")
    @Retry(name = "sample-api",fallbackMethod = "defaultResponse")
    //@CircuitBreaker(name = "default",fallbackMethod = "defaultResponse")
    //@RateLimiter(name="default")
    //@Bulkhead(name="sample-api")
    public String getSampleAPI(){
        logger.info("Sample API Call received");
        //Random code to hit an api which will throw an error
        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/randomapi", String.class);
        return entity.getBody();
    }

    public String defaultResponse(Exception ex){
        return "We are under maintenance:"+ex.getMessage();
    }
}
