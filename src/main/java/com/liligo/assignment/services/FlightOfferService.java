package com.liligo.assignment.services;

import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.models.FlightOfferResponse;
import com.liligo.assignment.models.TripType;
import com.liligo.assignment.repos.FlightOfferRepository;
import com.sun.scenario.effect.Offset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FlightOfferService {

    @Autowired
    FlightOfferRepository repository;

    public List<FlightOfferResponse> findAllFlights() {
        return mapToResponseObject(repository.findAll());
    }

    private List<FlightOfferResponse> mapToResponseObject(Iterable<FlightOffer> offers) {
        return StreamSupport.stream(offers.spliterator(), false)
                .map(offer -> new FlightOfferResponse(
                        offer.getStartLocation(),
                        offer.getEndLocation(),
                        offer.getInboundDeparture() != null ? offer.getInboundDeparture().withOffsetSameInstant(ZoneOffset.UTC) : null,
                        offer.getOutboundDeparture().withOffsetSameInstant(ZoneOffset.UTC),
                        offer.getTripType(),
                        calculateOutboundDuration(offer),
                        offer.getPricePerPassenger(),
                        offer.getProvider()
                ))
                .collect(Collectors.toList());
    }

    private int calculateOutboundDuration(FlightOffer offer) {
        long duration = Duration.between(offer.getOutboundDeparture(), offer.getOutboundArrival()).toMinutes();
        return (int) duration;
    }

    public List<FlightOfferResponse> findOfferByNoOfPassengers(int numberOfPassengers) {
        return mapToResponseObject(repository.findAllByNumberOfPassengers(numberOfPassengers));
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
                .filter(offer -> offer.getInbound() == null || offer.getOutbound().getArrival()
                        .isBefore(offer.getInbound().getDeparture()))
                .filter(offer -> offer.getInbound() == null || !offer.getOutbound().getDeparture().plusDays(6)
                        .equals(offer.getInbound().getArrival()))
                .collect(Collectors.toList());
    }

    private List<FlightOffer> mapRequestObject(List<FlightOfferRequest> filteredOffers) {
        return filteredOffers.stream()
                .map(offer -> new FlightOffer(
                        offer.getStartLocation(),
                        offer.getEndLocation(),
                        offer.getInbound() !=  null ? offer.getInbound().getDeparture() : null,
                        offer.getInbound() !=  null ? offer.getInbound().getArrival() : null,
                        offer.getOutbound().getDeparture(),
                        offer.getOutbound().getArrival(),
                        getTripType(offer),
                        offer.getPrice() / offer.getNumberOfPassengers(),
                        offer.getNumberOfPassengers(),
                        offer.getProvider()))
                .collect(Collectors.toList());
    }

    private TripType getTripType(FlightOfferRequest offer) {
        if(offer.getInbound() != null) {
            return TripType.ROUND_TRIP;
        }
        return TripType.ONE_WAY;
    }
}
