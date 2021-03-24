package sample;

import java.util.ArrayList;

public class User {
    private int kennitala;
    private String email;
    private String name;
    private ArrayList<Booking> bookings;

    @Override
    public String toString(){
        return name;
    }

    public User(int kennitala, String name, String email) {
        this.kennitala = kennitala;
        this.email = email;
        this.name = name;
        this.bookings = new ArrayList<>();
    }

    public int getKennitala() {
        return kennitala;
    }

    public void setKennitala(int kennitala) {
        this.kennitala = kennitala;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }
}

