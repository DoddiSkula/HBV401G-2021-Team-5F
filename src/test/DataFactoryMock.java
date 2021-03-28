package test;

import flightpackage.DataFactoryInterface;
import flightpackage.Flight;
import flightpackage.Seat;
import flightpackage.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataFactoryMock implements DataFactoryInterface {

    // Munum ekki nota testi
    public ObservableList<User> getUsers() {
        return null;
    }

    // Munum ekki nota í testi
    public ObservableList<Seat> getSeats(int flight_id) {
        return  null;
    }

    /**
     * Sækir flug eftir leit
     *
     * @param departureLocation brottfararstaður
     * @param arrivalLocation komustaður
     * @param flightDate dagsetning flugs (ár-mánuður-dags)
     * @param mealService er flug með veitingasölu eða ekki
     *
     * @return listi af flugum
     */
    public ObservableList<Flight> getFlights(String departureLocation, String arrivalLocation, String flightDate, Boolean mealService) {
        ObservableList<Flight> flights = FXCollections.observableArrayList();

        return flights;
    }
}
