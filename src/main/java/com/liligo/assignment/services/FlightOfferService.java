package com.liligo.assignment.services;

import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.repos.FlightOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightOfferService {

    @Autowired
    FlightOfferRepository repository;

    public List<FlightOffer> findAllFlights() {
        return (List<FlightOffer>)repository.findAll();
    }

    public void saveOffers(List<FlightOffer> offers) {
        repository.saveAll(offers);
    }

    public List<FlightOffer> findOfferByNoOfPassengers(int numberOfPassengers) {
        return repository.findAllByNumberOfPassengers(numberOfPassengers);
    }
}
