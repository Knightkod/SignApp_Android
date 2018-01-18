package eus.ehu.intel.signapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import eus.ehu.intel.signapp.Modelo.ProgressTask;
import eus.ehu.intel.signapp.Modelo.ServerConnection;
import eus.ehu.intel.signapp.Presentacion.CustomToast;

public class RegisterActivity extends AppCompatActivity {

    private ServerConnection srvConn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        intent = new Intent();
        final String login = ((EditText) findViewById(R.id.userRegister)).getText().toString();
        final String passwd = ((EditText) findViewById(R.id.passwdRegister)).getText().toString();
       srvConn = new ServerConnection(getResources().getString(R.string.baseUrl));
        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
        LayoutInflater inflater = getLayoutInflater();
        if (networkInfo != null && networkInfo.isConnected()) {
            sendRegisterToServer(this,login,passwd);
    }
        else
            CustomToast.createToast("error", this.getResources().getString(R.string.cxerr), inflater, this);
    }



    public void sendRegisterToServer(Context contxt, final String login, final String passwd){
        new ProgressTask<Boolean>(contxt){
            @Override
            protected Boolean work() throws Exception {
                return srvConn.registro(login, passwd);
            }

            @Override
            protected void onFinish(Boolean result) {
                if (result) {
                    intent.putExtra(LoginActivity.USER_REG, login);
                    intent.putExtra(LoginActivity.PASSW_REG, passwd);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            }
        }.execute();
    }
}
