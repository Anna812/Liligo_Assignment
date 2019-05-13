package com.liligo.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "flight_offers")
@Getter
@Setter
@NoArgsConstructor
public class FlightOffer {
    @ManyToOne
    @JoinColumn(name = "location_id")
    @Column(name="start_location")
    private Location startLocation;
    @ManyToOne
    @JoinColumn(name = "location_id")
    @Column(name="end_location")
    private Location endLocation;
    private FlightDateTime inbound;
    private FlightDateTime outbound;
    @Column(name="trip_type")
    private TripType tripType;
    @Column(name="price_per_passenger")
    private int pricePerPassenger;
    private String provider;
}
