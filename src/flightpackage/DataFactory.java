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
    public ObservableList<User> getUsers(String email) {
        ObservableList<User> users = FXCollections.observableArrayList();

        String query = "SELECT * FROM users WHERE email LIKE ?";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
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

    public User createUser(String name, String email, String password) {
        User user = new User(name, email, password);

        String query =  "INSERT INTO users VALUES (?, ?, ?)";

        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
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
        return user;
    }

    public ObservableList<Booking> getBookings(String user_email) {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();

        String query = "SELECT * FROM bookings WHERE user_email LIKE ?";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, user_email);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                // read the result set
                int flightId = rs.getInt("flight_id");
                String userEmail = rs.getString("user_email");
                int seatId = rs.getInt("seat_id");

                Flight flight = getFlightbyID(flightId);
                ObservableList<User> users = getUsers(userEmail);
                Seat seat = getSeat(flightId, seatId);

                bookings.add(new Booking(flight, users.get(0), seat));
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
        return bookings;
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
     * Skilar einu sæti
     *
     * @param flight_id flugnúmer
     * @param seatID sætisnúmer
     * @return sæti
     */
    public Seat getSeat(int flight_id, int seatID) {
        String id = Integer.toString(flight_id);
        String seatId = Integer.toString(seatID);
        Seat seat = null;

        String query = "SELECT * FROM seats WHERE flight_id = ? AND seatID = ?";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, seatId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                // read the result set
                seat = new Seat( rs.getInt("flight_id"),
                        rs.getInt("seatID"),
                        rs.getBoolean("isAvailable"),
                        rs.getBoolean("isFirstClass"),
                        rs.getBoolean("isEmergency"));
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

        return seat;
    }

    /**
     * Bókar sæti
     *
     * @param flight_id flugnúmer
     * @param seatID sætisnúmer
     * @param isAvailable laust/frátekið
     */
    public void reserveSeat(int flight_id, int seatID, boolean isAvailable) {
        String flightId = Integer.toString(flight_id);
        String seatId = Integer.toString(seatID);
        String availability = Boolean.toString(isAvailable);

        String query = "UPDATE seats SET isAvailable = ? WHERE flight_id = ? AND seatID = ?";

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, availability);
            pstmt.setString(2, flightId);
            pstmt.setString(3, seatId);
            pstmt.executeUpdate();
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
    }

    /**
     * Sækir flug eftir leit,
     * parametri = '%' ef ekki á að leita eftir honum.
     *
     * @param departureLocation brottfararstaður
     * @param arrivalLocation komustaður
     * @param flightDate dagsetning flugs (ár-mánuður-dags)
     * @param mealService er flug með veitingasölu eða ekki
     *
     * @return listi af flugum
     */
    public ObservableList<Flight> getFlights(String departureLocation, String arrivalLocation, String flightDate, Boolean mealService) {
        ObservableList<Flight> flights = FXCollections.observableArrayList();

        String query =  "SELECT * FROM flights WHERE departureLocation LIKE ? AND arrivalLocation LIKE ? AND flightDate LIKE ?";

        if (mealService != null && mealService)  {
            query = "SELECT * FROM flights WHERE departureLocation LIKE ? AND arrivalLocation LIKE ? AND flightDate LIKE ? AND mealService";
        }

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

    public Flight getFlightbyID(int flight_id) {
        String id = Integer.toString(flight_id);
        Flight flight = null;

        String query =  "SELECT * FROM flights WHERE id = ?";


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
                flight = new Flight(
                        rs.getInt("id"),
                        rs.getString("departureLocation"),
                        rs.getString("arrivalLocation"),
                        rs.getString("departureTime"),
                        rs.getString("arrivalTime"),
                        rs.getString("flightDate"),
                        rs.getInt("price"),
                        rs.getString("airline"),
                        rs.getBoolean("mealService")
                );
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
        return flight;
    }

    public static void main(String[] args) throws IOException {
        DataFactory dataFactory = new DataFactory();
        /*
        ObservableList<Flight> flights = dataFactory.getFlights("%", "%", "%", null);
        for (Flight flight: flights) {
            System.out.println(flight);
        }
        ObservableList<Seat> seats = dataFactory.getSeats(1);
        for (Seat seat: seats) {
            System.out.println(seat);
        }
        dataFactory.createUser("test2", "test2@test.com", "1234");
        ObservableList<User> users = dataFactory.getUsers("%");
        for (User user: users) {
            System.out.println(user);
        }


        Seat s = dataFactory.getSeat(1,1);
        System.out.println(s);
        dataFactory.reserveSeat(1,1,false);
        Seat s2 = dataFactory.getSeat(1,1);
        System.out.println(s2);

         */

        ObservableList<Booking> bookings = dataFactory.getBookings("%");
        for (Booking b: bookings) {
            System.out.println(b);
        }


    }
}
