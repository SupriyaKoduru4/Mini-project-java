package com.moviebooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheaterDAO {

	    // Display all theaters
	    public void viewTheaters() throws SQLException {

	        String sql = "SELECT * FROM theaters ORDER BY theater_id";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n===== Theaters =====");

	            boolean found = false;

	            while (rs.next()) {
	                found = true;

	                System.out.println( "Theater ID: " + rs.getInt("theater_id") + " | Name: " + rs.getString("theater_name") + " | Location: " + rs.getString("location") + " | Total Seats: " + rs.getInt("total_seats"));
	            }

	            if (!found) {
	                System.out.println("No theaters found.");
	            }
	        }
	    }

	    // Display all shows
	    public void viewShows() throws SQLException {

	        String sql = "SELECT sh.show_id, m.title, t.theater_name, " + "t.location, sh.show_date, sh.show_time, " + "sh.ticket_price, t.total_seats "
	                + "FROM shows sh " + "JOIN movies m ON sh.movie_id = m.movie_id "  + "JOIN theaters t ON sh.theater_id = t.theater_id " + "ORDER BY sh.show_date, sh.show_time";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n===== Available shows =====");

	            boolean found = false;

	            while (rs.next()) {
	                found = true;

	                System.out.println( "Show ID: " + rs.getInt("show_id") + " | Movie: " + rs.getString("title") + " | Theater: " + rs.getString("theater_name") + " | Location: " + rs.getString("location") + " | Date: " + rs.getDate("show_date")
	                        + " | Time: " + rs.getTime("show_time") + " | Price: Rs. " + rs.getBigDecimal("ticket_price") + " | Seats: " + rs.getInt("total_seats"));
	            }

	            if (!found) {
	                System.out.println("No shows available.");
	            }
	        }
	    }
	}

