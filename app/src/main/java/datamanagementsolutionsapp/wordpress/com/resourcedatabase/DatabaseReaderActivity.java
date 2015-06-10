/**
 * Resource Database
 *
 * @author Cameron Fenton, Matthew Lepain, James Kerr, Timothy Patton
 * @date 10/06/2015
 * Basic QR code and Barcode scanner scanner prototype,
 * also a link to our site is included as a QR.
 * Edit and add to the application as you see necessary also feel free to make corrections
 * and improvements
 */

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

    public void readusers(String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

        final EditText txtDatabaseOutput = (EditText) findViewById(R.id.txtDatabaseOutput);

        txtDatabaseOutput.setText("");
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

            sql = "SELECT id, username, password FROM Tbl_Names";
            ResultSet rs;
            rs = stmt.executeQuery(sql);

            txtDatabaseOutput.setText("All current users: ");

            // STEP 5: Extract data from result set
            while (rs.next()) {

                // Retrieve by column name
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");

                // Appends values to the GUI's EditText
                txtDatabaseOutput.append("\nID: " + id);
                txtDatabaseOutput.append("\nFirst: " + username);
                txtDatabaseOutput.append("\nLast: " + password);

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

    public void readResources(String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

        final EditText txtDatabaseOutputTwo = (EditText) findViewById(R.id.txtDatabaseOutputTwo);

        txtDatabaseOutputTwo.setText("");
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

            sql = "SELECT id, resource_name, resource_status FROM Tbl_Resources";
            ResultSet rs;
            rs = stmt.executeQuery(sql);

            txtDatabaseOutputTwo.setText("All current resources: ");

            // STEP 5: Extract data from result set
            while (rs.next()) {

                // Retrieve by column name
                int id = rs.getInt("id");
                String name = rs.getString("resource_name");
                String status = rs.getString("resource_status");

                // Appends values to the GUI's EditText
                txtDatabaseOutputTwo.append("\nID: " + id);
                txtDatabaseOutputTwo.append("\nResource: " + name);
                txtDatabaseOutputTwo.append("\nIs signed out: " + status);

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

        readusers(DB_URL, USER, PASS, JDBC_DRIVER);
        readResources(DB_URL, USER, PASS, JDBC_DRIVER);

    }
}
