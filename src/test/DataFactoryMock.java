package test;

import flightpackage.DataFactoryInterface;
import flightpackage.Flight;
import flightpackage.Seat;
import flightpackage.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataFactoryMock implements DataFactoryInterface {

    /**
     * Sækir alla notendur.
     *
     * @return listi af notendum
     */
    public ObservableList<User> getUsers(String email) {
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("Matthias Book", "mb123@hi.is", "12345678"));
        users.add(new User("John Smith", "johnny@hi.is", "12345678"));
        return users;
    }

    /**
     * Sækir öll sæti í tilteknu flugi.
     *
     * @param flight_id flugnúmer
     * @return listi af sætum
     */
    public ObservableList<Seat> getSeats(int flight_id) {
        ObservableList<Seat> seats = FXCollections.observableArrayList();
        seats.add(new Seat(1, 1, true, false, false));
        seats.add(new Seat(1, 2, true, false, false));
        seats.add(new Seat(1, 3, true, false, false));
        seats.add(new Seat(1, 4, true, false, false));
        return seats;
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
        flights.add(new Flight(1, "REY", "AEY", "11:00", "13:00", "2021-01-01", 15000, "Iceland Air", true));
        flights.add(new Flight(2, "REY", "EGS", "11:00", "14:00", "2021-01-01", 22500, "Flugfélag Íslands", true));
        flights.add(new Flight(3, "REY", "IFJ", "12:00", "14:00", "2021-01-01", 13500, "Flugfélag Íslands", true));
        return flights;
    }
}
