package flightpackage;

import javafx.collections.ObservableList;

public interface DataFactoryInterface {
    ObservableList<User> getUsers(String email);

    ObservableList<Seat> getSeats(int flight_id);

    ObservableList<Flight> getFlights(String departureLocation, String arrivalLocation, String flightDate, Boolean mealService);
}
