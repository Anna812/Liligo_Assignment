package com.liligo.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightOfferRequest {
    private Location startLocation;
    private Location endLocation;
    private FlightDateTime inbound;
    private FlightDateTime outbound;
    private int numberOfPassengers;
    private String provider;
    private int price;
}
