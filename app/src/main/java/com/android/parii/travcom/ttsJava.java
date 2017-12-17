package com.android.parii.travcom;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private String line = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(getApplicationContext(), this);
        final TextView text1 = (TextView) findViewById(R.id.textView1);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            private String[] arr;
            @Override
            public void onClick(View v) {
                File sdcard = Environment.getExternalStorageDirectory();
                // Get the text file
                File file = new File(sdcard, "test.pdf");
                // ob.pathh
                // Read text from file
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    // int i=0;
                    List<String> lines = new ArrayList<String>();
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                        // arr[i]=line;
                        // i++;
                        text.append(line);
                        text.append('\n');
                    }
                    for (String string : lines) {
                        tts.speak(string, TextToSpeech.SUCCESS, null);
                    }
                    arr = lines.toArray(new String[lines.size()]);
                    System.out.println(arr.length);
                    text1.setText(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                // speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}*/

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ttsJava extends Activity {

    FirebaseDatabase database;
    DatabaseReference category;

    Button b;
    EditText textmsg;
    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts_java);


        //Init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        b= (Button)findViewById(R.id.button1);
        textmsg=(EditText)findViewById(R.id.editText1);
    }

    // write text to file
    public void WriteBtn(View v)
    {
        // add-write text into file
        try
        {
            FileOutputStream fileout=openFileOutput("abbrev-deu.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();


            textmsg.setBackgroundColor(Color.WHITE);
            textmsg.setTextColor(Color.WHITE);
           // v.setVisibility(i);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.WHITE);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void ReadBtn(View v)
    {
        //reading text from file
        try
        {
            FileInputStream fileIn=openFileInput("abbrev-deu.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }

            Intent i = new Intent(ttsJava.this,Listen.class);
            i.putExtra("okay",s);
            startActivity(i);
         //   InputRead.close();
          //  textmsg.setText(s);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
