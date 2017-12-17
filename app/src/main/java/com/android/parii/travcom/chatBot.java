package com.android.parii.travcom;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.util.Locale;

public class chatBot extends AppCompatActivity {

    // private int x=0;
    private TextView conversation;
    private EditText userInput;
    private ConversationService myConversationService = null;


    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

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




        conversation = (TextView)findViewById(R.id.conversation);
        userInput = (EditText)findViewById(R.id.user_input);


        myConversationService =
                new ConversationService(
                        "2017-05-26",
                        getString(R.string.username),
                        getString(R.string.password)
                );


        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv,
                                          int action, KeyEvent keyEvent) {
                if(action == EditorInfo.IME_ACTION_DONE )
                {
                    // More code here
                    //x=0;

                    final String inputText = userInput.getText().toString();
                    conversation.append(
                            Html.fromHtml("<p><b>You:</b> " + inputText + "</p>")
                    );

                    // Optionally, clear edittext
                    userInput.setText("");

                    MessageRequest request = new MessageRequest.Builder()
                            .inputText(inputText)
                            .build();

                    myConversationService.
                            message(getString(R.string.workspace), request)
                            .enqueue(new ServiceCallback<MessageResponse>() {
                                @Override
                                public void onResponse(MessageResponse response)
                                {
                                    // More code here

                                    final String outputText = response.getText().get(0);
                                    tts.speak(outputText, TextToSpeech.QUEUE_ADD, null, "DEFAULT");

                                    runOnUiThread(new Runnable()
                                    {

                                        @Override
                                        public void run() {
                                            conversation.append(
                                                    Html.fromHtml("<p><b>Parii:</b> " +
                                                            outputText + "</p>")
                                            );
                                        }
                                    });



                                    if(response.getIntents().get(0).getIntent()
                                            .endsWith("RequestQuote")) {
                                        // More code here
                                        String quotesURL =
                                                "https://api.forismatic.com/api/1.0/" +
                                                        "?method=getQuote&format=text&lang=en";

                                        Fuel.get(quotesURL)
                                                .responseString(new Handler<String>() {
                                                    @Override
                                                    public void success(Request request,
                                                                        Response response, String quote)
                                                    {

                                                        Log.d("Success","To ho gaya, ab kya");

                                                        tts.speak(quote, TextToSpeech.QUEUE_ADD, null, "DEFAULT");

                                                        conversation.append(
                                                                Html.fromHtml("<p><b>Bot:</b> " +
                                                                        quote + "</p>")
                                                        );
                                                    }

                                                    @Override
                                                    public void failure(Request request,
                                                                        Response response,
                                                                        FuelError fuelError) {
                                                    }
                                                });

                                    }


                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.d("Fail","Ho gaya hai bro");
                                }


                            });

                }

                return false;
            }



        });






    }


    // public void send(View v) {

    //       x=1;


    //  }






}

