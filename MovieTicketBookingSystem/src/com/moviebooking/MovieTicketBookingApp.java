package com.moviebooking;

import java.sql.SQLException;
import java.util.Scanner;

public class MovieTicketBookingApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MovieDAO movieDAO = new MovieDAO();
        TheaterDAO theaterDAO = new TheaterDAO();
        BookingDAO bookingDAO = new BookingDAO();

        while (true) {

            System.out.println("\n===== Moving Ticket Booking System =====");
            System.out.println("1. View Movies");
            System.out.println("2. View Theaters");
            System.out.println("3. View Shows");
            System.out.println("4. Book Movie Ticket");
            System.out.println("5. View All Bookings");
            System.out.println("6. Generate Ticket Using Booking ID");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {

                    case 1:
                        movieDAO.viewMovies();
                        break;

                    case 2:
                        theaterDAO.viewTheaters();
                        break;

                    case 3:
                        theaterDAO.viewShows();
                        break;

                    case 4: {
 // Display shows before booking.
                        theaterDAO.viewShows();

                        System.out.print("Enter show ID: ");
                        int showId = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter customer name: ");
                        String customerName = sc.nextLine().trim();

                        System.out.print("Enter seat number: ");
                        int seatNumber = Integer.parseInt(sc.nextLine().trim());

                        bookingDAO.bookTicket( showId, customerName, seatNumber);
                        break;
                    }

                    case 5:
                        bookingDAO.viewBookings();
                        break;

                    case 6: {
                        System.out.print("Enter booking ID: ");
                        int bookingId = Integer.parseInt(sc.nextLine().trim());

                        bookingDAO.generateTicket(bookingId);
                        break;
                    }

                    case 0:
                        System.out.println("Application closed.");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }
}