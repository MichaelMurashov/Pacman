package sample.model;

import java.sql.*;

public class Database implements IDatabase {

    private Connection connection = null;
    private Statement statement = null;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pacman","root", "root");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public String CheckPassword(String userName, String password) {
        String answer = "not found";
        String selectTableSQL = "SELECT name, password FROM users WHERE name = '" + userName + "'";
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectTableSQL);
            if (result.next()) {
                if (result.getString("Password").equals(password))
                    answer = "ok";
                else
                    answer = "incorrect";
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return answer;
    }

    public void InsertNewUser(String userName, String password) {
        String insertTableSQL = "INSERT INTO users"
                + " (name, password) "
                + "VALUES ('" + userName + "','" + password + "')";
        try {
           statement = connection.createStatement();
            statement.executeUpdate(insertTableSQL);
           statement.close();
       } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
