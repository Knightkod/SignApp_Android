package eus.ehu.intel.signapp;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TraductorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traductor);
    }


    public void viewVideo(View view) {
        int buttonId = view.getId();
        Toast.makeText(this,"Funcion aun no disponible",Toast.LENGTH_SHORT).show();
    }
}
