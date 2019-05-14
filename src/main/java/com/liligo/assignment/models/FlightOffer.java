package com.liligo.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "flight_offers")
@Getter
@Setter
@NoArgsConstructor
public class FlightOffer {
    @Id
    @GeneratedValue
    @Column(name="datetime_id")
    @JsonIgnore
    private long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "startlocation_id")
    @JsonProperty("from")
    private Location startLocation;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endlocation_id")
    @JsonProperty("to")
    private Location endLocation;
    private OffsetDateTime inboundDeparture;
    private OffsetDateTime inboundArrival;
    private OffsetDateTime outboundDeparture;
    private OffsetDateTime outboundArrival;
    @Column(name="trip_type")
    private TripType tripType;
    @Column(name="price_per_passenger")
    private int pricePerPassenger;
    private int numberOfPassengers;
    private String provider;

    public FlightOffer(Location startLocation, Location endLocation, OffsetDateTime inboundDeparture, OffsetDateTime outboundDeparture, TripType tripType, int pricePerPassenger, int numberOfPassengers, String provider) {
        this.startLocation= startLocation;
        this.endLocation = endLocation;
        this.inboundDeparture = inboundDeparture;
        this.outboundDeparture = outboundDeparture;
        this.tripType = tripType;
        this.pricePerPassenger = pricePerPassenger;
        this.numberOfPassengers= numberOfPassengers;
        this.provider = provider;
    }
}
