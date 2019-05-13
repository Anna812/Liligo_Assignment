package com.liligo.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dates")
@Getter
@Setter
@NoArgsConstructor
public class FlightDateTime {
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
