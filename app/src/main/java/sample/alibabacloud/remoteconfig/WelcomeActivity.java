package sample.alibabacloud.remoteconfig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by saisarathchandra on 06/12/17.
 */

public class WelcomeActivity extends Activity implements View.OnClickListener {


    private final static String COUNT_KEY = "CHARACTER_COUNT";
    private final static String MIN_AGE = "MINIMUM_AGE";
    private final static String MAX_AGE = "MAXIMUM_AGE";
    private final static String IMG_NUM = "IMAGE_NUMBER";

    private final static String TAG = "WelcomeActivity";
    private final static String SHARED_PREF_FILE_NAME = "RemoteConfigPref";
    
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




            SharedPreferences.Editor prefFileEdit = sharedPreferences.edit();

            prefFileEdit.putInt(COUNT_KEY,20);
            prefFileEdit.putInt(MIN_AGE,18);
            prefFileEdit.putInt(MAX_AGE,35);
            prefFileEdit.putInt(IMG_NUM,1);

            prefFileEdit.apply();


        }

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
}
