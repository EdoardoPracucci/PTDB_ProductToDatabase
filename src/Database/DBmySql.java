package Database;

import java.sql.*;

public class DBmySql {
    static final String DB_URL = "jdbc:mysql://localhost/PTDB";
    static final String USER = "root";
    static final String PASS = "ciao1234";

    public static void main(String[] args) {
        //insert();
        //select();
    }

    public static void insert(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            // Execute a query
            System.out.println("Inserting records into the table...");
            String sql = "INSERT INTO Products VALUES (1, 'sedia', 18.50)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Products VALUES (2, 'tavolo', 70)";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void select(){
        final String QUERY = "SELECT id, name,price FROM Products";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                //Display values
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Name: " + rs.getString("name"));
                System.out.print(", Price: " + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertGeneric(Iterable<?> g){

    }
}
