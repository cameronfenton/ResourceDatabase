package datamanagementsolutionsapp.wordpress.com.resourcedatabase;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReaderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // Allows for network operation on the applications main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final EditText txtDatabaseOutput = (EditText) findViewById(R.id.txtDatabaseOutput);

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

        Connection conn = null;
        Statement stmt = null;
        String sql;

        try {

            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER).newInstance();

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            sql = "SELECT id, first, last FROM Tbl_Names";
            ResultSet rs;
            rs = stmt.executeQuery(sql);

            // STEP 5: Extract data from result set
            while (rs.next()) {

                // Retrieve by column name
                int id = rs.getInt("id");
                String first = rs.getString("first");
                String last = rs.getString("last");

                // Prints values to console
                System.out.print("ID: " + id);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);

                // Appends values to the GUI's EditText
                txtDatabaseOutput.append("\nID: " + id);
                txtDatabaseOutput.append("\nFirst: " + first);
                txtDatabaseOutput.append("\nLast: " + last);

            }

            // STEP 6: Clean-up environment
            rs.close();
            stmt.close();
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
                if (stmt != null) {

                    stmt.close();

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
