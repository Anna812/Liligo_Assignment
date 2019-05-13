package com.liligo.assignment.controllers;

import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.services.FlightOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FlightOfferController {

    @Autowired
    FlightOfferService flightOfferService;

    @RequestMapping("/offers")
    public List<FlightOffer> index() {
        return flightOfferService.findAllFlights();
    }

    @RequestMapping("/offers/{numberOfPassengers}")
    public List<FlightOffer> index(@PathVariable int numberOfPassengers) {
        return flightOfferService.findOfferByNoOfPassengers(numberOfPassengers);
    }

    @PostMapping("/save")
    public String save(List<FlightOffer> offers) {
        flightOfferService.saveOffers(offers);
        return "redirect:/offers";
    }
}
