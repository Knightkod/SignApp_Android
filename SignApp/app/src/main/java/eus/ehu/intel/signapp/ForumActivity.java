package eus.ehu.intel.signapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.intel.signapp.Modelo.ServerConnection;

public class ForumActivity extends AppCompatActivity {
    public static final String LOGIN_ID="login";
    public static final String LOGIN_PASS="pass";
    private String userLogin="";
    private String userPass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        Intent intent = getIntent();
        userLogin=intent.getStringExtra(LOGIN_ID);

        ServerConnection srvCx=new ServerConnection();
        srvCx.recibirPreguntasUsuario(userLogin);
        srvCx.recibirPreguntasOtrosUsuarios(userLogin);
    }

    public void sendQuest(View view) {
        String questText = ((EditText)findViewById(R.id.questionText)).getText().toString();
        ServerConnection srvCx=new ServerConnection();
        if(srvCx.subirPregunta(userLogin,userPass,questText)){
            Toast.makeText(getApplicationContext(),"AllOKQuestion",Toast.LENGTH_SHORT).show();
        }

    }

    public void uploadVideoPopUp(View view) {
        Toast.makeText(getApplicationContext(),"Funcion aun no disponible",Toast.LENGTH_SHORT).show();
    }

    public void uploadResponse(View view){
        ServerConnection srvCx=new ServerConnection();
        int id_preg=0;
        String urlRespuesta="";
        if(srvCx.enviarUrlRespuesta(userLogin,userPass,id_preg,urlRespuesta)){
            Toast.makeText(getApplicationContext(),"AllOKResponse",Toast.LENGTH_SHORT).show();
        }
    }

    public void logout(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


}
