/**
 * Resource Database
 *
 * @author Cameron Fenton, Matthew Lepain, James Kerr, Timothy Patton
 * @date 26/05/2015
 * Basic QR code and Barcode scanner scanner prototype,
 * also a link to our site is included as a QR.
 * Edit and add to the application as you see necessary also feel free to make corrections
 * and improvements
 */

package datamanagementsolutionsapp.wordpress.com.resourcedatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QRSCannerActivity extends Activity {

    public void displayUserType(String user_type) {

        String statement;

        statement = "Logged in as: " + user_type;

        Toast.makeText(getApplicationContext(), statement, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        final String user_type = getIntent().getStringExtra("user_type");
        final String resource_user = getIntent().getStringExtra("resource_user");

        Log.i("App", "content view set to scanner layout");
        Log.i("App", "Type of user logged in: " + user_type);

        displayUserType(user_type);

        final Button databaseReader, databaseWriter, datbaseStatus, exit, scan;

        databaseReader = (Button) findViewById(R.id.btnDatabaseReader);
        databaseWriter = (Button) findViewById(R.id.btnDatabaseWriter);
        datbaseStatus = (Button) findViewById(R.id.btnDatabaseStatus);
        exit = (Button) findViewById(R.id.btnExit);
        scan = (Button) findViewById(R.id.btnScan);

        // scan onClick method starts
        scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /**
                 * Imports the zxing QR and Barcode reader library, and creates an instance of
                 * the android QR and Bar Code scanners, android camera permission declared in
                 * android manifest xml
                 */
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                // Passes the mode to be used, the QR code type scan mode to the android.SCAN class
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                // starts the scanner method

                startActivityForResult(intent, 0);

                // Log.i() Prints to console
                Log.i("App", "Scanner instance created");

            }
        });
        // scan onClick method ends

        // exit onClick method starts
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Terminates the application, Log.i() outputs to the console
                Log.i("App", "Exiting the application");
                System.exit(1);

            }
        });
        // exit onClick method ends

        // databaseReader onClick method starts
        databaseReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), DatabaseReaderActivity.class));

            }
        });
        // databaseReader onClick method ends

        // databaseWriter onClick method starts
        databaseWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), DatabaseRegisterActivity.class));

            }
        });
        // databaseWriter onClick method ends

        // databaseStatus onClick method starts
        datbaseStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DatabaseResourceStatusActivity.class);

                intent.putExtra("user_type", user_type);
                intent.putExtra("resource_user", resource_user);

                startActivity(intent);
            }
        });
        // databaseStatus onClick method ends

    }

    /**
     * Once the scanner has tried to scan the code this is the method which is called,
     * the arguments pass the scanner class a request for the scan and a return value,
     * and passes the instance of it in this case the QR code scanner mode
     * if the scanner class encounters an error the request code will not be returned
     * if does not crash, even if it does fail to scan the code, the returned value is 0
     * if the scanner successfully reads a string value from the code then the result code is
     * RESULT_OK, if the scanner fails to read a string from the code but does not crash, then the
     * class will return RESULT_CANCELED
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {

            // Handle successful scan, code to be executed if the scan is successful
            if (resultCode == RESULT_OK) {
                String contents, outputStatement;
                TextView output_contents;

                output_contents = (TextView) findViewById(R.id.lblContents);

                // Returns the string value contained in the code
                contents = intent.getStringExtra("SCAN_RESULT");

                /**
                 * Outputs the returned strings to TextViews for now just to show its retrieval.
                 * I am thinking that if we store the strings with reference to the resources on
                 * the database this could allow for quicker searching,
                 * it would narrow down the search much quicker
                 */
                outputStatement = "Retrieved string from code:\n" + contents;
                output_contents.setText(outputStatement);

            }  // Handle cancel, code to be executed if the scan of code fails
            else if (resultCode == RESULT_CANCELED) {

                // Prints an error to the logcat (android studio console)
                Log.i("App", "Scan unsuccessful");

            }
        }
    }

}