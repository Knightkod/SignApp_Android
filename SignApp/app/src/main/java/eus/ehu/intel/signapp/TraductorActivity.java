package eus.ehu.intel.signapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import eus.ehu.intel.signapp.presentacion.CustomToast;
import eus.ehu.intel.signapp.presentacion.VideoButton;

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

    VideoButton videoButton = new VideoButton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traductor);
    }


    public void viewVideo(View view) {

        ConnectivityManager connMngr=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMngr.getActiveNetworkInfo();
        LayoutInflater inflater = getLayoutInflater();
        if(networkInfo!=null && networkInfo.isConnected()) {
            if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI)
                CustomToast.createToast("warning", this.getResources().getString(R.string.warnNoWifi), inflater, this);
            videoButton.showVideo(view, urlTraductor[Integer.parseInt(view.getTag().toString()) - 1], this);
        }
        else
            CustomToast.createToast("error", this.getResources().getString(R.string.cxerr), inflater, this);

    }
}
