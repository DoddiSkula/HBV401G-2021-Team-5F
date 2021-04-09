package flightpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;

public class FlightBookingController {
    private FlightSearchController searchController = new FlightSearchController();
    private FlightUserController userController = new FlightUserController();
    private DataFactory dataFactory = new DataFactory();

    private ObservableList<Booking> booking;

    // createBooking
    public ObservableList<Booking> createBooking(User user, Flight flight, ArrayList<Integer> seat_id) {
        ObservableList<Booking> book = FXCollections.observableArrayList();
        for(int id : seat_id) {
            Seat seat = dataFactory.getSeat(flight.getId(), id);
            book.add(new Booking(flight, user, seat));
        }

        // TODO skrifa í gagnagrunn

        return book;
    }

    // tengir viðmót við bókunarvirkni
    @FXML
    private void bookingHandler() {
        User user = userController.getLoggedInUser();
        Flight flight = searchController.getSelectedFlight();

        // TODO ná í sæti
        ArrayList<Integer> seats = null;

        booking = createBooking(user, flight, seats);
    }

}
