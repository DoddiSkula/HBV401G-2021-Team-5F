package flightpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FlightBookingController implements Initializable {
    @FXML
    public Label FromDisplay;
    private FlightSearchController searchController = new FlightSearchController();
    private FlightUserController userController = new FlightUserController();
    private DataFactory dataFactory = new DataFactory();

    private ObservableList<Booking> booking;

    // createBooking
    public ObservableList<Booking> createBooking(User user, Flight flight, ArrayList<Integer> seat_id) {
        ObservableList<Booking> book = FXCollections.observableArrayList();
        for(int id : seat_id) {
            Seat seat = dataFactory.getSeat(flight.getId(), id);
            Booking currentBooking = new Booking(flight, user, seat);

            // bóka sæti
            dataFactory.reserveSeat(currentBooking.getFlight().getId(), currentBooking.getSeat().getSeatID(), false);

            // bæta bókun við gagnagrunn
            dataFactory.createBooking(currentBooking.getUser().getEmail() ,currentBooking.getFlight().getId(), currentBooking.getSeat().getSeatID());

            // bæta bókun við local lista
            book.add(currentBooking);
        }
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

    public void backbuttonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("search.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void receiveData(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage window = (Stage) node.getScene().getWindow();

        Flight u = (Flight) window.getUserData();
        FromDisplay.setText(u.getDepartureLocation());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //receiveData();

        //Flight fluginfo = searchController.getSelectedFlight();
        //FromDisplay.setText(fluginfo.getDepartureLocation());
    }
}
