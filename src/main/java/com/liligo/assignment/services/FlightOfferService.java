package com.liligo.assignment.services;

import com.liligo.assignment.models.FlightDateTime;
import com.liligo.assignment.models.FlightOffer;
import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.models.FlightOfferResponse;
import com.liligo.assignment.models.TripType;
import com.liligo.assignment.repos.FlightOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FlightOfferService {

    @Autowired
    FlightOfferRepository repository;

    public List<FlightOfferResponse> findAllFlights() {
        return mapToResponseObject(repository.findAll());
    }

    public List<FlightOfferResponse> findOfferByNoOfPassengers(int numberOfPassengers) {
        return mapToResponseObject(repository.findAllByNumberOfPassengers(numberOfPassengers));
    }

    private List<FlightOfferResponse> mapToResponseObject(Iterable<FlightOffer> offers) {
        return StreamSupport.stream(offers.spliterator(), false)
                .map(offer -> new FlightOfferResponse(
                        offer.getStartLocation(),
                        offer.getEndLocation(),
                        formatDate(convertToUTC(offer.getInboundDeparture())),
                        formatDate(convertToUTC(offer.getOutboundDeparture())),
                        offer.getTripType(),
                        calculateOutboundDuration(offer),
                        offer.getPricePerPassenger(),
                        offer.getProvider()))
                .collect(Collectors.toList());
    }

    private String formatDate(OffsetDateTime dateTimeUTC) {
        if(dateTimeUTC != null) {
            return dateTimeUTC.format(FlightOfferResponse.dateFormatter);
        }
        return null;
    }

    private OffsetDateTime convertToUTC(OffsetDateTime offsetDateTime) {
        if(offsetDateTime != null) {
            return offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
        }
        return null;
    }

    private int calculateOutboundDuration(FlightOffer offer) {
        long duration = Duration.between(offer.getOutboundDeparture(), offer.getOutboundArrival()).toMinutes();
        return (int) duration;
    }

    public void saveOffers(List<FlightOfferRequest> offers) {
        List<FlightOfferRequest> filteredOffers = filterOffersToSave(offers);
        List<FlightOffer> offersToSave = mapRequestObject(filteredOffers);
        repository.saveAll(offersToSave);
    }

    private List<FlightOfferRequest> filterOffersToSave(List<FlightOfferRequest> offers) {
        return offers.stream()
                .filter(FlightOfferRequest::isPriceUnderLimit)
                .filter(FlightOfferRequest::isInternational)
                .filter(FlightOfferRequest::areDatesConsequent)
                .filter(FlightOfferRequest::isTripLongEnough)
                .collect(Collectors.toList());
    }

    private List<FlightOffer> mapRequestObject(List<FlightOfferRequest> filteredOffers) {
        return filteredOffers.stream()
                .map(offer -> {
                    Optional<FlightOfferRequest> optionalOffer = Optional.of(offer);
                    return new FlightOffer(
                            offer.getStartLocation(),
                            offer.getEndLocation(),
                            optionalOffer.map(FlightOfferRequest::getInbound)
                                    .map(FlightDateTime::getDeparture)
                                    .orElse(null),
                            optionalOffer.map(FlightOfferRequest::getInbound)
                                    .map(FlightDateTime::getArrival)
                                    .orElse(null),
                            offer.getOutbound().getDeparture(),
                            offer.getOutbound().getArrival(),
                            getTripType(offer),
                            offer.getPrice() / offer.getNumberOfPassengers(),
                            offer.getNumberOfPassengers(),
                            offer.getProvider());
                })
                .collect(Collectors.toList());
    }

    private TripType getTripType(FlightOfferRequest offer) {
        if(offer.getInbound() != null) {
            return TripType.ROUND_TRIP;
        }
        return TripType.ONE_WAY;
    }
}
