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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends Activity {

    public String[][] loginInfo = new String[99][4];
    public Integer idCount = -1;

    public void readPasswords() {


        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
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

            sql = "SELECT id, username, password, user_type FROM Tbl_Names";
            ResultSet rs;
            rs = stmt.executeQuery(sql);

            // STEP 5: Extract data from result set
            while (rs.next()) {
                idCount++;

                // Retrieve by column name
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String user_type = rs.getString("user_type");

                // Appends values to the GUI's EditText
                loginInfo[idCount][0] = (String.valueOf(id));
                loginInfo[idCount][1] = (username);
                loginInfo[idCount][2] = (password);
                loginInfo[idCount][3] = (user_type);

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

    public void allowLogin(String user_type) {

        Intent intent = new Intent(getApplicationContext(), QRSCannerActivity.class);

        intent.putExtra("user_type", user_type);

        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Allows for network operation on the applications main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // GUI variables
        final Button login, exit;
        final EditText txtUsername, txtPassword;

        login = (Button) findViewById(R.id.btnLogin);
        exit = (Button) findViewById(R.id.btnExitLogin);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass, indexUserType;
                Boolean canLoop = true;

                user = String.valueOf(txtUsername.getText());
                pass = String.valueOf(txtPassword.getText());

                readPasswords();

                // Loops through all login credentials
                for (int i = 0; canLoop; i++) {

                    canLoop = i < idCount;

                    Boolean foundMatch;
                    String indexUsername, indexPassword, match, notMatch;

                    indexUsername = loginInfo[i][1];
                    indexPassword = loginInfo[i][2];
                    indexUserType = loginInfo[i][3];

                    foundMatch = user.equals(indexUsername) && pass.equals(indexPassword);
                    match = "Successful login attempt";
                    notMatch = "Failed login attempt\nUsername and or password incorrect";

                    // Checks for match between user input and current user and pass at index
                    if (foundMatch) {

                        Toast.makeText(getApplicationContext(), match,
                                Toast.LENGTH_SHORT).show();
                        allowLogin(indexUserType);
                        canLoop = false;

                    } else if (i == (idCount-1) && !foundMatch){

                        Toast.makeText(getApplicationContext(), notMatch,
                                Toast.LENGTH_SHORT).show();

                    }

                }

            }

        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(0); // Exits the application

            }

        });

    }
}
