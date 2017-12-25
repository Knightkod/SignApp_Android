package eus.ehu.intel.signapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.intel.signapp.Modelo.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        Intent intent = new Intent(this,ForumActivity.class);
        String login = ((EditText)findViewById(R.id.userLogin)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.passwdLogin)).getText().toString();
        CheckBox chkBoxRemember = (CheckBox)findViewById(R.id.checkBoxRemindLogin);
        ServerConnection srvConn=new ServerConnection();
        if(srvConn.verificaLogin(login,passwd)){
            if(chkBoxRemember.isChecked())
                //aqui iría el código de guardar la info
                Toast.makeText(getApplicationContext(),"login guardado!",Toast.LENGTH_SHORT).show();
                intent.putExtra(ForumActivity.LOGIN_ID,login);
                intent.putExtra(ForumActivity.LOGIN_PASS,passwd);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.loginRegNOK,Toast.LENGTH_SHORT).show();
        }
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }

}
