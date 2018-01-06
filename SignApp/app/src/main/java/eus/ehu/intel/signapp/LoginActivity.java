package eus.ehu.intel.signapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.intel.signapp.Modelo.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREF_USERLOGIN = "userLogin";
    private static final String SHARED_PREF_USERPASS = "userPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextUserLogin = (EditText) findViewById(R.id.userLogin);
        EditText editTextPasswdLogin = (EditText) findViewById(R.id.passwdLogin);
        CheckBox chkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemindLogin);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String user = prefs.getString(SHARED_PREF_USERLOGIN, null);
        String pass = prefs.getString(SHARED_PREF_USERPASS, null);
        if (user != null)
            editTextUserLogin.setText(user);
        if (pass != null){
            editTextPasswdLogin.setText(pass);
            chkBoxRemember.setChecked(true);
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, ForumActivity.class);
        EditText editTextUserLogin = (EditText) findViewById(R.id.userLogin);
        EditText editTextPasswdLogin = (EditText) findViewById(R.id.passwdLogin);
        CheckBox chkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemindLogin);
        String login = editTextUserLogin.getText().toString();
        String passwd = editTextPasswdLogin.getText().toString();
        ServerConnection srvConn = new ServerConnection();
        if (srvConn.verificaLogin(login, passwd)) {
            if (chkBoxRemember.isChecked())
                saveLogin(login, passwd);
            intent.putExtra(ForumActivity.LOGIN_ID, login);
            intent.putExtra(ForumActivity.LOGIN_PASS, passwd);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.loginRegNOK, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLogin(String login, String pass){
        SharedPreferences prefs=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SHARED_PREF_USERLOGIN,login);
        editor.putString(SHARED_PREF_USERPASS,pass);
        editor.commit();
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

}
