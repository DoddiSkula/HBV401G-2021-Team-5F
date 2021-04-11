package flightpackage;

class UserData {
    // static variable single_instance of type Singleton
    private static UserData single_instance = null;

    // variable of type String
    public User user;
    public Flight flight;
    public Seat seat;

    // private constructor restricted to this class itself
    private UserData() {
        user = null;
        flight = null;
        seat = null;
    }

    // static method to create instance of Singleton class
    public static UserData getInstance() {
        if (single_instance == null)
        {
            single_instance = new UserData();
        }
        return single_instance;
    }
}
