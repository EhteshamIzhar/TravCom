package com.android.parii.travcom;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class songs extends AppCompatActivity {

    float x;
    TextView prob;

    String Happy = "https://www.youtube.com/results?search_query=Happy+mood+Songs+Playlist";
    String Sad = "https://www.youtube.com/results?search_query=emotional%27+Songs+List";
    String Energy ="https://www.youtube.com/results?search_query=Energitic+Songs+List";
    String Chill = "https://www.youtube.com/results?search_query=Energitic+Songs+List";
    String okay = "https://www.youtube.com/results?search_query=Energitic+Songs+List";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        prob=(TextView)findViewById(R.id.smile);

        x = getIntent().getFloatExtra("x",0);

        if(x>0.75) {
            prob.setText("Happy");
            prob.setTextColor(Color.GREEN);
        }
        else if(x>0.6) {
            prob.setText("Chill");
            prob.setTextColor(Color.BLUE);
        }
        else if(x>0.5) {
            prob.setText("Emotional");
            prob.setTextColor(Color.DKGRAY);
        }
        else {
            prob.setText("Upset");
            prob.setTextColor(Color.RED);
        }


    }


    public void webb(View v) {

        String url;
        if(x>0.75)
            url=Happy;
        else if(x>0.6)
            url=Chill;
        else if(x>0.5)
            url=Energy;
        else
            url =Happy;

        Intent i = new Intent(songs.this,Webview.class);
        i.putExtra("url",url);
        startActivity(i);
    }
}
