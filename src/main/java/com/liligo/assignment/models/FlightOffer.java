package com.liligo.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime inbound;
    private LocalDateTime outbound;
    @Column(name="trip_type")
    private TripType tripType;
    @Column(name="price_per_passenger")
    private int pricePerPassenger;
    private int numberOfPassengers;
    private String provider;
}
