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
    private static final int maximumPrice = 1001;
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

    public boolean isInternational() {
        return ! startLocation.getCountry().equals(endLocation.getCountry());
    }

    public boolean areDatesConsequent() {
        boolean result = outbound.isValidFlightDate();
        if (inbound != null) {
            result = result && inbound.isValidFlightDate()
                    && outbound.getArrival().isBefore(inbound.getDeparture());
        }
        return result;
    }

    public boolean isTripLongEnough() {
        if(inbound == null) {
            return true;
        }
        return outbound.getDeparture().plusDays(minimumDurationDays).isBefore(inbound.getArrival());
    }
}
