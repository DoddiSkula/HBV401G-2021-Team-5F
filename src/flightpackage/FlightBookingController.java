package flightpackage;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class FlightBookingController implements Initializable {
    @FXML public GridPane SeatGrid;
    public ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;
    public Label FromDisplay,ToDisplay, TimeDisplay, AirlineDisplay;

    private DataFactory dataFactory = new DataFactory();
    public UserData ud = UserData.getInstance();

    // createBooking
    public void createBooking(User user, Flight flight, ArrayList<Integer> seat_id) {
        for(int id : seat_id) {
            Seat seat = dataFactory.getSeat(flight.getId(), id);
            Booking currentBooking = new Booking(flight, user, seat);
            // bóka sæti
            dataFactory.reserveSeat(currentBooking.getFlight().getId(), currentBooking.getSeat().getSeatID(), false);
            // bæta bókun við gagnagrunn
            dataFactory.createBooking(currentBooking.getUser().getEmail() ,currentBooking.getFlight().getId(), currentBooking.getSeat().getSeatID());
        }
    }

    // takkavirkni til að loada scenes
    public void backbuttonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("search.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    // bóka flug takki
    public void confirmButtonPushed(ActionEvent event) throws IOException {
        ObservableList<Node> children = SeatGrid.getChildren();
        for (Node child : children) {
            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.isSelected()) {
                    ud.seats.add(parseInt(checkBox.getText()));
                }
            }
        }

        createBooking(ud.user, ud.flight, ud.seats);

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Bookingdisplay.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void displaybutton(ActionEvent event){
        Node node = (Node) event.getSource();
        Stage window = (Stage) node.getScene().getWindow();
        Flight u = (Flight) window.getUserData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FromDisplay.setText(ud.flight.getDepartureLocation());
        ToDisplay.setText(ud.flight.getArrivalLocation());
        TimeDisplay.setText(ud.flight.getDepartureTime());
        AirlineDisplay.setText(ud.flight.getAirline());

        Image seat = new Image("/images/seat.png");
        Image firstclass = new Image("/images/firstclass.png");
        Image emergency = new Image("/images/emergency.png");

        img1.setImage(firstclass); img2.setImage(firstclass); img3.setImage(firstclass);
        img4.setImage(firstclass); img5.setImage(seat); img6.setImage(seat);
        img7.setImage(seat); img8.setImage(seat); img9.setImage(emergency);
        img10.setImage(seat); img11.setImage(seat); img12.setImage(emergency);

        //initializea sætisglugga
        ObservableList<Node> children = SeatGrid.getChildren();
        int i = 0;
        for (Node child : children){
            i++;
            if(child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                int b = parseInt(checkBox.getText());
                Seat saeti = dataFactory.getSeat(ud.flight.getId(), i);
                if(b == saeti.getSeatID()){
                    if(!saeti.isAvailable()){
                        checkBox.setOpacity(0.3);
                        checkBox.setDisable(true);
                    }
                }
            }
        }
    }
}
