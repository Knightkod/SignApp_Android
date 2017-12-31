package eus.ehu.intel.signapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.Locale;

import eus.ehu.intel.signapp.Modelo.AudioPlayer;
import eus.ehu.intel.signapp.Modelo.LanguageGestor;

public class GeninfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geninfo);

        ViewGroup viewGroup = (ViewGroup)this.findViewById(R.id.genInfoLayout);
        for(int i=1;i<viewGroup.getChildCount();i++){
            AudioPlayer audioPlayer= new AudioPlayer(viewGroup.getChildAt(i),null);
            try {
                audioPlayer.setAudioUri(Uri.parse("android.resource://"+getPackageName()+"/raw/" +
                        "geninfo"+i+"_"+ LanguageGestor.getLanguage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
