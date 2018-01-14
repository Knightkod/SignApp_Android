package eus.ehu.intel.signapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.intel.signapp.Modelo.ProgressTask;
import eus.ehu.intel.signapp.Modelo.ServerConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREF_USERLOGIN = "userLogin";
    private static final String SHARED_PREF_USERPASS = "userPass";

    public static final String USER_REG="userReg";
    public static final String PASSW_REG="passReg";

    public static final int REGISTER_REQ_CODE=1;

    public String user;
    public String pass;

    private ServerConnection srvConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextUserLogin = (EditText) findViewById(R.id.userLogin);
        EditText editTextPasswdLogin = (EditText) findViewById(R.id.passwdLogin);
        CheckBox chkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemindLogin);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        user = prefs.getString(SHARED_PREF_USERLOGIN, null);
        pass = prefs.getString(SHARED_PREF_USERPASS, null);
        if (user != null)
            editTextUserLogin.setText(user);
        if (pass != null){
            editTextPasswdLogin.setText(pass);
            chkBoxRemember.setChecked(true);
        }
    }

    public void login(View view) {

        EditText editTextUserLogin = (EditText) findViewById(R.id.userLogin);
        EditText editTextPasswdLogin = (EditText) findViewById(R.id.passwdLogin);
        user = editTextUserLogin.getText().toString();
        pass = editTextPasswdLogin.getText().toString();

        srvConn= new ServerConnection(getResources().getString(R.string.baseUrl));

        new ProgressTask<Boolean>(this){
            @Override
            protected Boolean work() throws Exception {
                return srvConn.verificaLogin(user,pass);
            }
            @Override
            protected void onFinish(Boolean result) {
                if (result) {
                    CheckBox chkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemindLogin);
                    if (chkBoxRemember.isChecked())
                        saveLogin(user, pass);
                    Intent intent = new Intent(context, ForumActivity.class);
                    intent.putExtra(ForumActivity.LOGIN_ID, user);
                    intent.putExtra(ForumActivity.LOGIN_PASS, pass);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.loginRegNOK, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
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
        startActivityForResult(intent,REGISTER_REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){

        if(resultCode==RESULT_OK) {
            EditText editTextUserLogin = (EditText) findViewById(R.id.userLogin);
            EditText editTextPasswdLogin = (EditText) findViewById(R.id.passwdLogin);
            editTextUserLogin.setText(data.getStringExtra(USER_REG));
            editTextPasswdLogin.setText(data.getStringExtra(PASSW_REG));
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.loginRegNOK,Toast.LENGTH_SHORT).show();;
        }
    }

}
