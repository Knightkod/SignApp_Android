package eus.ehu.intel.signapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

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
        userPass=intent.getStringExtra(LOGIN_PASS);
        ServerConnection srvCx=new ServerConnection();
        printUserQuestions(this,srvCx.recibirPreguntasUsuario(userLogin));
        printOthersQuestions(this,srvCx.recibirPreguntasOtrosUsuarios(userLogin));
    }

    private void printUserQuestions(Context context,List<Forum> foro) {
        RelativeLayout linearLayout=(RelativeLayout)findViewById(R.id.myQuestionsLayout);
        int margin = (int) getResources().getDimension(R.dimen.forumButtonMargin);
        Button btn;
        int previousButtonId=0;
        for(int i = 0; i < foro.size();i++){
            btn = new Button(context);
            createButtonBasics(btn,i,i,foro,previousButtonId, margin);
            if(foro.get(i).getResp()!=null && foro.get(i).getResp()!="") {
                btn.setTag(foro.get(i).getResp());
                btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.video, 0);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),(String)v.getTag(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
            previousButtonId=i;
            linearLayout.addView(btn);
        }
    }

    private void printOthersQuestions(Context context,List<Forum> foro) {
        RelativeLayout linearLayout=(RelativeLayout)findViewById(R.id.othersQuestionLayout);
        int margin = (int) getResources().getDimension(R.dimen.forumButtonMargin);
        Button btn;
        int previousButtonId=0;
        for(int i = 0; i < foro.size();i++){
            btn = new Button(context);
            createButtonBasics(btn,i,(i+100),foro,previousButtonId, margin);
            btn.setTag(foro.get(i).getId());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int questId = (int)v.getTag();
                    uploadVideoPopUp(questId);
                }
            });
            previousButtonId=i+100;
            linearLayout.addView(btn);
        }
    }

    private void createButtonBasics(Button btn, int i, int viewId, List<Forum> foro, int previousButtonId, int margin){
        btn.setId(viewId);
        btn.setText(foro.get(i).getQuestion());
        btn.setTextColor(getResources().getColor(R.color.buttonTextColor));
        btn.setTextSize(getResources().getDimension(R.dimen.forumButtonTextSize));
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if(i!=0)
            layoutParams.addRule(RelativeLayout.BELOW,previousButtonId);
        else
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
        layoutParams.setMargins(margin,margin,margin,margin);
        btn.setLayoutParams(layoutParams);
    }


    public void sendQuest(View view) {
        String questText = ((EditText)findViewById(R.id.questionText)).getText().toString();
        ServerConnection srvCx=new ServerConnection();
        if(srvCx.subirPregunta(userLogin,userPass,questText)){
            Toast.makeText(getApplicationContext(),"AllOKQuestion",Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadVideoPopUp(int questId) {
        AlertDialog.Builder builder=new AlertDialog.Builder(ForumActivity.this);

        LayoutInflater inflater= ForumActivity.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.forum_upload_dialog,null);

        builder.setView(view);
        final  AlertDialog dialog=builder.show();
        Button sendResponseURL= view.findViewById(R.id.buttonSendResponse);
        sendResponseURL.setTag(questId);
        sendResponseURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String responseURL = ((EditText)view.findViewById(R.id.responseURL)).getText().toString();
                int questId=(int)v.getTag();
                ServerConnection srvCx=new ServerConnection();
                if(srvCx.enviarUrlRespuesta(userLogin,userPass,questId,responseURL)){
                    Toast.makeText(getApplicationContext(),"AllOKResponse, con id: "+Integer.toString(questId)+" y url "+responseURL,Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    public void logout(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void uploadInfoPopup(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(ForumActivity.this);
        LayoutInflater inflater= ForumActivity.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.forum_info_dialog,null);

        builder.setView(v);

        builder.show();
    }
}