package eus.ehu.intel.signapp.Presentacion;

import android.app.Activity;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import eus.ehu.intel.signapp.R;

/**
 * Created by iubuntu on 6/01/18.
 */

public class VideoButton {

    int prevButtonId=0;
    boolean notFirstTime=false;
    public void showVideo(View view, String url,Activity activity){

    ViewGroup layout = (ViewGroup) view.getParent();
    View nextButton;
    RelativeLayout.LayoutParams nextButtonParams;
        if(notFirstTime) {
        WebView web = (WebView)activity.findViewById(R.id.traductorWebView);
            ViewGroup relativeLayout = (ViewGroup) web.getParent();
              relativeLayout.removeView(web);
        if(prevButtonId!=layout.getChildAt(layout.getChildCount()-1).getId()) {
            nextButton = layout.getChildAt(layout.indexOfChild(activity.findViewById(prevButtonId)) + 1);
            nextButtonParams = (RelativeLayout.LayoutParams) nextButton.getLayoutParams();
            nextButtonParams.removeRule(RelativeLayout.BELOW);
            nextButtonParams.addRule(RelativeLayout.BELOW, prevButtonId);
            nextButton.setLayoutParams(nextButtonParams);
        }

    }//quizas sería mejor ocultar como view.gone en lugar de borrarla
     notFirstTime=true;
        prevButtonId=view.getId();
        WebView web = createWebView(url,view);
        if(view.getId()!=layout.getChildAt(layout.getChildCount()-1).getId()) {
        nextButton = layout.getChildAt(layout.indexOfChild(view) + 1);
        nextButtonParams = (RelativeLayout.LayoutParams) nextButton.getLayoutParams();
        nextButtonParams.removeRule(RelativeLayout.BELOW);
        nextButtonParams.addRule(RelativeLayout.BELOW, R.id.traductorWebView);
        nextButton.setLayoutParams(nextButtonParams);
    }
        layout.addView(web);
}

    private WebView createWebView(String url,View view){
        RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WebView web = new WebView(view.getContext());
        web.setId(R.id.traductorWebView);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadData("<iframe width=\"100%\" height=\"auto\"+\n" +
                "src='"+url+"' ></iframe>","text/html",null);
        web.setBackgroundColor(Color.TRANSPARENT);
        webViewParams.addRule(RelativeLayout.BELOW,view.getId());
        web.setLayoutParams(webViewParams);
        return web;
    }
}
