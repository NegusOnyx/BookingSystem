package queue;

import entity.Booking;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BookingQueue {
    // Queue to store booking requests
    private BlockingQueue<Booking> bookingQueue = new LinkedBlockingQueue<>();

    // Method to add a booking to the queue
    public void addBooking(Booking booking) {
        try {
            bookingQueue.put(booking);
            System.out.println("Added to queue: " + booking);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Method to retrieve and remove a booking from the queue
    public Booking takeBooking() throws InterruptedException {
        return bookingQueue.take();
    }

    public boolean isEmpty() {
        return bookingQueue.isEmpty();
    }
}

