package com.example.aayushiron.sivertongue;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class filler extends AppCompatActivity {

    LinearLayout enterPage, recording, results;
    ImageButton next, start;
    EditText input;
    String[] fillers;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    TextView instructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filler);

        enterPage = findViewById(R.id.ll1);
        recording = findViewById(R.id.ll2);
        results = findViewById(R.id.ll3);
        next = findViewById(R.id.imageButton5);
        input = findViewById(R.id.editText);
        start = findViewById(R.id.imageButton3);
        instructions = findViewById(R.id.textView3);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Begin Speaking");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = input.getText().toString();
                if (temp == null || temp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter something into the box", Toast.LENGTH_LONG).show();
                } else {
                    fillers = makeArrayOfInput(temp);
                    enterPage.setVisibility(View.GONE);
                    recording.setVisibility(View.VISIBLE);
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSpeechRecognizerIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(mSpeechRecognizerIntent, 10);
                } else {
                    instructions.setText("Your device does not support speech input");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    recording.setVisibility(View.GONE);
                    results.setVisibility(View.VISIBLE);
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.modeka);
                    for (int i = 0; i < fillers.length; i++) {
                        TextView textView = new TextView(this);
                        textView.setText(fillers[i] + ": " + Integer.toString(count(result.get(0).toLowerCase(), fillers[i].toLowerCase())) + " times.");
                        textView.setTextColor(Color.parseColor("#dfdfdf"));
                        textView.setTypeface(typeface);
                        textView.setTextSize(35);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        textView.setPadding(0, 0, 0, 25);
                        results.addView(textView);
                    }
                    ImageButton goBack = new ImageButton(this);
                    goBack.setImageResource(R.drawable.go_back);
                    goBack.setBackgroundColor(Color.TRANSPARENT);
                    goBack.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
                    goBack.setId(R.id.imageButton2);
                    results.addView(goBack);
                    goBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "nope", Toast.LENGTH_LONG).show();
                }
        }
    }

    public String[] makeArrayOfInput (String s)  {
        String[] words = s.split("\\s+");
        return words;
    }

    public static int count(String text, String find) {
        int index = 0, count = 0, length = find.length();
        while( (index = text.indexOf(find, index)) != -1 ) {
            index += length; count++;
        }
        return count;
    }
}
