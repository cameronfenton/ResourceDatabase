/**
 * 26/05/2015
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DatabaseResourceStatusActivity extends Activity {

    public ArrayList<String> resources = new ArrayList<>();

    public void dbReader(String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

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

            int i = 0;

            // STEP 5: Extract data from result set
            while (rs.next()) {

                // Retrieve by column name
                int id = rs.getInt("id");
                String resource_name = rs.getString("resource_name");
                String resource_status = rs.getString("resource_status");

                resources.add(resource_name);
                System.out.println(resources.get(i));

                i++;

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

    }

    public void dbWriter_user(String resourceItem, String user_type, String resource_user, String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

        Boolean wrote;
        Integer status;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql;

        if (user_type.equals("administrator")) {

            try {
                // Register JDBC driver
                Class.forName(JDBC_DRIVER).newInstance();

                // Open a connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // Insert query
                sql = "UPDATE `bookingtestdb`.`Tbl_Resources` SET `resource_User` = '" + resource_user + "' WHERE `Tbl_Resources`.`resource_name` ='" + resourceItem + "';";

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


            // Updates time signed in/out
            // Call new method
        }


    }

    public void dbWriter(String resourceItem, String user_type, String resource_user, String resourceStatus, String DB_URL, String USER, String PASS, String JDBC_DRIVER) {

        System.out.println(user_type);
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

        dbWriter_user(resourceItem, user_type, resource_user, DB_URL, USER, PASS, JDBC_DRIVER);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

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

        final Spinner itemInput, statusInput;

        final String user_type = getIntent().getStringExtra("user_type");

        final String resource_user = getIntent().getStringExtra("resource_user");


        Button changeStatus = (Button) findViewById(R.id.btnChangeStatus);
        itemInput = (Spinner) findViewById(R.id.spinItemInput);
        statusInput = (Spinner) findViewById(R.id.spinStatusInput);

        List<CharSequence> list = new ArrayList<>();

        dbReader(DB_URL, USER, PASS, JDBC_DRIVER);

        for (int i = 0; i < resources.size(); i++) {

            list.add(resources.get(i));

        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list);
        System.out.println("created array adapter");

        ArrayAdapter<CharSequence> adapterTwo = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_dropdown_item);

        // Sets the items on the spinner to the CharSequence
        itemInput.setAdapter(adapter);
        statusInput.setAdapter(adapterTwo);

        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String resourceItem = itemInput.getSelectedItem().toString();
                String resourceStatus = statusInput.getSelectedItem().toString();

                System.out.println("UserType: " + user_type);
                dbWriter(resourceItem, user_type, resource_user, resourceStatus, DB_URL, USER, PASS, JDBC_DRIVER);
            }

        });

    }

}
