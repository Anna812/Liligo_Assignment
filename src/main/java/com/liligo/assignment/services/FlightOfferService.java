package com.liligo.assignment.services;

import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.models.TripType;
import com.liligo.assignment.repos.FlightOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
                .filter(offer -> !offer.getStartLocation().getCountry()
                        .equals(offer.getEndLocation().getCountry()))
                .filter(offer -> offer.getOutbound().getArrival()
                        .isBefore(offer.getInbound().getDeparture()))
                .filter(offer -> !offer.getOutbound().getDeparture().plusDays(6)
                        .equals(offer.getInbound().getArrival()))
                .collect(Collectors.toList());
    }

    private List<FlightOffer> mapRequestObject(List<FlightOfferRequest> filteredOffers) {
        return filteredOffers.stream()
                .map(offer -> new FlightOffer(
                        offer.getStartLocation(),
                        offer.getEndLocation(),
                        offer.getInbound().getDeparture(),
                        offer.getOutbound().getDeparture(),
                        getTripType(offer),
                        offer.getPrice() / offer.getNumberOfPassengers(),
                        offer.getNumberOfPassengers(),
                        offer.getProvider()))
                .collect(Collectors.toList());
    }
    
    private TripType getTripType(FlightOfferRequest offer) {
        if(offer.getStartLocation().equals(offer.getEndLocation())) {
            return TripType.ROUND_TRIP;
        }
        return TripType.ONE_WAY;
    }

    public List<FlightOffer> findOfferByNoOfPassengers(int numberOfPassengers) {
        return repository.findAllByNumberOfPassengers(numberOfPassengers);
    }
}
