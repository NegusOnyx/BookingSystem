package dal;

import entity.Booking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
    private Connection connection;

    // Connect to the database and create it if necessary
    public DatabaseHandler() throws SQLException {
        try {
            // Connect to the database and create if it doesn't exist
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDB;create=true", "APP", "123");
            System.out.println("Connected to the database.");
            createBookingsTable(); // Ensure the BOOKINGS table exists
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to connect to the database or create it.");
        }
    }

    // Create the BOOKINGS table if it doesn't exist
    private void createBookingsTable() throws SQLException {
        String createTableSQL = "CREATE TABLE BOOKINGS (" +
                                "bookingId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                                "userName VARCHAR(100), " +
                                "status VARCHAR(50), " +
                                "amount DOUBLE)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("BOOKINGS table created.");
        } catch (SQLException e) {
            // Handle the case where the table already exists (which throws an exception)
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("BOOKINGS table already exists.");
            } else {
                throw e;
            }
        }
    }

    // Save a new booking to the database
    public void saveBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO BOOKINGS (userName, status, amount) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, booking.getUserName());
        ps.setString(2, booking.getStatus());
        ps.setDouble(3, booking.getAmount());
        ps.executeUpdate();
        System.out.println("Booking saved: " + booking);
    }

    // Update the status of a booking
    public void updateBookingStatus(Booking booking) throws SQLException {
        String sql = "UPDATE BOOKINGS SET status = ? WHERE bookingId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, booking.getStatus());
        ps.setInt(2, booking.getBookingId());
        ps.executeUpdate();
        System.out.println("Booking status updated: " + booking);
    }
}
