package eus.ehu.intel.signapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import eus.ehu.intel.signapp.Modelo.AudioPlayer;
import eus.ehu.intel.signapp.Modelo.LanguageGestor;

public class GeninfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geninfo);
        int audioIndex =0;

        ViewGroup viewGroup = (ViewGroup)this.findViewById(R.id.genInfoLayout);
        for(int i=1;i<viewGroup.getChildCount();i++){
            if(viewGroup.getChildAt(i) instanceof TextView) {
                try {
                    audioIndex++;
                    AudioPlayer audioPlayer= new AudioPlayer(viewGroup.getChildAt(i),null);
                    audioPlayer.setAudioUri(Uri.parse("android.resource://" + getPackageName() + "/raw/" +
                            "geninfo" + audioIndex + "_" + LanguageGestor.getLanguage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
