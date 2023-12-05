import org.apache.commons.lang3.StringUtils;

import java.sql.*;

public class User {

    private static int userID = -1;


    public void addUser(String userID, String phoneNum, String fullName, String username, String email, String password) {
        if (userID == null || phoneNum == null || fullName == null || username == null || email == null || password == null) {
            System.out.println("Please enter a valid entry.");
            return;
        }

        if (!StringUtils.isNumeric(phoneNum)) {
            System.out.println("Please enter the correct integers.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");

            String insertQuery = "INSERT INTO Users (phone_num, email, fullname, UserID, username, password,role) " +
                    "VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

            insertStatement.setInt(1, Integer.parseInt(phoneNum));
            insertStatement.setString(2, email.toLowerCase().trim());
            insertStatement.setString(3, fullName.toUpperCase().trim());
            insertStatement.setInt(4, Integer.parseInt(userID));
            insertStatement.setString(5, username.toLowerCase().trim());
            insertStatement.setString(6, PasswordHandler.encryptPassword(password));
            insertStatement.setString(7, "user");

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added successfully. Your userID is " + userID + ". Remember this to login in the future.");
            } else {
                System.out.println("Failed to add user");
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error performing database operation: " + e.getMessage());
        }
    }

    public static boolean checkDuplicateKeys(String userID, String username) {
        if (userID == null || username == null) {
            System.out.println("Please enter a valid entry.");
            return true;
        }

        if (!StringUtils.isNumeric(userID)) {
            System.out.println("Please enter the correct integers.");
            return true;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            // Check if the UserID or username already exists
            String checkQuery = "SELECT COUNT(*) FROM Users WHERE UserID = ? OR username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, Integer.parseInt(userID));
            checkStatement.setString(2, username.toLowerCase().trim());
            ResultSet checkResultSet = checkStatement.executeQuery();

            if (checkResultSet.getInt(1) > 0) {
                System.out.println("\nUserID or username already exists. Please choose another.");
                connection.close();
                return true;
            }

            connection.close();

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("UserID or username already exists. Please choose another.");
            } else {
                System.err.println("Error performing database operation: " + e.getMessage());
            }
        }

        return false;
    }

    public static boolean checkUserIdOrUsernameTaken(String column, String value) {
        if (column == null || value == null) {
            System.out.println("Please enter a valid entry.");
            return true;
        }

        String sql = "SELECT COUNT(*) FROM Users WHERE " + column + " = ?";

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            if (column.equals("UserID")) {
                preparedStatement.setInt(1, Integer.parseInt(value));
            } else {
                preparedStatement.setString(1, value);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            int count = resultSet.getInt(1);

            resultSet.close();
            connection.close();

            return count > 0;

        } catch (SQLException e) {
            System.err.println("Error checking for uniqueness: " + e.getMessage());
            return true;  // Default to true for safety
        }
    }

    public static boolean checkUserLogin(String username, String inputPassword) {
        if (username == null || inputPassword == null) {
            return false;
        }

        String dbPassword = null; // To store password fetched from the database

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");

            String selectQuery = "SELECT password FROM Users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dbPassword = resultSet.getString("password");
            }
            // getting userID of the user when they login
            String sql = "SELECT UserID FROM Users WHERE username = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
            preparedStatement2.setString(1, username);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if (resultSet2.next()) {
                userID = resultSet2.getInt("UserID");
            }

            connection.close();
            // Assuming pwdHandler.match() checks the input password against the hashed password from the DB
            return PasswordHandler.checkPassword(inputPassword, dbPassword);

        } catch (SQLException e) {
            System.err.println("Error performing database operation: " + e.getMessage());
            return false; // Return false on exception
        }
    }

    public static int getUserID() { return userID; }

    public static void updateUserProfile(String updateColumn, String inputValue) {
        if (updateColumn == null || inputValue == null) {
            System.out.println("Please enter a valid entry.");
            return;
        }

        // should do checks for input value
        if (updateColumn.equals("fullname")) {
            inputValue = inputValue.toUpperCase().trim();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");

            String sql = "UPDATE Users SET " + updateColumn + " = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, inputValue);
            preparedStatement.setInt(2, userID);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Profile information updated successfully!");
                if (updateColumn.equals("UserID")) {
                    userID = Integer.parseInt(inputValue);
                }
            } else {
                System.out.println("Failed to update profile information.");
            }

            connection.close();

        } catch (SQLException e) {
            System.err.println("Error updating profile information: " + e.getMessage());
        }
    }

    public static void showUserInfo(String username) {
        if (username == null) {
            System.out.println("Please enter a valid username.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            String sql = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username.toLowerCase().trim()); // Assuming usernames are stored in lowercase and trimmed

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {  // Check if a row is available in the ResultSet
                userID = resultSet.getInt("UserID");
                String userFullName = resultSet.getString("fullname");
                String userEmail = resultSet.getString("email");
                String userPhoneNum = resultSet.getString("phone_num");

                System.out.println("UserID: " + userID);
                System.out.println("Username: " + username);
                System.out.println("Full Name: " + userFullName);
                System.out.println("Email: " + userEmail);
                System.out.println("Phone Number: " + userPhoneNum);
                System.out.println("Password: " + "**********");
            } else {
                System.out.println("No user found with username: " + username);
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error performing database operation: " + e.getMessage());
        }
    }

    public static void setUserIDToInvalid() {
        // this is purposely used for testing...
        userID = -1;
    }
}