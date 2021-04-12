package flightpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class BookingDisplayController  implements Initializable {

    @FXML
    public Label UserDisplay, FlightDsiplay, AirlineDisplay, FromDisplay, ToDisplay, SeatDisplay, seatsLabel,PriceDisplay;

    public UserData ud = UserData.getInstance();
    public void homeButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("search.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void userButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDisplay.setText(ud.user.getName());
        FlightDsiplay.setText(String.valueOf(ud.flight.getId()));
        AirlineDisplay.setText(ud.flight.getAirline());
        FromDisplay.setText(ud.flight.getDepartureLocation());
        ToDisplay.setText(ud.flight.getArrivalLocation());
        SeatDisplay.setText(ud.seats.toString());
        PriceDisplay.setText((ud.price) + " kr.");
        ud.seats = new ArrayList<Integer>();
        ud.flight = null;
        ud.price = 0;
    }
}
