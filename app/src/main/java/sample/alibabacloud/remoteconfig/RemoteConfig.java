import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import sample.alibabacloud.remoteconfig.MainActivity;
import sample.alibabacloud.remoteconfig.R;

public class RemoteConfig extends Activity implements View.OnClickListener {

    //Fetch class level variables
    Button userForm,remoteConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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