package com.liligo.assignment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightOfferRequest {
    private static final int maximumPrice = 1000;
    private static final int minimumDurationDays = 6;
    @JsonProperty("from")
    private Location startLocation;
    @JsonProperty("to")
    private Location endLocation;
    private FlightDateTime inbound;
    private FlightDateTime outbound;
    private int numberOfPassengers;
    private String provider;
    private int price;

    public boolean isPriceUnderLimit() {
        return price / numberOfPassengers < maximumPrice;
    }

    public boolean isDomestic() {
        return startLocation.getCountry().equals(endLocation.getCountry());
    }

    public boolean areDatesConsequent() {
        return inbound == null
                || inbound.isValidFlightDate()
                || outbound.isValidFlightDate()
                || outbound.getArrival().isBefore(inbound.getDeparture());
    }

    public boolean isTripLongEnough() {
        return inbound == null
                || !outbound.getDeparture().plusDays(minimumDurationDays)
                .equals(inbound.getArrival());
    }
}
