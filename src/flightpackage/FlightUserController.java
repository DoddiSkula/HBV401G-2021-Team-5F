package flightpackage;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FlightUserController implements Initializable{
    @FXML private Button modeButton;
    @FXML private Button userButton;
    @FXML private Label nameFieldLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField emailTextField;
    @FXML private Label statusLabel;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;

    private final DataFactory dataFactory = new DataFactory();
    private User user;

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
        user = ud.user;
        if(user != null){
            statusLabel.setText(ud.user.getName());
            loginSuccess();
        }
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

    public void hideOrShowLoginItems(int show){
        emailLabel.setOpacity(show);
        emailTextField.setOpacity(show);
        emailTextField.setText("");
        passwordLabel.setOpacity(show);
        passwordTextField.setOpacity(show);
        passwordTextField.setText("");
        modeButton.setOpacity(show);
        nameFieldLabel.setOpacity(0);
        nameTextField.setOpacity(0);
        nameTextField.setText("");
    }

    public void loginSuccess(){
        mode = 2;
        hideOrShowLoginItems(0);
        userButton.setText("Útskrá");
        statusLabel.setText(getLoggedInUser().getName());
        ud.user = getLoggedInUser();
    }

    public void userButtonPressed(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String name = nameTextField.getText();
        if(mode == 0){
            if(login(email,password)){
                loginSuccess();
            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Rangt netfang eða lykilorð");
                a.show();
            }
        }
        else if (mode == 1){
            if(register(name,email,password)){
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
            mode = 0;
            hideOrShowLoginItems(1);
            userButton.setText("Innskrá");
            statusLabel.setText("Enginn notandi skráður inn");
            ud.user = null;
        }
    }

    public static void main(String[] args) {
        FlightUserController userController = new FlightUserController();

        System.out.println(userController.login("", ""));

        User testUser = userController.getLoggedInUser();
        System.out.println(testUser);
    }
}
