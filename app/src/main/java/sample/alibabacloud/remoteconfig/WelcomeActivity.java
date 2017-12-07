package sample.alibabacloud.remoteconfig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alicloud.openservices.tablestore.ClientException;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.TableStoreException;
import com.alicloud.openservices.tablestore.model.Column;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.CreateTableRequest;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeySchema;
import com.alicloud.openservices.tablestore.model.PrimaryKeyType;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.PutRowRequest;
import com.alicloud.openservices.tablestore.model.RowPutChange;
import com.alicloud.openservices.tablestore.model.TableMeta;
import com.alicloud.openservices.tablestore.model.TableOptions;

import sample.alibabacloud.remoteconfig.model.ColumnData;

/**
 * Created by saisarathchandra on 06/12/17.
 */

public class WelcomeActivity extends Activity implements View.OnClickListener {


    public final static String COUNT_KEY = "CHARACTER_COUNT";
    public final static String MIN_AGE = "MINIMUM_AGE";
    public final static String MAX_AGE = "MAXIMUM_AGE";
    public final static String IMG_NUM = "IMAGE_NUMBER";

    public final static String DEF_COUNT_VALUE = "20";
    public final static String DEF_MIN_AGE = "18";
    public final static String DEF_MAX_AGE = "35";
    public final static String DEF_IMG_NUM = "1";

    private final static String TAG = "WelcomeActivity";
    public final static String SHARED_PREF_FILE_NAME = "RemoteConfigPref";

    public static final String PRIMARY_KEY_NAME = "Version";
    public static final String PRIMARY_KEY_VALUE = "User1";
    public static final String TABLE_NAME = "RemoteConfiguration";

    //Fetch class level variables
    Button userForm,remoteConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        userForm = findViewById(R.id.userForm);
        remoteConfig = findViewById(R.id.remoteConfig);

        userForm.setOnClickListener(this);
        remoteConfig.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_FILE_NAME,MODE_PRIVATE);
        boolean isPresent = sharedPreferences.contains(COUNT_KEY);
        if(!isPresent){
            // create Table & Shared preferences and put initial values

            SyncClient client = new SyncClient(getString(R.string.Endpoint), getString(R.string.AccessKey), getString(R.string.AccessKeySecret),
                getString(R.string.InstanceName));

            // TODO call async client for the creating table and update values

            SharedPreferences.Editor prefFileEdit = sharedPreferences.edit();

            prefFileEdit.putString(COUNT_KEY,DEF_COUNT_VALUE);
            prefFileEdit.putString(MIN_AGE,DEF_MIN_AGE);
            prefFileEdit.putString(MAX_AGE,DEF_MAX_AGE);
            prefFileEdit.putString(IMG_NUM,DEF_IMG_NUM);

            prefFileEdit.apply();
        }else{
            // TODO get the row values from server and update in shared preferences
        }

    }

    //Create a Table with a Primary Key Defined with some Additional Parameters
    private static void createTable(SyncClient client) {
        TableMeta tableMeta = new TableMeta(TABLE_NAME);
        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema(PRIMARY_KEY_NAME, PrimaryKeyType.STRING));
        int timeToLive = -1;
        int maxVersions = 1;

        TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);
        CreateTableRequest request = new CreateTableRequest(tableMeta, tableOptions);

        client.createTable(request);
    }

    private static void putRow(SyncClient client, ColumnData cData) {
        // Creating Primary Key
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(PRIMARY_KEY_NAME, PrimaryKeyValue.fromString(PRIMARY_KEY_VALUE));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(TABLE_NAME, primaryKey);

//        if you wish to have timestamp, add it as the third parameter to the addColumn method
//        long ts = System.currentTimeMillis();

        rowPutChange.addColumn(COUNT_KEY,ColumnValue.fromString(cData.getCharCount()));
        rowPutChange.addColumn(MIN_AGE,ColumnValue.fromString(cData.getMinAge()));
        rowPutChange.addColumn(MAX_AGE,ColumnValue.fromString(cData.getMaxAge()));
        rowPutChange.addColumn(IMG_NUM,ColumnValue.fromString(cData.getImageNum()));

        client.putRow(new PutRowRequest(rowPutChange));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.userForm){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.remoteConfig){
            Intent intent = new Intent(this,RemoteConfig.class);
            startActivity(intent);
        }
    }

    static class CreateTableNDefValues extends AsyncTask<SyncClient, Void, Void> {

        SyncClient client;

        @Override
        protected Void doInBackground(SyncClient... syncClients) {
            try {

                client = syncClients[0];

                // Create Table
                createTable(client);

                // Give some time to load
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Creating Object with all the values
                ColumnData cData = new ColumnData();
                cData.setCharCount(DEF_COUNT_VALUE);
                cData.setMinAge(DEF_MIN_AGE);
                cData.setMaxAge(DEF_MAX_AGE);
                cData.setImageNum(DEF_IMG_NUM);

                // putRow
                putRow(client, cData);

            } catch (TableStoreException e) {
                Log.e(TAG, "TableStoreException: Request error Message : " + e.getMessage());
                Log.e(TAG, "TableStoreException: Request error ID : " + e.getRequestId());

            } catch (ClientException e) {
                Log.e(TAG, "ClientException: Request error Message : " + e.getMessage());

            } finally {
                // Do your maintainence activites here
            }
            client.shutdown();
            return null;
        }
    }

}
