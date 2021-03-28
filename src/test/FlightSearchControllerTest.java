package test;

import flightpackage.Flight;
import flightpackage.FlightSearchController;
import javafx.collections.ObservableList;
import org.junit.*;
import static org.junit.Assert.*;

public class FlightSearchControllerTest {
    private FlightSearchController search;
    private ObservableList<Flight> flights;

    @Before
    public void setUp() {
        search = new FlightSearchController();
    }

    @After
    public void tearDown() {
        flights.removeAll();
    }

    @Test
    public void searchByAttributeTest1() {
        flights = search.searchByAttribute("REY", null, null, null);
        assertEquals(5, flights.size());
    }

    @Test
    public void searchByAttributeTest2() {
        flights = search.searchByAttribute(null, null, null, true);
        assertEquals(6, flights.size());
    }

    @Test
    public void searchByAttributeTest3() {
        Flight f = new Flight( 1,"REY", "AEY", "11:00", "13:00", "2021-01-01", 15000, "Iceland Air", true);
        flights = search.searchByAttribute("REY", "AEY", "2021-01-01", true);
        assertEquals(f.getId(), flights.get(0).getId());
    }

    @Test
    public void searchByAttributeTest4() {
        flights = search.searchByAttribute("REY", "REY", null, null);
        assertEquals(0, flights.size());
    }
}