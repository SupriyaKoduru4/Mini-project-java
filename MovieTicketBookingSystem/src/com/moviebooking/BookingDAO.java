package com.moviebooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingDAO {

    // Book a movie ticket using a transaction
    public void bookTicket( int showId, String customerName, int seatNumber) throws SQLException {

        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return;
        }

        if (seatNumber <= 0) {
            System.out.println("Seat number must be greater than zero.");
            return;
        }

        String showSql = "SELECT sh.ticket_price, t.total_seats " + "FROM shows sh " + "JOIN theaters t ON sh.theater_id = t.theater_id " + "WHERE sh.show_id = ? FOR UPDATE";

        String insertSql = "INSERT INTO bookings " + "(show_id, customer_name, seat_number, ticket_price) " + "VALUES (?, ?, ?, ?)";

        int bookingId = 0;

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                // Lock the selected show row while validating/booking.
                try (PreparedStatement ps = con.prepareStatement(showSql)) {

                    ps.setInt(1, showId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException( "Show ID not found.");
                        }

                        int totalSeats = rs.getInt("total_seats");
                        java.math.BigDecimal price = rs.getBigDecimal("ticket_price");

                        if (seatNumber > totalSeats) {
                            throw new SQLException( "Invalid seat. This theater has " + totalSeats + " seats.");
                        }

                        // prevents the same seat being booked twice.
                        try (PreparedStatement insert = con.prepareStatement( insertSql, Statement.RETURN_GENERATED_KEYS)) {

                            insert.setInt(1, showId);
                            insert.setString(2, customerName.trim());
                            insert.setInt(3, seatNumber);
                            insert.setBigDecimal(4, price);
                            insert.executeUpdate();

                            try (ResultSet keys = insert.getGeneratedKeys()) {
                                if (keys.next()) {
                                    bookingId = keys.getInt(1);
                                } else {
                                    throw new SQLException("Could not generate booking ID.");
                                }
                            }
                        }
                    }
                }
                con.commit();

            } catch (SQLException e) {
                con.rollback();

                if ("23000".equals(e.getSQLState())) {
                    System.out.println("Booking failed: this seat is already booked " + "for the selected show.");
                    return;
                }

                if (e.getMessage() != null && (e.getMessage().contains("Show ID not found") || e.getMessage().contains("Invalid seat"))) {
                    System.out.println(e.getMessage());
                    return;
                }
                throw e;

            } finally {
                con.setAutoCommit(true);
            }
        }

        System.out.println("\nTicket booked successfully!");
        generateTicket(bookingId);
    }

    // Generate and display ticket details
    public void generateTicket(int bookingId) throws SQLException {

        String sql = "SELECT b.booking_id, b.customer_name, " + "b.seat_number, b.booking_time, b.ticket_price, " + "m.title, t.theater_name, t.location, " + "sh.show_date, sh.show_time " + "FROM bookings b "
                + "JOIN shows sh ON b.show_id = sh.show_id "  + "JOIN movies m ON sh.movie_id = m.movie_id " + "JOIN theaters t ON sh.theater_id = t.theater_id " + "WHERE b.booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\n========== Movie Ticket ==========");
                    System.out.println("Booking ID : " + rs.getInt("booking_id"));
                    System.out.println("Customer   : " + rs.getString("customer_name"));
                    System.out.println("Movie      : " + rs.getString("title"));
                    System.out.println("Theater    : " + rs.getString("theater_name"));
                    System.out.println("Location   : " + rs.getString("location"));
                    System.out.println("Show Date  : " + rs.getDate("show_date"));
                    System.out.println("Show Time  : " + rs.getTime("show_time"));
                    System.out.println("Seat No.   : " + rs.getInt("seat_number"));
                    System.out.println("Ticket Fee : Rs. " + rs.getBigDecimal("ticket_price"));
                    System.out.println("Booked At  : " + rs.getTimestamp("booking_time"));
                    System.out.println("==================================");
                } else {
                    System.out.println("Booking ID not found.");
                }
            }
        }
    }

    // View all bookings
    public void viewBookings() throws SQLException {

        String sql = "SELECT b.booking_id, b.customer_name, " + "m.title, t.theater_name, sh.show_date, " + "sh.show_time, b.seat_number, b.ticket_price " + "FROM bookings b " + "JOIN shows sh ON b.show_id = sh.show_id "
                + "JOIN movies m ON sh.movie_id = m.movie_id " + "JOIN theaters t ON sh.theater_id = t.theater_id " + "ORDER BY b.booking_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== All Bookings =====");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("Booking ID: " + rs.getInt("booking_id") + " | Customer: " + rs.getString("customer_name") + " | Movie: " + rs.getString("title") + " | Theater: " + rs.getString("theater_name")
                        + " | Date: " + rs.getDate("show_date")+ " | Time: " + rs.getTime("show_time") + " | Seat: " + rs.getInt("seat_number") + " | Price: Rs. " + rs.getBigDecimal("ticket_price"));
            }

            if (!found) {
                System.out.println("No bookings found.");
            }
        }
    }
}