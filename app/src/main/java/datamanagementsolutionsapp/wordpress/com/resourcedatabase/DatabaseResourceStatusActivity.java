/**
 *  26/05/2015
 */
package datamanagementsolutionsapp.wordpress.com.resourcedatabase;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseResourceStatusActivity extends Activity {

    public void dbWriter(String resourceItem, String resourceStatus, String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

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
            sql = "UPDATE `bookingtestdb`.`Tbl_Resources` SET `resource_status` = '" + resourceStatus + "' WHERE `Tbl_Resources`.`resource_name` ='" + resourceItem + "';";

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
        setContentView(R.layout.activity_status);

        // Allows for network operation on the applications main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Spinner itemInput, statusInput;

        Button changeStatus = (Button) findViewById(R.id.btnChangeStatus);
        itemInput = (Spinner) findViewById(R.id.spinItemInput);
        statusInput = (Spinner) findViewById(R.id.spinStatusInput);

        // An adapter to change the array of item names in the resource file to an CharSequence
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.resources, android.R.layout.simple_expandable_list_item_1);

        ArrayAdapter<CharSequence> adapterTwo = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_expandable_list_item_1);

        // Sets the items on the spinner to the CharSequence
        itemInput.setAdapter(adapter);
        statusInput.setAdapter(adapterTwo);

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

                String resourceItem = itemInput.getSelectedItem().toString();
                String resourceStatus = statusInput.getSelectedItem().toString();
                dbWriter(resourceItem, resourceStatus, DB_URL, USER, PASS, JDBC_DRIVER);

            }
        });

    }
}
