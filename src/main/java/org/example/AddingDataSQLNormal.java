package org.example;

import java.sql.*;
import java.util.Random;

public class AddingDataSQLNormal {

    // JDBC URL, username, and password of MySQL server
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/socialMedia";
    static final String USER = "root";
    static final String PASS = "GITESH123@bh";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS)) {

            conn.setAutoCommit(false);  // Disable auto-commit for batch processing

            // Insert Users
            insertUsers(conn, 1000000);

            // Insert Posts
            insertPosts(conn, 500000);

            // Insert Interactions
            insertInteractions(conn, 5000000);

            // Insert Ads
            insertAds(conn, 100000);

            // Insert Ad Impressions
            insertAdImpressions(conn, 300000);

            conn.commit();  // Commit all the data

            System.out.println("Data insertion complete!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUsers(Connection conn, int count) throws SQLException {
        String sql = "INSERT INTO Users (UserID, Username, JoinDate, Country) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Random rand = new Random();
            String[] countries = {"USA", "India", "UK", "Canada", "Germany", "Australia"};

            for (int i = 1; i <= count; i++) {
                pstmt.setInt(1, i);  // UserID
                pstmt.setString(2, "user_" + i);  // Username
                pstmt.setDate(3, new Date(System.currentTimeMillis() - (long) (rand.nextDouble() * 31556952000L)));  // Random JoinDate (within 1 year)
                pstmt.setString(4, countries[rand.nextInt(countries.length)]);  // Random Country

                pstmt.addBatch();

                if (i % 10000 == 0) {  // Insert in batches of 10,000
                    pstmt.executeBatch();
                    System.out.println("Inserted " + i + " users");
                }
            }
            pstmt.executeBatch();  // Insert any remaining records
        }
    }

    public static void insertPosts(Connection conn, int count) throws SQLException {
        String sql = "INSERT INTO Posts (PostID, UserID, PostType, PostDate, Content) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Random rand = new Random();
            String[] postTypes = {"text", "image", "video"};

            for (int i = 1; i <= count; i++) {
                pstmt.setInt(1, i);  // PostID
                pstmt.setInt(2, rand.nextInt(1000000) + 1);  // Random UserID from 1 to 1 million
                pstmt.setString(3, postTypes[rand.nextInt(postTypes.length)]);  // Random PostType
                pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis() - (long) (rand.nextDouble() * 31556952000L)));  // Random PostDate
                pstmt.setString(5, "This is post content for post_" + i);  // Post Content

                pstmt.addBatch();

                if (i % 10000 == 0) {  // Insert in batches of 10,000
                    pstmt.executeBatch();
                    System.out.println("Inserted " + i + " posts");
                }
            }
            pstmt.executeBatch();  // Insert any remaining records
        }
    }

    public static void insertInteractions(Connection conn, int count) throws SQLException {
        String sql = "INSERT INTO Interactions (InteractionID, UserID, PostID, InteractionType, InteractionDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Random rand = new Random();
            String[] interactionTypes = {"like", "comment", "share"};

            for (int i = 1; i <= count; i++) {
                pstmt.setInt(1, i);  // InteractionID
                pstmt.setInt(2, rand.nextInt(1000000) + 1);  // Random UserID from 1 to 1 million
                pstmt.setInt(3, rand.nextInt(500000) + 1);  // Random PostID from 1 to 500,000
                pstmt.setString(4, interactionTypes[rand.nextInt(interactionTypes.length)]);  // Random InteractionType
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis() - (long) (rand.nextDouble() * 31556952000L)));  // Random InteractionDate

                pstmt.addBatch();

                if (i % 10000 == 0) {  // Insert in batches of 10,000
                    pstmt.executeBatch();
                    System.out.println("Inserted " + i + " interactions");
                }
            }
            pstmt.executeBatch();  // Insert any remaining records
        }
    }

    public static void insertAds(Connection conn, int count) throws SQLException {
        String sql = "INSERT INTO Ads (AdID, AdveriserID, AdContent, StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Random rand = new Random();

            for (int i = 1; i <= count; i++) {
                pstmt.setInt(1, i);  // AdID
                pstmt.setInt(2, rand.nextInt(1000000) + 1);  // Random AdvertiserID from 1 to 1 million
                pstmt.setString(3, "This is ad content for ad_" + i);  // Ad Content
                pstmt.setDate(4, new Date(System.currentTimeMillis() - (long) (rand.nextDouble() * 31556952000L)));  // Random StartDate
                pstmt.setDate(5, new Date(System.currentTimeMillis() + (long) (rand.nextDouble() * 31556952000L)));  // Random EndDate

                pstmt.addBatch();

                if (i % 10000 == 0) {  // Insert in batches of 10,000
                    pstmt.executeBatch();
                    System.out.println("Inserted " + i + " ads");
                }
            }
            pstmt.executeBatch();  // Insert any remaining records
        }
    }

    public static void insertAdImpressions(Connection conn, int count) throws SQLException {
        String sql = "INSERT INTO AdImpressions (ImpressionID, AdID, UserID, ImpressionDate, Clicked) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Random rand = new Random();

            for (int i = 1; i <= count; i++) {
                pstmt.setInt(1, i);  // ImpressionID
                pstmt.setInt(2, rand.nextInt(100000) + 1);  // Random AdID from 1 to 100,000
                pstmt.setInt(3, rand.nextInt(1000000) + 1);  // Random UserID from 1 to 1 million
                pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis() - (long) (rand.nextDouble() * 31556952000L)));  // Random ImpressionDate
                pstmt.setBoolean(5, rand.nextBoolean());  // Random Clicked status (true or false)

                pstmt.addBatch();

                if (i % 10000 == 0) {  // Insert in batches of 10,000
                    pstmt.executeBatch();
                    System.out.println("Inserted " + i + " ad impressions");
                }
            }
            pstmt.executeBatch();  // Insert any remaining records
        }
    }
}
