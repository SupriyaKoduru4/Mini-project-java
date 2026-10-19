package com.moviebooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDAO {

	    public void viewMovies() throws SQLException {

	        String sql = "SELECT * FROM movies ORDER BY movie_id";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            System.out.println("\n===== Available movies =====");

	            boolean found = false;

	            while (rs.next()) {
	                found = true;

	                System.out.println( "Movie ID: " + rs.getInt("movie_id") + " | Title: " + rs.getString("title") + " | Genre: " + rs.getString("genre") + " | Language: " + rs.getString("language") + " | Duration: " + rs.getInt("duration_minutes") + " minutes" + " | Release Date: " + rs.getDate("release_date"));
	            }

	            if (!found) {
	                System.out.println("No movies available.");
	            }
	        }
	    }
	}

