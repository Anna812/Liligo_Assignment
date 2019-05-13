package com.liligo.assignment.services;

import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.repos.FlightOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightOfferService {

    @Autowired
    FlightOfferRepository repository;

    public List<FlightOffer> findAllFlights() {
        return (List<FlightOffer>)repository.findAll();
    }

    public void saveOffers(List<FlightOfferRequest> offers) {
        List<FlightOfferRequest> filteredOffers = filterOffersToSave(offers);
        List<FlightOffer> offersToSave = mapRequestObject(filteredOffers);
        repository.saveAll(offersToSave);
    }

    private List<FlightOfferRequest> filterOffersToSave(List<FlightOfferRequest> offers) {
        return offers.stream()
                .filter(offer -> offer.getPrice() / offer.getNumberOfPassengers() < 1000)
                .filter(offer -> ! offer.getStartLocation().getCountry()
                        .equals(offer.getEndLocation().getCountry()))
                .filter(offer -> offer.getInbound().getArrival().toLocalDateTime()
                                        .isBefore(offer.getOutbound().getDeparture().toLocalDateTime()))
                .filter(offer -> ! offer.getInbound().getDeparture().toLocalDateTime().plusDays(6)
                        .equals(offer.getOutbound().getArrival().toLocalDateTime()))
                .collect(Collectors.toList());
    }

    private List<FlightOffer> mapRequestObject(List<FlightOfferRequest> filteredOffers) {
        return null;
    }

    public List<FlightOffer> findOfferByNoOfPassengers(int numberOfPassengers) {
        return repository.findAllByNumberOfPassengers(numberOfPassengers);
    }
}
