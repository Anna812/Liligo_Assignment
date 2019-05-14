package com.liligo.assignment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightOfferResponse {
    @JsonProperty("from")
    private Location startLocation;
    @JsonProperty("to")
    private Location endLocation;
    private OffsetDateTime inbound;
    private OffsetDateTime outbound;
    private TripType tripType;
    private int outboundFlightDuration;
    private int pricePerPassenger;
    private String provider;
}
