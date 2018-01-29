package eus.ehu.intel.signapp;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import eus.ehu.intel.signapp.modelo.AudioPlayer;
import eus.ehu.intel.signapp.modelo.LanguageGestor;

public class GeninfoActivity extends AppCompatActivity {

    private AudioPlayer[] audioPlayer={null,null,null,null};
    private Handler[] mHandler = {null,null,null,null};

    private int cte=1536;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geninfo);

        LayoutInflater inflater = getLayoutInflater();


        LinearLayout genInfoLayout = (LinearLayout)this.findViewById(R.id.genInfoLayout);
        String[] genInfoTexts=getResources().getStringArray(R.array.genInfoTexts);
        for(int i=0;i<genInfoTexts.length;i++){
            createTextView(genInfoLayout,genInfoTexts[i]);
            View otherView=inflater.inflate(R.layout.custom_media_controller_layout,genInfoLayout);

            try {
                audioPlayer[i]= new AudioPlayer(genInfoLayout.getChildAt(i),null);
                audioPlayer[i].setAudioUri(Uri.parse("android.resource://" + getPackageName() + "/raw/" +
                        "geninfo" + (i+1) + "_" + LanguageGestor.getLanguage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            SeekBar seekBar = otherView.findViewById(R.id.seekBar);
            startSeekBar(seekBar,audioPlayer[i],i);
            ImageButton imgButton = otherView.findViewById(R.id.playPauseImageButton);
            startImageButton(imgButton,i);

        }
    }

    @Override
    public void onStop(){
        for(int i = 0; i < audioPlayer.length;i++){
            ((ImageButton)findViewById(i+cte)).setImageResource(R.drawable.play_blauw);
            audioPlayer[i].pause();
        }
        super.onStop();



    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        String[] genInfoTexts=getResources().getStringArray(R.array.genInfoTexts);
        for(int i = 0; i < genInfoTexts.length;i++){
            audioPlayer[i].release();
            mHandler[i].removeCallbacksAndMessages(null);
        }


    }

    private void createTextView(LinearLayout genInfoLayout,String genInfoText){
        int margin = (int) getResources().getDimension(R.dimen.standardViewMargin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,margin,0,0);
        TextView textView=new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText(genInfoText);
        textView.setTextColor(getResources().getColor(R.color.textColor));
        textView.setTextSize(getResources().getDimension(R.dimen.genInfoTextSize));

        genInfoLayout.addView(textView);
    }

    private void startSeekBar(SeekBar seekBar, AudioPlayer audioPlayer, int i){
        seekBar.setId(cte*2);
        seekBar.setMax(audioPlayer.getDuration());
        seekBarListener(seekBar,audioPlayer);
        seekBarPositionUpdate(seekBar,audioPlayer,i);


    }

    private void seekBarListener(SeekBar seekBar,final AudioPlayer audioPlayer){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(audioPlayer != null && fromUser){
                    audioPlayer.seekTo(progress);
                }
            }
        });
    }

    private void seekBarPositionUpdate(final SeekBar seekBar, final AudioPlayer audioPlayer,final int i){
        mHandler[i]= new Handler();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(audioPlayer != null){
                    int mCurrentPosition = audioPlayer.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition);
                    if((mCurrentPosition+500)>audioPlayer.getDuration()) {
                        LinearLayout linearLayout = (LinearLayout) seekBar.getParent();
                        ImageButton button = (ImageButton)linearLayout.getChildAt(linearLayout.indexOfChild(seekBar)-1);
                        button.setImageResource(R.drawable.play_blauw);
                        seekBar.setProgress(0);
                    }
                }

                mHandler[i].postDelayed(this, 1000);
            }
        });
    }

    private void startImageButton(ImageButton imgButton, int i){
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
