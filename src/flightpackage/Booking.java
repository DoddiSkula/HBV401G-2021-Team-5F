package flightpackage;

public class Booking {
    private Flight flight;
    private User user;
    private Seat seat;


    public Booking(Flight flight, User user, Seat seat) {
        this.flight = flight;
        this.user = user;
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}