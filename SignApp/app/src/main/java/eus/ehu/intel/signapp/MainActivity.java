package eus.ehu.intel.signapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

import eus.ehu.intel.signapp.Modelo.LanguageGestor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toMenu(View view){
        String tag=view.getTag().toString();
        LanguageGestor.changeLanguage(this,tag);

        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }
}
