# Booking System

This is a multithreaded Java booking system for a simulated queue-based booking process, involving database interaction using Derby.

## Features
- User booking via command-line interface.
- Booking validation, payment processing, and confirmation using threads.
- Integration with Apache Derby database.

## Technologies
- Java
- Apache Derby (JDBC)
- Multithreading

## How to Run
1. Clone the repository: `git clone <your-repo-url>`
2. Open the project in your preferred IDE (NetBeans, IntelliJ, etc.).
3. Ensure Apache Derby is set up and running.
4. Run the `BookingSystemApp.java` file.

## Project Structure
- `bl`: Business logic layer.
- `dal`: Data Access Layer for database interaction.
- `entity`: Classes representing data entities.
- `queue`: Queue implementation for managing bookings.
- `app`: Main application logic.
