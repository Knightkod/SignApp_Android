package eus.ehu.intel.signapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import java.util.List;

import eus.ehu.intel.signapp.Modelo.Forum;
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
        printUserQuestions(this,srvCx.recibirPreguntasUsuario(userLogin));
        printOthersQuestions(this,srvCx.recibirPreguntasOtrosUsuarios(userLogin));
    }
    private void printUserQuestions(Context context,List<Forum> foro) {
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.myQuestionsLayout);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int margin = (int) getResources().getDimension(R.dimen.forumButtonMargin);
        layoutParams.setMargins(margin,margin,margin,margin);
        Button btn;
        for(int i = 0; i < foro.size();i++){
            btn = new Button(context);
            btn.setText(foro.get(i).getQuestion());
            btn.setLayoutParams(layoutParams);
            btn.setTextColor(getResources().getColor(R.color.buttonTextColor));
            btn.setTextSize(getResources().getDimension(R.dimen.forumButtonTextSize));
            btn.setTag(foro.get(i).getResp());
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            if(foro.get(i).getResp()!=null && foro.get(i).getResp()!="") {
                btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.video, 0);
           btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),(String)v.getTag(),Toast.LENGTH_SHORT).show();
                }
            });
            }
            linearLayout.addView(btn);
        }
    }

    private void printOthersQuestions(Context context,List<Forum> foro) {
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.othersQuestionLayout);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int margin = (int) getResources().getDimension(R.dimen.forumButtonMargin);
        layoutParams.setMargins(margin,margin,margin,margin);
        for(int i = 0; i < foro.size();i++){
            Button btn = new Button(context);
            btn.setText(foro.get(i).getQuestion());
            btn.setLayoutParams(layoutParams);
            btn.setTextColor(getResources().getColor(R.color.buttonTextColor));
            btn.setTextSize(getResources().getDimension(R.dimen.forumButtonTextSize));
            btn.setTag(foro.get(i).getId());
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),Integer.toString((int)v.getTag()),Toast.LENGTH_SHORT).show();
                }
            });
            linearLayout.addView(btn);
        }
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
