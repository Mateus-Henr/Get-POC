import java.sql.*;
public class dbConnection {
    public static void main(String args[]){
        Class.forName("com.mysql.cj.jbdc.Driver");
        Connection connection = DriveManager.getConnectoin("jdbc:mysql://localhost:3306/", "root", "root"); // adicionar nome do db depois da "/" no primeiro parametro

    }
}
