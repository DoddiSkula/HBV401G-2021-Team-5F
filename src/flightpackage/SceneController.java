package flightpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SceneController implements Initializable{
    @FXML
    private Button addButton;
    @FXML
    private ListView usersListView;
    @FXML
    private ListView bookingsListView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    private DataFactory dataFactory = new DataFactory();
    private ObservableList<User> users = FXCollections.observableArrayList();

    public void addButtonOnActivity(ActionEvent event){
        users.add(new User(usernameTextField.getText(), emailTextField.getText(), "123"));
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("search.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = dataFactory.getUsers("%");
        usersListView.setItems(users);
    }
    public void listViewMouseClicked(MouseEvent mouseEvent){
        User selectedItem = (User) usersListView.getSelectionModel().getSelectedItem();
        emailTextField.setText(selectedItem.getEmail());
        usernameTextField.setText(selectedItem.getName());

        bookingsListView.setItems(getReservedFlights(selectedItem));
    }

    //þetta fer í annan controller
    private ObservableList<Flight> getReservedFlights( User user){
        ObservableList<Flight> reservedFlight = FXCollections.observableArrayList();
        ArrayList<Booking> bookings = user.getBookings();
        for(Booking booking: bookings){
            reservedFlight.add(booking.getFlight());
        }
        return reservedFlight;

    }
}

