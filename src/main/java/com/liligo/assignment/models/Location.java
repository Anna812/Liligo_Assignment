package com.liligo.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue
    @Column(name="location_id")
    private long id;
    private String country;
    private String city;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(country)
                .append(city)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) {
            return false;
        }
        if(other == this) {
            return true;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        Location otherLocation = (Location) other;
        return new EqualsBuilder()
                .append(country, otherLocation.getCountry())
                .append(city, otherLocation.getCity())
                .isEquals();
    }
}
