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
    @JsonProperty("from")
    private Location startLocation;
    @JsonProperty("to")
    private Location endLocation;
    private FlightDateTime inbound;
    private FlightDateTime outbound;
    private int numberOfPassengers;
    private String provider;
    private int price;
}
