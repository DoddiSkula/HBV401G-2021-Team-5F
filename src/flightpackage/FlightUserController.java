package flightpackage;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FlightUserController implements Initializable{
    private final DataFactory dataFactory = new DataFactory();
    private User user;
    @FXML
    private Button modeButton;
    @FXML
    private Button loginOrNewUserButton;
    @FXML
    private Label nameFieldLabel;
    @FXML
    private TextField nameTextField;

    public void changeMode(ActionEvent event) throws IOException {
        if(loginOrNewUserButton.getText().length() == 7){
            loginOrNewUserButton.setText("Nýskrá");
            modeButton.setText("Ég á reikning");
            nameFieldLabel.setOpacity(1);
            nameTextField.setOpacity(1);
        }
        else{
            loginOrNewUserButton.setText("Innskrá");
            modeButton.setText("Nýr notandi");
            nameFieldLabel.setOpacity(0);
            nameTextField.setOpacity(0);
        }
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
    }

    public Boolean register(String name, String email, String password) {
        ObservableList<User> checkUserExists = getUser(email);
        if(checkUserExists.size() != 0 || name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        user = dataFactory.createUser(name, email, password);
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
        user = checkUser;
        return true;
    }

    public void logout() {
        user = null;
    }

    public User getLoggedInUser() {
        if(user == null) {
            System.out.println("No user logged in.");
            return null;
        }
        return user;
    }

    private ObservableList<User> getUser(String email) {
        String mail = email == null || email.equals("") ? "%" : email;
        return dataFactory.getUsers(mail);
    }

    public static void main(String[] args) {
        FlightUserController userController = new FlightUserController();

        System.out.println(userController.login("", ""));

        User testUser = userController.getLoggedInUser();
        System.out.println(testUser);
    }

}
