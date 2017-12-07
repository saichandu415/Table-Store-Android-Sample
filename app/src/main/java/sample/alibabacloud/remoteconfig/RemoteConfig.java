package sample.alibabacloud.remoteconfig;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.PutRowRequest;
import com.alicloud.openservices.tablestore.model.RowPutChange;

import sample.alibabacloud.remoteconfig.model.ColumnData;

public class RemoteConfig extends Activity implements View.OnClickListener {

    //Fetch class level variables
    Button button;
    EditText countValue,ageMinValue,ageMaxValue,imageValue;
    TextView headingText,output;

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

        StringBuffer opBuf = new StringBuffer();

        if(id == R.id.button){

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

    private static void putRow(SyncClient client, ColumnData cData) {
        // Creating Primary Key
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(WelcomeActivity.PRIMARY_KEY_NAME, PrimaryKeyValue.fromString(WelcomeActivity.PRIMARY_KEY_VALUE));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange(WelcomeActivity.TABLE_NAME, primaryKey);

        rowPutChange.addColumn(WelcomeActivity.COUNT_KEY,ColumnValue.fromString(cData.getCharCount()));
        rowPutChange.addColumn(WelcomeActivity.MIN_AGE,ColumnValue.fromString(cData.getMinAge()));
        rowPutChange.addColumn(WelcomeActivity.MAX_AGE,ColumnValue.fromString(cData.getMaxAge()));
        rowPutChange.addColumn(WelcomeActivity.IMG_NUM,ColumnValue.fromString(cData.getImageNum()));

        client.putRow(new PutRowRequest(rowPutChange));
    }
}