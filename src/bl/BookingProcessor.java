package bl;

import dal.DatabaseHandler;
import entity.Booking;
import queue.BookingQueue;

public class BookingProcessor {
    private BookingQueue bookingQueue; // Shared queue
    private DatabaseHandler dbHandler; // Handle DB operations

    public BookingProcessor(BookingQueue bookingQueue, DatabaseHandler dbHandler) {
        this.bookingQueue = bookingQueue;
        this.dbHandler = dbHandler;
    }

    // Thread to validate bookings
    public void startValidationThread() {
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {  // Use this condition to allow interruption
                try {
                    Booking booking = bookingQueue.takeBooking();  // BlockingQueue takes care of synchronization
                    System.out.println("Validating booking: " + booking);
                    booking.setStatus("Validated");

                    // Ensure dbHandler is not null and booking is valid
                    if (dbHandler != null && booking != null) {
                        dbHandler.updateBookingStatus(booking);
                        System.out.println("Booking validated: " + booking);
                    } else {
                        System.out.println("Error: DatabaseHandler or Booking is null");
                    }

                    // After validation, pass it for payment
                    processPayment(booking);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // Exit thread on interruption
                } catch (Exception e) {
                    e.printStackTrace();  // Catch potential errors
                }
            }
        }).start();
    }

    // Thread to process payments
    public void processPayment(Booking booking) {
        new Thread(() -> {
            try {
                System.out.println("Processing payment for: " + booking);
                Thread.sleep(2000); // Simulate payment processing time
                booking.setStatus("Paid");

                if (dbHandler != null) {
                    dbHandler.updateBookingStatus(booking);
                    System.out.println("Payment processed: " + booking);
                } else {
                    System.out.println("Error: DatabaseHandler is null");
                }

                // After payment, confirm booking
                confirmBooking(booking);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Thread to confirm bookings
    public void confirmBooking(Booking booking) {
        new Thread(() -> {
            try {
                System.out.println("Confirming booking: " + booking);
                booking.setStatus("Confirmed");

                if (dbHandler != null) {
                    dbHandler.updateBookingStatus(booking);
                    System.out.println("Booking confirmed: " + booking);
                } else {
                    System.out.println("Error: DatabaseHandler is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
