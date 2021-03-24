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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    ObservableList<String> Departure_LocationsList = FXCollections.observableArrayList("REK","EGS","AKR","IFJ","VEY","KEF");
    ObservableList<String> Arrival_LocationsList = FXCollections.observableArrayList("REK","EGS","AKR","IFJ","VEY","KEF");
    @FXML
    public ListView fligthsListViews;
    @FXML
    private ChoiceBox departureLocationBox;
    @FXML
    private ChoiceBox arrivalLocationBox;

    private DataFactory dataFactory = new DataFactory();

    public ObservableList<Flight> fl = FXCollections.observableArrayList();


    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("scene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fl = dataFactory.getFlights();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fligthsListViews.setItems(fl);
        arrivalLocationBox.setItems(Arrival_LocationsList);
        departureLocationBox.setItems(Departure_LocationsList);
    }



}
