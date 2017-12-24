package eus.ehu.intel.signapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

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
        Toast.makeText(this,"Funcion aun no disponible",Toast.LENGTH_SHORT).show();
    }
    public void recordVideo(View view){
        Toast.makeText(this,"Funcion aun no disponible",Toast.LENGTH_SHORT).show();
    }
}
