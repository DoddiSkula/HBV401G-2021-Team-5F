package flightpackage;

import javafx.collections.ObservableList;

public class FlightUserController {
    private final DataFactory dataFactory = new DataFactory();
    private User user;

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
