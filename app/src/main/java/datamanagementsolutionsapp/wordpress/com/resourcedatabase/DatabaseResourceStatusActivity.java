package datamanagementsolutionsapp.wordpress.com.resourcedatabase;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Cameron on 5/13/2015.
 */
public class DatabaseResourceStatusActivity extends Activity {


    public void dbWriter(String resourceStatus, String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

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

            // Insert query
            sql = "UPDATE `bookingtestdb`.`Tbl_Resources` SET `resource_status` " +
                    "= '" + resourceStatus + "' WHERE `Tbl_Resources`.`id` =1";

            // Execute insert query
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allows for network operation on the applications main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button changeStatus = (Button) findViewById(R.id.btnChangeStatus);
        final Spinner statusInput = (Spinner) findViewById(R.id.spinStatusInput);

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

        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String resourceStatus = statusInput.getSelectedItem().toString();
                dbWriter(resourceStatus, DB_URL, USER, PASS, JDBC_DRIVER);

            }
        });

    }
}
