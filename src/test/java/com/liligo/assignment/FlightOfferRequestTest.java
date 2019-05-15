package com.liligo.assignment;

import com.liligo.assignment.models.FlightDateTime;
import com.liligo.assignment.models.FlightOfferRequest;
import com.liligo.assignment.models.Location;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlightOfferRequestTest {
    private FlightOfferRequest testRequest;

    @Before
    public void setup() {
        testRequest = new FlightOfferRequest();
    }

    @Test
    public void testIsPriceUnderLimitWithCorrectPrice() {
        testRequest.setPrice(500);
        testRequest.setNumberOfPassengers(5);
        assertTrue(testRequest.isPriceUnderLimit());
    }

    @Test
    public void testIsInternationalWithDomestic() {
        Location mockLocation = mock(Location.class);
        when(mockLocation.getCountry()).thenReturn("Zambia");
        testRequest.setStartLocation(mockLocation);
        testRequest.setEndLocation(mockLocation);
        assertFalse(testRequest.isInternational());
    }

    @Test
    public void testAreDatesConsequentWithCorrectDatesOneWay() {
        OffsetDateTime departure = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 5, 45), ZoneOffset.UTC);
        OffsetDateTime arrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 45), ZoneOffset.UTC);
        FlightDateTime outbound = new FlightDateTime(departure, arrival);
        testRequest.setOutbound(outbound);
        assertTrue(testRequest.areDatesConsequent());
    }

    @Test
    public void testAreDatesConsequentWithCorrectDatesRoundTrip() {
        OffsetDateTime outboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 5, 45), ZoneOffset.UTC);
        OffsetDateTime outboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 45), ZoneOffset.UTC);
        OffsetDateTime inboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 13, 5, 45), ZoneOffset.UTC);
        OffsetDateTime inboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 13, 7, 45), ZoneOffset.UTC);

        FlightDateTime outbound = new FlightDateTime(outboundDeparture, outboundArrival);
        FlightDateTime inbound = new FlightDateTime(inboundDeparture, inboundArrival);

        testRequest.setOutbound(outbound);
        testRequest.setInbound(inbound);
        assertTrue(testRequest.areDatesConsequent());
    }

    @Test
    public void testAreDatesConsequentWithWrongInbounds() {
        OffsetDateTime outboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 5, 45), ZoneOffset.UTC);
        OffsetDateTime outboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 45), ZoneOffset.UTC);
        OffsetDateTime inboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 13, 5, 45), ZoneOffset.UTC);
        OffsetDateTime inboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 13, 4, 45), ZoneOffset.UTC);

        FlightDateTime outbound = new FlightDateTime(outboundDeparture, outboundArrival);
        FlightDateTime inbound = new FlightDateTime(inboundDeparture, inboundArrival);

        testRequest.setOutbound(outbound);
        testRequest.setInbound(inbound);
        assertFalse(testRequest.areDatesConsequent());
    }

    @Test
    public void testAreDatesConsequentWithInboundBeforeOutbound() {
        OffsetDateTime outboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 5, 45), ZoneOffset.UTC);
        OffsetDateTime outboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 45), ZoneOffset.UTC);
        OffsetDateTime inboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 0), ZoneOffset.UTC);
        OffsetDateTime inboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 13, 7, 45), ZoneOffset.UTC);

        FlightDateTime outbound = new FlightDateTime(outboundDeparture, outboundArrival);
        FlightDateTime inbound = new FlightDateTime(inboundDeparture, inboundArrival);

        testRequest.setOutbound(outbound);
        testRequest.setInbound(inbound);
        assertFalse(testRequest.areDatesConsequent());
    }

    @Test
    public void testIsTripLongEnoughWithoutInbound() {
        OffsetDateTime departure = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 5, 45), ZoneOffset.UTC);
        OffsetDateTime arrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 45), ZoneOffset.UTC);
        FlightDateTime outbound = new FlightDateTime(departure, arrival);
        testRequest.setOutbound(outbound);
        assertTrue(testRequest.isTripLongEnough());
    }

    @Test
    public void testIsTripLongEnoughWith10Days() {
        OffsetDateTime outboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 5, 45), ZoneOffset.UTC);
        OffsetDateTime outboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 12, 7, 45), ZoneOffset.UTC);
        OffsetDateTime inboundDeparture = OffsetDateTime.of(LocalDateTime.of(2017, 5, 18, 5, 50), ZoneOffset.UTC);
        OffsetDateTime inboundArrival = OffsetDateTime.of(LocalDateTime.of(2017, 5, 18, 7, 45), ZoneOffset.UTC);

        FlightDateTime outbound = new FlightDateTime(outboundDeparture, outboundArrival);
        FlightDateTime inbound = new FlightDateTime(inboundDeparture, inboundArrival);

        testRequest.setOutbound(outbound);
        testRequest.setInbound(inbound);
        assertTrue(testRequest.isTripLongEnough());
    }
}
