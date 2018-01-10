package eus.ehu.intel.signapp.Modelo;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import java.io.IOException;

/**
 * Created by iubuntu on 31/12/17.
 */

public class AudioPlayer implements MediaController.MediaPlayerControl, MediaPlayer.OnPreparedListener {
    private View view;
    private MediaPlayer player;
    private MediaController controller;

    public AudioPlayer(final View view, final Runnable onExit){
        this.view=view;
        player=new MediaPlayer();
        player.setOnPreparedListener(this);
        controller= new MediaController(view.getContext()){
            @Override
            public void hide(){

            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    release();
                    Activity activity=(Activity)view.getContext();
                    activity.finish();
                    //onExit.run();
                }

                return super.dispatchKeyEvent(event);
            }
        };

    }

    public void setAudioUri(Uri uri) throws IOException{
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(view.getContext(),uri);
        player.prepare();
        //player.start();
    }

    public void release(){
        if(player!=null){
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        ViewGroup layout = (ViewGroup)view.getParent();
        controller.setAnchorView(layout.getChildAt((layout.indexOfChild(view)+1)));
        controller.show(0);
    }

    @Override
    public void start() {
        player.start();
    }

    @Override
    public void pause() {
        player.pause();
    }

    public int getDuration() {
        return player.getDuration();
    }

    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    public void seekTo(int pos) {
        player.seekTo(pos);
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public int getAudioSessionId() {
        return player.getAudioSessionId();
    }


}
