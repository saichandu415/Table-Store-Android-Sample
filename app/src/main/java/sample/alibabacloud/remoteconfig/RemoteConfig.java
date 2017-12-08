package sample.alibabacloud.remoteconfig;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alicloud.openservices.tablestore.ClientException;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.TableStoreException;
import com.alicloud.openservices.tablestore.model.Column;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.PutRowRequest;
import com.alicloud.openservices.tablestore.model.RowPutChange;
import com.alicloud.openservices.tablestore.model.RowUpdateChange;
import com.alicloud.openservices.tablestore.model.UpdateRowRequest;

import java.util.ArrayList;
import java.util.List;

import sample.alibabacloud.remoteconfig.model.ColumnData;

import static sample.alibabacloud.remoteconfig.WelcomeActivity.TABLE_NAME;

public class RemoteConfig extends Activity implements View.OnClickListener {

    //Fetch class level variables
    Button button;
    EditText countValue,ageMinValue,ageMaxValue,imageValue;
    TextView headingText,output;


    private final static String TAG = "RemoteConfig";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.button);
        countValue = findViewById(R.id.countValue);
        ageMinValue = findViewById(R.id.ageMinValue);
        ageMaxValue = findViewById(R.id.ageMaxValue);
        imageValue = findViewById(R.id.imageValue);

        headingText = findViewById(R.id.headingText);
        output = findViewById(R.id.output);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();



        if(id == R.id.button){

            SyncClient client = new SyncClient(getString(R.string.Endpoint), getString(R.string.AccessKey), getString(R.string.AccessKeySecret),
                    getString(R.string.InstanceName));
            updateConfigValues updateConfigValues = new updateConfigValues();
            updateConfigValues.execute(client);




        }
    }

    private static void updateRow(SyncClient client, ColumnData cData) {
        // Creating Primary Key
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(WelcomeActivity.PRIMARY_KEY_NAME, PrimaryKeyValue.fromString(WelcomeActivity.PRIMARY_KEY_VALUE));
        PrimaryKey primaryKey = primaryKeyBuilder.build();
        // Creating list of Columns
        List<Column> columnsList = new ArrayList<>();

        columnsList.add(new Column(WelcomeActivity.COUNT_KEY,ColumnValue.fromString(cData.getCharCount())));
        columnsList.add(new Column(WelcomeActivity.MIN_AGE,ColumnValue.fromString(cData.getMinAge())));
        columnsList.add(new Column(WelcomeActivity.MAX_AGE,ColumnValue.fromString(cData.getMaxAge())));
        columnsList.add(new Column(WelcomeActivity.IMG_NUM,ColumnValue.fromString(cData.getImageNum())));

        RowUpdateChange rowUpdateChange = new RowUpdateChange(TABLE_NAME, primaryKey);

        rowUpdateChange.put(columnsList);

        try {
            client.updateRow(new UpdateRowRequest(rowUpdateChange));
        } catch (TableStoreException ex) {
            Log.e(TAG, "updateRow: Error "+ex);
            ex.printStackTrace();
        }

        RowPutChange rowPutChange = new RowPutChange(TABLE_NAME, primaryKey);

        rowPutChange.addColumn(WelcomeActivity.COUNT_KEY,ColumnValue.fromString(cData.getCharCount()));
        rowPutChange.addColumn(WelcomeActivity.MIN_AGE,ColumnValue.fromString(cData.getMinAge()));
        rowPutChange.addColumn(WelcomeActivity.MAX_AGE,ColumnValue.fromString(cData.getMaxAge()));
        rowPutChange.addColumn(WelcomeActivity.IMG_NUM,ColumnValue.fromString(cData.getImageNum()));

        client.putRow(new PutRowRequest(rowPutChange));
    }


    class updateConfigValues extends AsyncTask<SyncClient, Void, Void> {

        ProgressDialog loading = new ProgressDialog(RemoteConfig.this);
        SyncClient client;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setMessage("Updating App Config Values");
            loading.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loading.dismiss();
        }

        @Override
        protected Void doInBackground(SyncClient... syncClients) {
            try {

                client = syncClients[0];

                // Create Table
//                createTable(client);

                // Give some time to load
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Creating Object with all the values
//                ColumnData cData = new ColumnData();
//                cData.setCharCount(DEF_COUNT_VALUE);
//                cData.setMinAge(DEF_MIN_AGE);
//                cData.setMaxAge(DEF_MAX_AGE);
//                cData.setImageNum(DEF_IMG_NUM);

                // putRow


                updateRow( client ,new ColumnData(countValue.getText().toString(),ageMinValue.getText().toString(),ageMaxValue.getText().toString(),imageValue.getText().toString()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateOutput();
                    }
                });



            } catch (TableStoreException e) {
                Log.e(TAG, "TableStoreException: Request error Message : " + e.getMessage());
                Log.e(TAG, "TableStoreException: Request error ID : " + e.getRequestId());

            } catch (ClientException e) {
                Log.e(TAG, "ClientException: Request error Message : " + e.getMessage());

            } finally {
                // Do your maintainence activites here
            }
//            client.shutdown();
            return null;
        }
    }

    private void updateOutput() {
        StringBuffer opBuf = new StringBuffer();

        opBuf.append("Updated Config:\n")
                .append("Character Count : ")
                .append(countValue.getText())
                .append("\nMinimum Age : ")
                .append(ageMinValue.getText())
                .append("\nMaximum Age : ")
                .append(ageMaxValue.getText())
                .append("\nUpdated Image Value : ")
                .append(imageValue.getText());


        output.setText(opBuf);
    }
}