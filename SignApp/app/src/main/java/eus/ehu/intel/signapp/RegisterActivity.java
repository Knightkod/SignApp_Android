package eus.ehu.intel.signapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.intel.signapp.Modelo.ServerConnection;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        String login = ((EditText)findViewById(R.id.userRegister)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.passwdRegister)).getText().toString();
        ServerConnection srvConn=new ServerConnection();
        if(srvConn.registro(login,passwd)){
            Toast.makeText(getApplicationContext(),R.string.loginRegOK,Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.loginRegNOK,Toast.LENGTH_SHORT).show();
        }
    }
}
