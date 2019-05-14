package com.liligo.assignment.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightOfferResponse {
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @JsonProperty("from")
    private Location startLocation;
    @JsonProperty("to")
    private Location endLocation;
    @JsonInclude(Include.NON_NULL)
    private String inbound;
    private String outbound;
    private TripType tripType;
    private int outboundFlightDuration;
    private int pricePerPassenger;
    private String provider;
}
