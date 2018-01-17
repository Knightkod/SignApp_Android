package eus.ehu.intel.signapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AlphabetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        insertAlphabImages();
       }

    private void insertAlphabImages(){
        ImageView imgView;
        String[] alph={"a","b","c","d","e","f","g","h","i",
                "j","k","l","m","n","o","p","q","r","s","t",
                "u","v","w","x","y","z"};
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.alphabetScrollLinLayout);
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
               LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<alph.length;i++) {
            imgView = new ImageView(this);
            imgView.setImageResource(getResources().getIdentifier(alph[i],"drawable",this.getPackageName()));
           imgView.setLayoutParams(params);
            linearLayout.addView(imgView);
        }
    }
}
