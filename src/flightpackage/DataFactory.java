package flightpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

public class DataFactory implements DataFactoryInterface {
    String DATABASE_URL;

    public DataFactory() {
        File directory = new File("./src/database");
        try {
            DATABASE_URL = "jdbc:sqlite:" + directory.getCanonicalPath() + "/flight.db";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();

        User user1 = new User( /*kennitala:*/ 1, /*name*/ "Hannes",  /*email*/ "hannes@ikea.is");
        User user2 = new User( /*kennitala:*/ 2, /*name*/ "Röggi",  /*email*/ "roggi@hakkari.is");
        User user3 = new User( /*kennitala:*/ 3, /*name*/ "Tóti",  /*email*/ "Toti@flugger.com");
        User user4 = new User( /*kennitala:*/ 80085, /*name*/ "Ahmed Kaboom",  /*email*/ "Ahmed@bomb.bomb");

        /*
        ObservableList<Flight> flights = null;
        try {
            flights = getFlights();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Seat> seats = getSeats();

        ArrayList<Booking> bookings1 = new ArrayList<>();
        bookings1.add(new Booking(flights.get(0), user1, seats.get(0)));
        user1.setBookings(bookings1);

        ArrayList<Booking> bookings2 = new ArrayList<>();
        bookings2.add(new Booking(flights.get(1), user2, seats.get(3)));
        user2.setBookings((bookings2));

        ArrayList<Booking> bookings3 = new ArrayList<>();
        bookings3.add(new Booking(flights.get(2), user3, seats.get(7)));
        user3.setBookings((bookings3));

        ArrayList<Booking> bookings4 = new ArrayList<>();
        bookings4.add(new Booking(flights.get(1), user4, seats.get(6)));
        user4.setBookings((bookings4));
        */

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        return users;
    }

    public ObservableList<Seat> getSeats(int flight_id){
        String id = Integer.toString(flight_id);
        ObservableList<Seat> seats = FXCollections.observableArrayList();

        String query = "SELECT * FROM seats WHERE flight_id = ?";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                // read the result set
                seats.add(new Seat(
                        rs.getInt("flight_id"),
                        rs.getInt("seatID"),
                        rs.getBoolean("isAvailable"),
                        rs.getBoolean("isFirstClass"),
                        rs.getBoolean("isEmergency")
                ));
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return seats;
    }

    public ObservableList<Flight> getFlights() throws IOException {
        ObservableList<Flight> flights = FXCollections.observableArrayList();

        String query = "select * from flights";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                // read the result set
                flights.add(new Flight(
                        rs.getInt("id"),
                        rs.getString("departureLocation"),
                        rs.getString("arrivalLocation"),
                        rs.getString("departureTime"),
                        rs.getString("arrivalTime"),
                        rs.getString("flightDate"),
                        rs.getInt("price"),
                        rs.getString("airline"),
                        rs.getBoolean("mealService")
                ));
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return flights;
    }

    public static void main(String[] args){
        DataFactory dataFactory = new DataFactory();
        try {
            ObservableList<Flight> flights = dataFactory.getFlights();
            for (Flight flight: flights) {
                System.out.println(flight);
            }
            ObservableList<Seat> seats = dataFactory.getSeats(2);
            for (Seat s: seats) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
