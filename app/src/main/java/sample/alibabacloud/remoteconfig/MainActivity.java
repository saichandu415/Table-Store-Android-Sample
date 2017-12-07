package sample.alibabacloud.remoteconfig;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alicloud.openservices.tablestore.ClientException;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.TableStoreException;
import com.alicloud.openservices.tablestore.model.Column;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.CreateTableRequest;
import com.alicloud.openservices.tablestore.model.DeleteTableRequest;
import com.alicloud.openservices.tablestore.model.GetRowRequest;
import com.alicloud.openservices.tablestore.model.GetRowResponse;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeySchema;
import com.alicloud.openservices.tablestore.model.PrimaryKeyType;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.PutRowRequest;
import com.alicloud.openservices.tablestore.model.Row;
import com.alicloud.openservices.tablestore.model.RowPutChange;
import com.alicloud.openservices.tablestore.model.SingleRowQueryCriteria;
import com.alicloud.openservices.tablestore.model.TableMeta;
import com.alicloud.openservices.tablestore.model.TableOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PRIMARY_KEY_NAME = "pk";
    private static final String TABLE_NAME = "RemoteConfiguration";
    private static final String TAG = "MainActivity";

    //Creating local objects
    TextInputLayout nameInputLayout;
    EditText nameValue;
    Spinner ageValue;
    String[] ageArr;
    RadioGroup radioGender;
    RadioButton radioMale, radioFemale, selectedRdoBtn;
    Button button;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        nameInputLayout = findViewById(R.id.nameInputLayout);
        nameValue = findViewById(R.id.nameValue);
        ageValue = findViewById(R.id.ageValue);
        radioGender = findViewById(R.id.radioGender);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        button = findViewById(R.id.button);
        output = findViewById(R.id.output);

//        //Creating Client
//        SyncClient client = new SyncClient(getString(R.string.Endpoint), getString(R.string.AccessKey), getString(R.string.AccessKeySecret),
//                getString(R.string.InstanceName));
//
//        DoOnNetwork dON = new DoOnNetwork();
//        dON.execute(client);


        nameInputLayout.setCounterEnabled(true);
        nameInputLayout.setCounterMaxLength(20);

        //Get Minimum Age
        int minAge = 18;
        //Get Maximum Age
        int maxAge = 35;
        //Create and Add it to Array
        ageArr = new String[maxAge-minAge+1];
        for(int i =minAge;i<=maxAge;i++){
            ageArr[i-minAge] = String.valueOf(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item,ageArr);
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        ageValue.setAdapter(adapter);
        button.setOnClickListener(this);

    }

    private static void putRow(SyncClient client, String pkValue) {
        // Creating Primary Key
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(PRIMARY_KEY_NAME, PrimaryKeyValue.fromString(pkValue));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(TABLE_NAME, primaryKey);

        //加入一些属性列
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                rowPutChange.addColumn(new Column("Col" + i, ColumnValue.fromLong(j), ts + j));
            }
        }

        client.putRow(new PutRowRequest(rowPutChange));
    }

    private static void createTable(SyncClient client) {
        TableMeta tableMeta = new TableMeta(TABLE_NAME);
        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema(PRIMARY_KEY_NAME, PrimaryKeyType.STRING));
        int timeToLive = -1; // 数据的过期时间, 单位秒, -1代表永不过期. 假如设置过期时间为一年, 即为 365 * 24 * 3600.
        int maxVersions = 1; // 保存的最大版本数, 设置为1即代表每列上最多保存一个版本(保存最新的版本).

        TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);

        CreateTableRequest request = new CreateTableRequest(tableMeta, tableOptions);

        client.createTable(request);
    }

    private static void getRow(SyncClient client, String pkValue) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(PRIMARY_KEY_NAME, PrimaryKeyValue.fromString(pkValue));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 读一行
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria(TABLE_NAME, primaryKey);
        // 设置读取最新版本
        criteria.setMaxVersions(1);
        GetRowResponse getRowResponse = client.getRow(new GetRowRequest(criteria));
        Row row = getRowResponse.getRow();
        Log.d(TAG, "getRow: Row Received Compelte");
        Log.d(TAG, "getRow:" + row);

        // 设置读取某些列
        criteria.addColumnsToGet("Col0");
        getRowResponse = client.getRow(new GetRowRequest(criteria));
        row = getRowResponse.getRow();

        Log.d(TAG, "getRow: Row Received columns added");
        Log.d(TAG, "getRow:" + row);


    }

    private static void deleteTable(SyncClient client) {
        DeleteTableRequest request = new DeleteTableRequest(TABLE_NAME);
        client.deleteTable(request);
        Log.d(TAG, "deleteTable: Table deleted");
    }

    @Override
    public void onClick(View view) {

        StringBuffer opStringBuf = new StringBuffer();

        int id = view.getId();
        if(id == R.id.button){


            opStringBuf.append("Form Submitted : ");
            // Get Name Input
            opStringBuf.append("\n Name given : "+nameValue.getEditableText());
            // Get Selected Age
            opStringBuf.append("\n"+"The age selected is : "+ageValue.getSelectedItem());
            // Get selected Gender
            int selectedRadio = radioGender.getCheckedRadioButtonId();
            selectedRdoBtn = findViewById(selectedRadio);
            opStringBuf.append("\n"+"selected Gender : "+selectedRdoBtn.getText());

            output.setText(opStringBuf);



        }


    }


    static class DoOnNetwork extends AsyncTask<SyncClient, Void, Void> {

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

                // putRow
                putRow(client, "pkValue");

                // getRow
                getRow(client, "pkValue");


            } catch (TableStoreException e) {
                Log.e(TAG, "TableStoreException: Request error Message : " + e.getMessage());
                Log.e(TAG, "TableStoreException: Request error ID : " + e.getRequestId());

            } catch (ClientException e) {
                Log.e(TAG, "ClientException: Request error Message : " + e.getMessage());

            } finally {
                // Delete the table created
                deleteTable(client);
            }
            client.shutdown();
            return null;
        }
    }

}
