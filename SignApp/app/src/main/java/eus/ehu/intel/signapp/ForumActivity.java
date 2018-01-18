package eus.ehu.intel.signapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.List;

import eus.ehu.intel.signapp.Modelo.Forum;
import eus.ehu.intel.signapp.Modelo.ProgressTask;
import eus.ehu.intel.signapp.Modelo.ServerConnection;
import eus.ehu.intel.signapp.Presentacion.CustomToast;
import eus.ehu.intel.signapp.Presentacion.VideoButton;

public class ForumActivity extends AppCompatActivity {
    public static final String LOGIN_ID = "login";
    public static final String LOGIN_PASS = "pass";
    private String userLogin = "";
    private String userPass = "";

    private String responseURL;
    private ServerConnection srvConn;

    private Context context;

    VideoButton videoButton = new VideoButton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        srvConn = new ServerConnection(getResources().getString(R.string.baseUrl));
        context = this;
        Intent intent = getIntent();
        userLogin = intent.getStringExtra(LOGIN_ID);
        userPass = intent.getStringExtra(LOGIN_PASS);

        new ProgressTask<List<Forum>>(context) {
            @Override
            protected List<Forum> work() throws Exception {
                return srvConn.recibirPreguntasUsuario(userLogin);
            }

            @Override
            protected void onFinish(List<Forum> result) {
                if (result != null) {
                    printUserQuestions(result);
                    otherUserThings();
                }
            }
        }.execute();

    }

    private void otherUserThings() {
        new ProgressTask<List<Forum>>(context) {
            @Override
            protected List<Forum> work() throws Exception {
                return srvConn.recibirPreguntasOtrosUsuarios(userLogin);
            }

            @Override
            protected void onFinish(List<Forum> result) {
                if (result != null) {
                    printOthersQuestions(context, result);
                }
            }
        }.execute();
    }

    private void printUserQuestions(List<Forum> foro) {
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.myQuestionsLayout);
        int margin = (int) getResources().getDimension(R.dimen.forumButtonMargin);
        Button btn;
        int previousButtonId = 0;
        for (int i = 0; i < foro.size(); i++) {
            btn = new Button(context);
            createButtonBasics(btn, i, i, foro, previousButtonId, margin);
            if (foro.get(i).getResp() != null && foro.get(i).getResp() != "") {
                btn.setTag(foro.get(i).getResp());
                btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.video, 0);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
                        LayoutInflater inflater = getLayoutInflater();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI)
                                CustomToast.createToast("warning", context.getResources().getString(R.string.warnNoWifi), inflater, context);
                            Activity activity = (Activity) v.getContext();
                            videoButton.showVideo(v, (String) v.getTag(), activity);
                        } else
                            CustomToast.createToast("error", context.getResources().getString(R.string.cxerr), inflater, context);
                    }
                });
            }
            previousButtonId = i;
            linearLayout.addView(btn);
        }
    }

    private void printOthersQuestions(Context context, List<Forum> foro) {
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.othersQuestionLayout);
        int margin = (int) getResources().getDimension(R.dimen.forumButtonMargin);
        Button btn;
        int previousButtonId = 0;
        for (int i = 0; i < foro.size(); i++) {
            btn = new Button(context);
            createButtonBasics(btn, i, (i + 100), foro, previousButtonId, margin);
            btn.setTag(foro.get(i).getId());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int questId = (int) v.getTag();
                    uploadVideoPopUp(questId);
                }
            });
            previousButtonId = i + 100;
            linearLayout.addView(btn);
        }
    }

    private void createButtonBasics(Button btn, int i, int viewId, List<Forum> foro, int previousButtonId, int margin) {
        btn.setId(viewId);
        btn.setText(foro.get(i).getQuestion());
        btn.setTextColor(getResources().getColor(R.color.buttonTextColor));
        btn.setTextSize(getResources().getDimension(R.dimen.forumButtonTextSize));
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (i != 0)
            layoutParams.addRule(RelativeLayout.BELOW, previousButtonId);
        else
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.setMargins(margin, margin, margin, margin);
        btn.setLayoutParams(layoutParams);
    }


    public void sendQuest(View view) {
        final String questText = ((EditText) findViewById(R.id.questionText)).getText().toString();

        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
        LayoutInflater inflater = getLayoutInflater();
        if (networkInfo != null && networkInfo.isConnected()) {
            srvConn = new ServerConnection(getResources().getString(R.string.baseUrl));
            new ProgressTask<Boolean>(context) {
                @Override
                protected Boolean work() throws Exception {
                    return srvConn.subirPregunta(userLogin, userPass, questText);
                }

                @Override
                protected void onFinish(Boolean result) {
                    LayoutInflater inflater = getLayoutInflater();
                    if (result) {
                        CustomToast.createToast("success", context.getResources().getString(R.string.questSendOk), inflater, context);
                    } else
                        CustomToast.createToast("error", context.getResources().getString(R.string.questSendErr), inflater, context);
                }
            }.execute();
        } else
            CustomToast.createToast("error", context.getResources().getString(R.string.cxerr), inflater, context);
    }

    public void uploadVideoPopUp(int questId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForumActivity.this);
        LayoutInflater inflater = ForumActivity.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.forum_upload_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.show();

        Button sendResponseURL = view.findViewById(R.id.buttonSendResponse);
        sendResponseURL.setTag(questId);
        sendResponseURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
                LayoutInflater inflater = getLayoutInflater();
                if (networkInfo != null && networkInfo.isConnected()) {
                    sendResponseToServer(dialog, view, v);
                } else
                    CustomToast.createToast("error", context.getResources().getString(R.string.cxerr), inflater, context);
            }
        });
    }

    private void sendResponseToServer(final AlertDialog dialog, View view, View v) {
        responseURL = ((EditText) view.findViewById(R.id.responseURL)).getText().toString();
        final int questId = (int) v.getTag();
        srvConn = new ServerConnection(getResources().getString(R.string.baseUrl));

        new ProgressTask<Boolean>(context) {
            @Override
            protected Boolean work() throws Exception {
                return srvConn.enviarUrlRespuesta(userLogin, userPass, questId, responseURL,context);
            }

            @Override
            protected void onFinish(Boolean result) {
                LayoutInflater inflater = getLayoutInflater();
                if (result) {
                    CustomToast.createToast("success", context.getResources().getString(R.string.respSendOk), inflater, context);
                } else
                    CustomToast.createToast("error", context.getResources().getString(R.string.respSendErr), inflater, context);

                dialog.dismiss();
            }
        }.execute();
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void uploadInfoPopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForumActivity.this);
        LayoutInflater inflater = ForumActivity.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.forum_info_dialog, null);

        builder.setView(v);

        builder.show();
    }
}