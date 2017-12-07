package sample.alibabacloud.remoteconfig;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                    .append("\nUpdated Image Value :")
                    .append(imageValue.getText());

            output.setText(opBuf);
        }
    }
}