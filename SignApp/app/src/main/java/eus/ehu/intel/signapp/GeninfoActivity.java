package eus.ehu.intel.signapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import eus.ehu.intel.signapp.Modelo.AudioPlayer;
import eus.ehu.intel.signapp.Modelo.LanguageGestor;

public class GeninfoActivity extends AppCompatActivity {

    AudioPlayer[] audioPlayer={null,null,null,null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geninfo);

        int cte=1536;
        LayoutInflater inflater = getLayoutInflater();
        int margin = (int) getResources().getDimension(R.dimen.standardViewMargin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,margin,0,0);
        LinearLayout genInfoLayout = (LinearLayout)this.findViewById(R.id.genInfoLayout);
        String[] genInfoTexts=getResources().getStringArray(R.array.genInfoTexts);
        for(int i=0;i<genInfoTexts.length;i++){
            TextView textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setText(genInfoTexts[i]);
            textView.setTextColor(getResources().getColor(R.color.textColor));
            textView.setTextSize(getResources().getDimension(R.dimen.genInfoTextSize));

            genInfoLayout.addView(textView);
            View otherView=inflater.inflate(R.layout.custom_media_controller_layout,genInfoLayout);

            try {
                audioPlayer[i]= new AudioPlayer(genInfoLayout.getChildAt(i),null);
                audioPlayer[i].setAudioUri(Uri.parse("android.resource://" + getPackageName() + "/raw/" +
                        "geninfo" + (i+1) + "_" + LanguageGestor.getLanguage()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageButton imgButton = otherView.findViewById(R.id.playPauseImageButton);
            imgButton.setTag(i);
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton button = (ImageButton) v;
                    int i = (Integer) v.getTag();
                    if(!audioPlayer[i].isPlaying()) {
                        button.setImageResource(R.drawable.circled_pause);
                        audioPlayer[i].start();
                    }
                    else {
                        button.setImageResource(R.drawable.play_blauw);
                        audioPlayer[i].pause();
                    }
                }
            });
            imgButton.setId(i+cte);

        }
    }
}
