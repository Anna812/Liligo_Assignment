package com.liligo.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDateTime {
    private OffsetDateTime departure;
    private OffsetDateTime arrival;

    public boolean isValidFlightDate() {
        return departure.isBefore(arrival);
    }
}
