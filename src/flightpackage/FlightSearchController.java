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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FlightSearchController implements Initializable {
    @FXML public TableView<Flight> fligthsListViews;
    @FXML private ChoiceBox departureLocationBox;
    @FXML private ChoiceBox arrivalLocationBox;
    @FXML private CheckBox mealService;
    @FXML private DatePicker flightDatePicker;
    @FXML private TableColumn<Flight, Integer> IdTableView;
    @FXML private TableColumn<Flight, String> DepartureTableView;
    @FXML private TableColumn<Flight, String> ArrivalTableView;
    @FXML private TableColumn<Flight, String> DepartureTimeTableView;
    @FXML private TableColumn<Flight, String> ArrivalTimeTableView;
    @FXML private TableColumn<Flight, String> DateTableView;
    @FXML private TableColumn<Flight, Integer> PriceTableView;
    @FXML private TableColumn<Flight, String> AirlineTableView;
    @FXML private TableColumn<Flight, Boolean> MealTableView;

    private final DataFactory dataFactory = new DataFactory();
    public ObservableList<Flight> flightList = FXCollections.observableArrayList();
    public Flight selectedFlight = null;

    ObservableList<String> Departure_LocationsList = FXCollections.observableArrayList("","REY","EGS","AEY","IFJ","VEY","KEF");
    ObservableList<String> Arrival_LocationsList = FXCollections.observableArrayList("","REY","EGS","AEY","IFJ","VEY","KEF");

    public void initialize(URL url, ResourceBundle resourceBundle) {
        flightList = dataFactory.getFlights("%", "%", "%", null);

        arrivalLocationBox.setItems(Arrival_LocationsList);
        departureLocationBox.setItems(Departure_LocationsList);

        IdTableView.setCellValueFactory(new PropertyValueFactory<>("id"));
        DepartureTableView.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));
        ArrivalTableView.setCellValueFactory(new PropertyValueFactory<>("arrivalLocation"));
        DepartureTimeTableView.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        ArrivalTimeTableView.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        DateTableView.setCellValueFactory(new PropertyValueFactory<>("flightDate"));
        PriceTableView.setCellValueFactory(new PropertyValueFactory<>("price"));
        AirlineTableView.setCellValueFactory(new PropertyValueFactory<>("airline"));
        MealTableView.setCellValueFactory(new PropertyValueFactory<>("mealService"));

        fligthsListViews.setItems(flightList);
        fligthsListViews.getColumns().setAll(IdTableView, DepartureTableView, ArrivalTableView, DepartureTimeTableView, ArrivalTimeTableView, DateTableView, PriceTableView, AirlineTableView, MealTableView);
    }

    /**
     * Leitar af flugi eftir attributum
     * @param departureLocation brottfararstaður, departureLocation = null ef ekki á að leita eftir
     * @param arrivalLocation komustaður, arrivalLocation = null ef ekki á að leita eftir
     * @param flightDate dagsetning (ár-mánuður-dagur), flightDate = null ef ekki á að leita eftir
     * @param meal máltíð í flugi eða ekki, meal = null ef ekki á að leita eftir
     * @return listi af flugum
     */
    public ObservableList<Flight> searchByAttribute(String departureLocation, String arrivalLocation, String flightDate, Boolean meal) {
        String dep = departureLocation == null || departureLocation.equals("") ? "%" : departureLocation;
        String arr = arrivalLocation == null || arrivalLocation.equals("") ? "%" : arrivalLocation;
        String date = flightDate == null || flightDate.equals("") ? "%" : flightDate;

        return dataFactory.getFlights(dep, arr, date, meal);
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("scene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void bookingbuttonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("seat.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void searchHandler(ActionEvent e) {
        String date = flightDatePicker.getValue() == null ? "" : flightDatePicker.getValue().toString();
        flightList = searchByAttribute((String) departureLocationBox.getValue(), (String) arrivalLocationBox.getValue(), date, mealService.isSelected());
        fligthsListViews.setItems(flightList);
    }

    public static void main(String[] args) {
        FlightSearchController searcher = new FlightSearchController();

        // Dæmi um að leita eftir flugum frá Reykjavík
        ObservableList<Flight> searchedFlights = searcher.searchByAttribute("REY", null, null, null);
        // Prenta útkomu
        for(Flight flight: searchedFlights) {
            System.out.println(flight);
        }
    }

}






