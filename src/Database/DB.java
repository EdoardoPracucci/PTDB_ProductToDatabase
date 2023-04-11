package Database;

import java.sql.*;

public class DB {
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "/home/edo/Documents/Repository/PTDB_ProductToDatabase/PTDB_ProductToDB/src/Database/MyDB.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Connection getConnect() {
        String url = "jdbc:sqlite:/home/edo/Documents/Repository/PTDB_ProductToDatabase/PTDB_ProductToDB/src/Database/MyDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:/home/edo/Documents/Repository/PTDB_ProductToDatabase/PTDB_ProductToDB/src/Database/" + fileName;

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        DB db = new DB();
        db.insertProduct(1,"sedia",18.10);
        db.insertProduct(2,"tavolo",60);
        db.selectAll();
    }

    public void insertProduct(int id,String name, double price){
        String sql= "insert into products(id,name,price) VALUES (?,?,?)";
        try (Connection conn = this.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createNewTable(){
            Connection c = null;
            Statement stmt = null;
            try {

                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:/home/edo/Documents/Repository/PTDB_ProductToDatabase/PTDB_ProductToDB/src/Database/MyDB.db");
                System.out.println("Database Opened...\n");
                stmt = c.createStatement();
                // SQL statement for creating a new table
                String sql = "CREATE TABLE IF NOT EXISTS products (\n"
                        + "	id integer PRIMARY KEY,\n"
                        + "	name text NOT NULL,\n"
                        + "	price double NOT NULL\n"
                        + ");";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            }
            catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Table Products Created Successfully!!!");
    }

    public void selectAll(){
        String sql = "SELECT id, name, price FROM products";

        try (Connection conn = this.getConnect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
