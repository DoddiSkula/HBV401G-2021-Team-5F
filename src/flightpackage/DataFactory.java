package flightpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    /**
     * Sækir alla notendur.
     *
     * @return listi af notendum
     */
    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();

        String query = "select * from users";

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
                users.add(new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
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
        return users;
    }

    /**
     * Sækir öll sæti í tilteknu flugi.
     *
     * @param flight_id flugnúmer
     * @return listi af sætum
     */
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

    /**
     * Sækir flug eftir leit,
     * parametri = '%' ef ekki á að leita eftir honum.
     *
     * @param departureLocation brottfararstaður
     * @param arrivalLocation komustaður
     * @param flightDate dagsetning flugs (ár-mánuður-dags)
     * @return listi af flugum
     */
    public ObservableList<Flight> getFlights(String departureLocation, String arrivalLocation, String flightDate) {
        ObservableList<Flight> flights = FXCollections.observableArrayList();

        String query = "SELECT * FROM flights WHERE departureLocation LIKE ? AND arrivalLocation LIKE ? AND flightDate LIKE ?";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, departureLocation);
            pstmt.setString(2, arrivalLocation);
            pstmt.setString(3, flightDate);
            ResultSet rs = pstmt.executeQuery();
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

    public static void main(String[] args) throws IOException {
        DataFactory dataFactory = new DataFactory();
        ObservableList<Flight> flights = dataFactory.getFlights("%", "%", "%");
        for (Flight flight: flights) {
            System.out.println(flight);
        }
        /*
        ObservableList<Seat> seats = dataFactory.getSeats(1);
        for (Seat seat: seats) {
            System.out.println(seat);
        }
        ObservableList<User> users = dataFactory.getUsers();
        for (User user: users) {
            System.out.println(user);
        }
        */

    }
}
