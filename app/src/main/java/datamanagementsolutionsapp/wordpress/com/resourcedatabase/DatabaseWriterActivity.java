package datamanagementsolutionsapp.wordpress.com.resourcedatabase;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseWriterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        // Allows for network operation on the applications main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // JDBC driver
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

        /**
         * This is the "hostname" & credentials
         * host: mysql.skulestuff.com
         * database name: bookingtestdb
         * user: datams password: datamanagement
         */
        final String DB_URL = "jdbc:mysql://mysql.skulestuff.com/bookingtestdb";
        final String USER = "datams";
        final String PASS = "datamanagement";

        Boolean wrote;
        Integer status;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER).newInstance();

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Query
            sql = "INSERT INTO Tbl_Names (id, first, last) VALUES ('5', 'Bob', 'Smith')";

            // Execute a query
            System.out.println("Creating statement...");
            preparedStatement = conn.prepareStatement(sql);
            status = preparedStatement.executeUpdate();

            // Checks if values were written to database
            if (status == 1) {
                wrote = true;
            } else {
                wrote = false;
            }

            System.out.println("Values written to the database: " + wrote);

            // Clean-up environment
            preparedStatement.close();
            conn.close();

        } catch (SQLException se) {

            // Handle errors for JDBC
            se.printStackTrace();

        } catch (Exception e) {

            // Handle errors for Class.forName
            e.printStackTrace();

        } finally {

            // Finally block used to close resources

            try {
                if (preparedStatement != null) {

                    preparedStatement.close();

                }
            } catch (SQLException se2) {

                // Nothing we can do

            }
            try {

                if (conn != null) {

                    conn.close();

                }

            } catch (SQLException se) {

                se.printStackTrace();

            } // End finally try

        } // End try

        System.out.println("Goodbye!");


    }
}
