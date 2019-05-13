package com.liligo.assignment.repos;

import com.liligo.assignment.models.FlightOffer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlightOfferRepository extends CrudRepository<FlightOffer, Long> {
    public List<FlightOffer> findAllByNumberOfPassengers(int numberOfPassengers);
}
