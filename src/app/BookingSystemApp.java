package app;

import bl.BookingProcessor;
import dal.DatabaseHandler;
import entity.Booking;
import queue.BookingQueue;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookingSystemApp {
    public static void main(String[] args) {
        BookingQueue bookingQueue = new BookingQueue();

        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            BookingProcessor processor = new BookingProcessor(bookingQueue, dbHandler);

            processor.startValidationThread(); // Start the validation thread

            // Simulating adding bookings
            Scanner scanner = new Scanner(System.in);
            int bookingId = 1;
            boolean running = true;

            while (running) {
                System.out.println("Enter your name to make a booking (or 'exit' to quit):");
                String userName = scanner.nextLine();
                if (userName.equalsIgnoreCase("exit")) {
                    running = false;
                    break;
                }

                double amount = 0;
                boolean validAmount = false;

                // Ensure valid amount is entered
                while (!validAmount) {
                    System.out.println("Enter amount for booking:");
                    try {
                        amount = scanner.nextDouble();
                        if (amount > 0) {
                            validAmount = true;  // Only accept positive values
                        } else {
                            System.out.println("Amount must be positive.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); // Consume the invalid input
                    }
                }

                scanner.nextLine(); // Consume newline

                Booking booking = new Booking(bookingId++, userName, amount);
                dbHandler.saveBooking(booking); // Save booking in database
                bookingQueue.addBooking(booking); // Add to queue for processing

                System.out.println("Booking for " + userName + " has been added and is being processed.");
            }

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
