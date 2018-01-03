package eus.ehu.intel.signapp;

import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class TraductorActivity extends AppCompatActivity {

    private String[] urlTraductor={
            "https://www.youtube.com/embed/jHukIQ8Szfg",
        "https://www.youtube.com/embed/TKRVFvv5eUU",
        "https://www.youtube.com/embed/ljdxTvBw6Uw",
        "https://www.youtube.com/embed/W57loe08GUI",
        "https://www.youtube.com/embed/-DlW8yowAwM",
        "https://www.youtube.com/embed/SId3u5fiOzY",
        "https://www.youtube.com/embed/1nxhCtaNIBw",
        "https://www.youtube.com/embed/Iwj-cSe7_nQ",
        "https://www.youtube.com/embed/s3Hwwyw7-tA",
        "https://www.youtube.com/embed/pBOK4R5bW7w"
        };
    private int buttonClickedTag=50;//valor arbitrario que permite saber cuando es el estado inicial
    private int buttonClickedId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traductor);
    }


    public void viewVideo(View view) {

        ViewGroup layout = (ViewGroup) view.getParent();
        View nextButton= layout.getChildAt(layout.indexOfChild(findViewById(buttonClickedId))+1);
        RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams nextButtonParams = (RelativeLayout.LayoutParams) nextButton.getLayoutParams();
        if(buttonClickedTag!=50) {
            WebView web = (WebView)findViewById(R.id.traductorWebView);
            ViewGroup relativeLayout = (ViewGroup) web.getParent();

            relativeLayout.removeView(web);
            /*nextButtonParams.removeRule(RelativeLayout.BELOW);
            nextButtonParams.addRule(RelativeLayout.BELOW,buttonClickedId);
            nextButton.setLayoutParams(nextButtonParams);*/

        }//quizas sería mejor ocultar como view.gone en lugar de borrarla
        buttonClickedTag = Integer.parseInt(view.getTag().toString())-1;
        buttonClickedId = view.getId();
        WebView web = new WebView(this);
        web.setId(R.id.traductorWebView);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadData("<iframe width=\"100%\" height=\"auto\"+\n" +
                "src='"+urlTraductor[buttonClickedTag]+"' ></iframe>","text/html",null);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
        webViewParams.addRule(RelativeLayout.BELOW,buttonClickedId);
        web.setLayoutParams(webViewParams);
        /*nextButtonParams.removeRule(RelativeLayout.BELOW);
        nextButtonParams.addRule(RelativeLayout.BELOW,R.id.traductorWebView);
        nextButton.setLayoutParams(nextButtonParams);*/
        layout.addView(web);
    }
}
