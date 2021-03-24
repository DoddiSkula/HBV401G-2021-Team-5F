package flightpackage;

public class Seat {
    private int SeatID;
    private boolean isAvailable;
    private boolean FirstClass;
    private boolean Emergency;

    public Seat(int seatID, boolean isAvailable, boolean firstClass, boolean emergency) {
        SeatID = seatID;
        this.isAvailable = isAvailable;
        FirstClass = firstClass;
        Emergency = emergency;
    }

    public int getSeatID() {
        return SeatID;
    }

    public void setSeatID(int seatID) {
        SeatID = seatID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isFirstClass() {
        return FirstClass;
    }

    public void setFirstClass(boolean firstClass) {
        FirstClass = firstClass;
    }

    public boolean isEmergency() {
        return Emergency;
    }

    public void setEmergency(boolean emergency) {
        Emergency = emergency;
    }
}
