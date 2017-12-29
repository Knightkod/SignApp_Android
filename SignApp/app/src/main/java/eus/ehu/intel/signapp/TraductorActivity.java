package eus.ehu.intel.signapp;

import android.graphics.Color;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traductor);
    }


    public void viewVideo(View view) {
        int buttonId = Integer.parseInt(view.getTag().toString())-1;
        WebView web = new WebView(this);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadData("<iframe width=\"100%\" height=\"auto\"+\n" +
                "src='"+urlTraductor[buttonId]+"' ></iframe>","text/html",null);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
        ViewGroup layout = (ViewGroup) view.getParent();
        layout.addView(web);
    }
}
