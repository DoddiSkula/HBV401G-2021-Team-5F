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


public class FlightUserController implements Initializable{
    @FXML public TableView<BookingDisplay> bookingsView;
    @FXML private Button modeButton, userButton;
    @FXML private TextField nameTextField, passwordTextField, emailTextField;
    @FXML private Label statusLabel, emailLabel, passwordLabel, nameFieldLabel;
    @FXML private TableColumn<BookingDisplay, String> airlineColumn;
    @FXML private TableColumn<BookingDisplay, String> fromColumn;
    @FXML private TableColumn<BookingDisplay, String> toColumn;
    @FXML private TableColumn<BookingDisplay, String> dateColumn;
    @FXML private TableColumn<BookingDisplay, String> departureTimeColumn;
    @FXML private TableColumn<BookingDisplay, String> arrivalTimeColumn;
    @FXML private TableColumn<BookingDisplay, String> seatNumberColumn;
    @FXML private TableColumn<BookingDisplay, Boolean> foodColumn;

    private final DataFactory dataFactory = new DataFactory();
    public ObservableList<BookingDisplay> bookingsDisplay = FXCollections.observableArrayList();

    public int mode = 0;
    public UserData ud = UserData.getInstance();


    public void changeMode(ActionEvent event) throws IOException {
        if(mode == 0){
            userButton.setText("Nýskrá");
            modeButton.setText("Ég á reikning");
            nameFieldLabel.setOpacity(1);
            nameTextField.setOpacity(1);
            mode = 1;
        }
        else if(mode == 1){
            userButton.setText("Innskrá");
            modeButton.setText("Nýr notandi");
            nameFieldLabel.setOpacity(0);
            nameTextField.setOpacity(0);
            mode = 0;
        }
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("search.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(ud.user != null){
            statusLabel.setText(ud.user.getName());
            loginSuccess();
        }
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalLocation"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("flightDate"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        seatNumberColumn.setCellValueFactory(new PropertyValueFactory<>("seatId"));
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("mealService"));
    }

    public Boolean register(String name, String email, String password) {
        ObservableList<User> checkUserExists = getUser(email);
        if(checkUserExists.size() != 0 || name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        ud.user = dataFactory.createUser(name, email, password);
        return true;
    }

    public Boolean login(String email, String password) {
        ObservableList<User> checkUserExists = getUser(email);
        if(checkUserExists.size() == 0) {
            return false;
        }
        User checkUser = getUser(email).get(0);
        if(checkUser == null || !checkUser.getPassword().equals(password)) {
            return false;
        }
        ud.user = checkUser;
        return true;
    }

    public void logout() {
        mode = 0;
        hideOrShowLoginItems(1);
        userButton.setText("Innskrá");
        statusLabel.setText("Enginn notandi skráður inn");
        ud.user = null;
        bookingsDisplay = FXCollections.observableArrayList();;
        bookingsView.setItems(bookingsDisplay);
    }


    private ObservableList<User> getUser(String email) {
        String mail = email == null || email.equals("") ? "%" : email;
        return dataFactory.getUsers(mail);
    }

    public void hideOrShowLoginItems(int show){
        Boolean x;
        if(show == 0){ x = true;
        }
        else{ x = false;
        }
        emailLabel.setOpacity(show);
        emailTextField.setOpacity(show);
        emailTextField.setText("");
        emailTextField.setDisable(x);
        passwordLabel.setOpacity(show);
        passwordTextField.setOpacity(show);
        passwordTextField.setText("");
        passwordTextField.setDisable(x);
        modeButton.setOpacity(show);
        modeButton.setDisable(x);
        nameFieldLabel.setOpacity(0);
        nameTextField.setOpacity(0);
        nameTextField.setText("");
        nameTextField.setDisable(x);
    }

    public void loginSuccess(){
        mode = 2;
        hideOrShowLoginItems(0);
        userButton.setText("Útskrá");
        statusLabel.setText(ud.user.getName());
        getBookingDisplay();
        bookingsView.setItems(bookingsDisplay);
        bookingsView.getColumns().setAll(airlineColumn,fromColumn,toColumn,dateColumn,departureTimeColumn,arrivalTimeColumn,seatNumberColumn,foodColumn);
    }

    public void userButtonPressed(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String name = nameTextField.getText();
        if(mode == 0){
            if (emailTextField.getText().length() == 0 || passwordTextField.getText().length() == 0) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Vinsamlegast fyllið út í alla reiti");
                a.show();
            }
            else if(login(email,password)){
                loginSuccess();
            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Rangt netfang eða lykilorð");
                a.show();
            }
        }
        else if (mode == 1){
            if (nameTextField.getText().length() == 0 || passwordTextField.getText().length() == 0 || nameTextField.getText().length() == 0){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Vinsamlegast fyllið út í alla reiti");
                a.show();
            }
            else if(register(name,email,password)){
                loginSuccess();
            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Netfang þegar á skrá");
                a.show();
            }
        }
        else if(mode == 2){
            logout();
        }
    }

    public void getBookingDisplay() {
        ObservableList<Booking> bookings = dataFactory.getBookings(ud.user.getEmail());
        for(int i = 0; i < bookings.size(); i++){
            Booking booking = bookings.get(i);
            bookingsDisplay.add(new BookingDisplay(booking.getFlight(), booking.getSeat()));
        }
    }

    public static void main(String[] args) {
        FlightUserController userController = new FlightUserController();

        System.out.println(userController.login("", ""));
    }
}
