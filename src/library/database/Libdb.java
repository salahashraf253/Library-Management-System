package library.database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Libdb {

    private static Libdb handler = null;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public Libdb() {
        createConnection();
        setupBookTable();
        setupMEMBERTable();
    }

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void  setupBookTable() {
        String TABLE_NAME = "Book";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table" + TABLE_NAME);
            } else {
                stmt.execute("create Table" + TABLE_NAME + "("
                        +"ID varchar(200) primary key ,\n"
                        +"title varchar  (200),\n"
                        +"author varchar  (200),\n"
                        +"publisher varchar  (200),\n"
                        +"category varchar  (200),\n"
                        +"avail  boolen  defult true"
                        +")");
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage()+"........SETUP DATABASE");


        }
        finally{
        }
    }
    void  setupMEMBERTable() {
        String TABLE_NAME = "Member";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table" + TABLE_NAME);
            } else {
                stmt.execute("create Table" + TABLE_NAME + "("
                        +"ID varchar(200) primary key ,\n"
                        +"username varchar  (200),\n"
                        +"password  varchar  (200),\n"

                        +")");
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage()+"........SETUP DATABASE");


        }
        finally{
        }
    }

}