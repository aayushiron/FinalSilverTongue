package com.example.aayushiron.sivertongue;

import android.content.Intent;
import android.os.SystemClock;
import android.os.health.SystemHealthManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class spee extends AppCompatActivity {

    LinearLayout recording, results;
    ImageButton start, back;
    TextView instructions, worpm, guide;
    double startTime, endTime;
    double convertion = 60000;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spee);

        recording = findViewById(R.id.ll2);
        results = findViewById(R.id.ll1);
        start = findViewById(R.id.imageButton3);
        instructions = findViewById(R.id.textView3);
        worpm = findViewById(R.id.textView5);
        guide = findViewById(R.id.textView22);
        back = findViewById(R.id.imageButton2);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Begin Speaking");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = (double) SystemClock.elapsedRealtime();
                if (mSpeechRecognizerIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(mSpeechRecognizerIntent, 10);
                } else {
                    instructions.setText("Your device does not support speech input");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    endTime = (double) SystemClock.elapsedRealtime();
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    recording.setVisibility(View.GONE);
                    results.setVisibility(View.VISIBLE);
                    double minutes = (endTime - startTime)/convertion;
                    int wpm = (int)(countWords(result.get(0))/minutes);
                    countDownAnim(worpm, wpm);
                    if (wpm <= 150 && wpm >= 110) {
                        guide.setText("You are speaking just right");
                    } else if (wpm < 110) {
                        guide.setText("Try to speak " + Integer.toString(110 - wpm) + " wpm more.");
                    } else if (wpm > 150) {
                        guide.setText("Try to speak " + Integer.toString(wpm - 150) + " wpm less.");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "nope", Toast.LENGTH_LONG).show();
                }
        }
    }

    public static long countWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }

    public void countDownAnim(final TextView txt, final int w) {
        new Thread(new Runnable() {
            int counter = 0;
            public void run() {
                while (counter < w + 1) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    txt.post(new Runnable() {

                        public void run() {
                            txt.setText("" + counter);

                        }

                    });
                    counter++;
                }

            }

        }).start();
    }
}
