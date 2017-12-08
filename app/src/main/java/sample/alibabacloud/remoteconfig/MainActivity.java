package sample.alibabacloud.remoteconfig;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Creating local objects
    TextInputLayout nameInputLayout;
    EditText nameValue;
    Spinner ageValue;
    String[] ageArr;
    RadioGroup radioGender;
    RadioButton radioMale, radioFemale, selectedRdoBtn;
    Button button;
    TextView output;
    ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInputLayout = findViewById(R.id.nameInputLayout);
        nameValue = findViewById(R.id.nameValue);
        ageValue = findViewById(R.id.ageValue);
        radioGender = findViewById(R.id.radioGender);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        button = findViewById(R.id.button);
        output = findViewById(R.id.output);
        backgroundImage = findViewById(R.id.backgroundImage);

        nameInputLayout.setCounterEnabled(true);

        int minAge;
        int maxAge;

        //Logic for checking the App is installed first or existing
        SharedPreferences sharedPreferences = getSharedPreferences(WelcomeActivity.SHARED_PREF_FILE_NAME,MODE_PRIVATE);
        boolean isPresent = sharedPreferences.contains(WelcomeActivity.COUNT_KEY);
        if(isPresent){
            minAge = Integer.parseInt(sharedPreferences.getString(WelcomeActivity.MIN_AGE,null));
            maxAge = Integer.parseInt(sharedPreferences.getString(WelcomeActivity.MAX_AGE,null));
            nameInputLayout.setCounterMaxLength(Integer.parseInt(sharedPreferences.getString(WelcomeActivity.COUNT_KEY,null)));

            if(sharedPreferences.getString(WelcomeActivity.IMG_NUM,null).equals("1")){
                backgroundImage.setImageResource(R.drawable.background_1);
            }else{
                backgroundImage.setImageResource(R.drawable.background_2);
            }

        }else{
            //if the app opening is new
            minAge = 18;
            maxAge = 35;
            nameInputLayout.setCounterMaxLength(20);
        }

        ageArr = new String[maxAge-minAge+1];
        for(int i =minAge;i<=maxAge;i++){
            ageArr[i-minAge] = String.valueOf(i);
        }

        // set the values into the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item,ageArr);

        ageValue.setAdapter(adapter);
        button.setOnClickListener(this);

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

}
