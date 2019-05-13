package com.liligo.assignment.controllers;

import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.services.FlightOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightOfferController {

    @Autowired
    FlightOfferService flightOfferService;

    @GetMapping("/offers")
    public List<FlightOffer> index() {
        return flightOfferService.findAllFlights();
    }

    @GetMapping("/offers/{numberOfPassengers}")
    public List<FlightOffer> index(@PathVariable int numberOfPassengers) {
        if(numberOfPassengers < 0) {
            return flightOfferService.findOfferByNoOfPassengers(numberOfPassengers);
        }
        return index();
    }

    @PostMapping("/save")
    public List<FlightOffer> save(ArrayList<FlightOfferRequest> offers) {
        flightOfferService.saveOffers(offers);
        return index();
    }
}
