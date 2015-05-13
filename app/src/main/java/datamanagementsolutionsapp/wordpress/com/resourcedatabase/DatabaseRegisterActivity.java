package datamanagementsolutionsapp.wordpress.com.resourcedatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseRegisterActivity extends Activity {

    public void dbWriter(String username, String password, String verifyPassword) {

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

            // Insert query
            sql = "INSERT INTO Tbl_Names (username, password) " +
                    "VALUES ('" + username + "', '" + password + "')";

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
        setContentView(R.layout.activity_register);
        // Allows for network operation on the applications main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button register;

        final EditText username, password, verifyPassword;

        register = (Button) findViewById(R.id.btnRegister);

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        verifyPassword = (EditText) findViewById(R.id.txtVerifyPassword);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernameString = String.valueOf(username.getText());
                final String passwordString = String.valueOf(password.getText());
                final String verifyPasswordString = String.valueOf(verifyPassword.getText());

                System.out.println(passwordString);
                System.out.println(verifyPasswordString);

                if (usernameString.length() >= 1) {

                    if (passwordString.length() >= 1) {

                        if (verifyPasswordString.length() >= 1) {

                            if (passwordString.equals(verifyPasswordString)) {

                                // calls the method which writes the username to the database
                                dbWriter(usernameString, passwordString, verifyPasswordString);

                                startActivity(new Intent(getApplicationContext(), QRSCannerActivity.class));

                            } else {

                                Toast.makeText(getApplicationContext(),
                                        "Passwords are different!!!", Toast.LENGTH_LONG).show();

                            }

                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Enter your password again", Toast.LENGTH_LONG).show();

                        }

                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Enter a password", Toast.LENGTH_LONG).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(),
                            "Enter a username", Toast.LENGTH_LONG).show();

                }


            }

        });

    }

}
