package Database;

import Model.Product;

import java.sql.*;

public class ProductDbQuery {
    DB db = new DB();

    public void createTable(){
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
    public void addInsertProduct(Product product){
        String sql= "insert into products(id,name,price) VALUES (?,?,?)";
        try (Connection conn = db.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteProduct(int productToDeleteById){
        String sql= "DELETE FROM product  WHERE id = ?";
        try (Connection conn = db.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productToDeleteById);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void selectProduct(){
        String sql = "SELECT id, name, price FROM products";

        try (Connection conn = db.getConnect();
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
