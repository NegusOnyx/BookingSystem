
package entity;


public class Booking {
    private int bookingId;
    private String userName;
    private String status; // e.g., "Pending", "Validated", "Confirmed"
    private double amount;

    public Booking(int bookingId, String userName, double amount) {
        this.bookingId = bookingId;
        this.userName = userName;
        this.amount = amount;
        this.status = "Pending"; // Initial status
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", User: " + userName + ", Status: " + status + ", Amount: $" + amount;
    }
}

