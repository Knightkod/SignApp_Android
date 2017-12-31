package eus.ehu.intel.signapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    public static final int VIDEO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void toGeninfo(View view){
        Intent intent = new Intent(this,GeninfoActivity.class);
        startActivity(intent);
    }
    public void toTraductor(View view){
        Intent intent = new Intent(this,TraductorActivity.class);
        startActivity(intent);
    }
    public void toAlphab(View view){
        Intent intent = new Intent(this,AlphabetActivity.class);
        startActivity(intent);
    }
    public void toForum(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void recordVideo(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getApplicationContext(),R.string.noCamera,Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null)
                startActivityForResult(intent,VIDEO_REQUEST_CODE);
        }

    }

    public void grabaVideo(View view){

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK)
            return ;

        switch (requestCode){
            case VIDEO_REQUEST_CODE:
                Toast.makeText(this,R.string.videoOk,Toast.LENGTH_SHORT).show();
                data.getData();
                //falta el tratamiento que se le da al video una vez grabado, en este caso llamar a dropbox para subirlo
                break;
        }
    }
}
