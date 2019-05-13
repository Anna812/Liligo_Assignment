package com.liligo.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "flight_offers")
@Getter
@Setter
@NoArgsConstructor
public class FlightOffer {
    @Id
    @GeneratedValue
    @Column(name="datetime_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "location_id", insertable=false, updatable =false)
    private Location startLocation;
    @ManyToOne
    @JoinColumn(name = "location_id", insertable=false, updatable =false)
    private Location endLocation;
    private ZonedDateTime inbound;
    private ZonedDateTime outbound;
    @Column(name="trip_type")
    private TripType tripType;
    @Column(name="price_per_passenger")
    private int pricePerPassenger;
    private int numberOfPassengers;
    private String provider;

    public FlightOffer(Location startLocation, Location endLocation, ZonedDateTime inbound, ZonedDateTime outbound, TripType tripType, int pricePerPassenger, int numberOfPassengers, String provider) {
        this.startLocation= startLocation;
        this.endLocation = endLocation;
        this.inbound = inbound;
        this.outbound= outbound;
        this.tripType = tripType;
        this.pricePerPassenger = pricePerPassenger;
        this.numberOfPassengers= numberOfPassengers;
        this.provider = provider;
    }
}
