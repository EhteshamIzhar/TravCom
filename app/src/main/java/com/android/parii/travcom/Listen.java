package com.android.parii.travcom;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class Listen extends AppCompatActivity {


    TextToSpeech tts;

    int x = 1;
    String pdf;
    TextView myview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        myview=(TextView)findViewById(R.id.conversation);


        //TTS
        TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(final int status)
            {
                if (status == TextToSpeech.SUCCESS) {
                    Log.d("TTS", "Text to speech engine started successfully.");
                    tts.setLanguage(Locale.US);
                } else {
                    Log.d("TTS", "Error starting the text to speech engine.");
                }
            }
        };
        tts = new TextToSpeech(this.getApplicationContext(), listener);


        //Ends

        pdf = getIntent().getStringExtra("okay");
        myview.setText(pdf);




    }


    public void zasa(View v)
    {
        x++;
       if(x%2 == 0)
       {

        tts.speak(myview.getText(), TextToSpeech.QUEUE_ADD, null, "DEFAULT");

       }
       else
           tts.stop();
    }





}
