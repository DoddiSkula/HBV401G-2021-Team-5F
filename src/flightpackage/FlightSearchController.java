package flightpackage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FlightSearchController implements Initializable {

    ObservableList<String> Departure_LocationsList = FXCollections.observableArrayList(" ","REK","EGS","AKR","IFJ","VEY","KEF");
    ObservableList<String> Arrival_LocationsList = FXCollections.observableArrayList(" ","REK","EGS","AKR","IFJ","VEY","KEF");
    @FXML
    public TableView<Flight> fligthsListViews;
    @FXML
    private ChoiceBox departureLocationBox;
    @FXML
    private ChoiceBox arrivalLocationBox;
    @FXML   private TableColumn<Flight, String> IdTableView;
    @FXML   private TableColumn<Flight, String> DepartureTableView;
    @FXML   private TableColumn<Flight, String> ArrivalTableView;
    @FXML   private TableColumn<Flight, String> DepartureTimeTableView;
    @FXML   private TableColumn<Flight, String> ArrivalTimeTableView;
    @FXML   private TableColumn<Flight, String> DateTableView;
    @FXML   private TableColumn<Flight, String> PriceTableView;
    @FXML   private TableColumn<Flight, String> AirlineTableView;
    @FXML   private TableColumn<Flight, String> MealTableView;

    private DataFactory dataFactory = new DataFactory();

    public ObservableList<Flight> fluglisti = FXCollections.observableArrayList();


    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("scene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        fluglisti = dataFactory.getFlights("%", "%", "%");

        arrivalLocationBox.setItems(Arrival_LocationsList);
        departureLocationBox.setItems(Departure_LocationsList);
        IdTableView.setCellValueFactory(new PropertyValueFactory<>("Id"));
        DepartureTableView.setCellValueFactory(new PropertyValueFactory<>("Frá"));
        ArrivalTableView.setCellValueFactory(new PropertyValueFactory<>("Til"));
        DepartureTimeTableView.setCellValueFactory(new PropertyValueFactory<>("Brottför"));
        ArrivalTimeTableView.setCellValueFactory(new PropertyValueFactory<>("Koma"));
        DateTableView.setCellValueFactory(new PropertyValueFactory<>("Dagur"));
        PriceTableView.setCellValueFactory(new PropertyValueFactory<>("Verð"));
        AirlineTableView.setCellValueFactory(new PropertyValueFactory<>("Flugfélag"));
        MealTableView.setCellValueFactory(new PropertyValueFactory<>("Matur"));


        // wrap the observable list in a filtered list
        FilteredList<Flight> filteredData = new FilteredList<>(fluglisti, b -> true);
        // search fallið í rauninni er að reyna að fá það til þess að sækja gögn úr choice box og filtera eftir texta gildi
        departureLocationBox.accessibleTextProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(flight -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (flight.getDepartureLocation().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (flight.getArrivalLocation().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
        });

     //þarf eitthvað að fixa þetta
        SortedList<Flight> sortedData = new SortedList<>(filteredData);
       // sortedData.comparatorProperty().bind(fligthsListViews.comparatorProperty());
     //   fligthsListViews.setItems(sortedData);

    }

    /**
     * Leitar af flugi eftir attributum
     * @param departureLocation brottfararstaður, departureLocation = "%" ef ekki á að leita eftir
     * @param arrivalLocation komustaður, arrivalLocation = "%" ef ekki á að leita eftir
     * @param flightDate dagsetning (ár-mánuður-dagsetning), flightDate = "%" ef ekki á að leita eftir
     * @return listi af flugum
     */
    public ObservableList<Flight> searchByAttribute(String departureLocation, String arrivalLocation, String flightDate) {
        return dataFactory.getFlights(departureLocation, arrivalLocation, flightDate);
    }

    public ObservableList<Flight> filterByAttribute() {
        // TODO
        return null;
    }

    public static void main(String[] args) {
        FlightSearchController searcher = new FlightSearchController();

        // Dæmi um að leita eftir flugi
        ObservableList<Flight> searchedFlights = searcher.searchByAttribute("REY", "%", "%");
        // Prenta útkomu
        for(Flight flight: searchedFlights) {
            System.out.println(flight);
        }
    }
}






