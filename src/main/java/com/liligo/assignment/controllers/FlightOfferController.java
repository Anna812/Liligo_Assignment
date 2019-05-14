package com.liligo.assignment.controllers;

import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.models.FlightOfferResponse;
import com.liligo.assignment.services.FlightOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightOfferController {

    @Autowired
    FlightOfferService flightOfferService;

    @GetMapping("/offers")
    public List<FlightOfferResponse> index() {
        return flightOfferService.findAllFlights();
    }

    @GetMapping("/offers/{numberOfPassengers}")
    public List<FlightOfferResponse> index(@PathVariable int numberOfPassengers) {
        if(numberOfPassengers > 0) {
            List<FlightOfferResponse> offers = flightOfferService.findOfferByNoOfPassengers(numberOfPassengers);
            if(!CollectionUtils.isEmpty(offers)) {
                return offers;
            }
        }
        return index();
    }

    @PostMapping("/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@RequestBody ArrayList<FlightOfferRequest> offers) {
        flightOfferService.saveOffers(offers);
    }
}
