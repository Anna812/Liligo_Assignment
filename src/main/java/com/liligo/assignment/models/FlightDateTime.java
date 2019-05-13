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
    @Id
    @GeneratedValue
    @Column(name="datetime_id")
    private long id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
