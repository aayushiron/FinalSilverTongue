package com.example.aayushiron.sivertongue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projects.alshell.vokaturi.EmotionProbabilities;
import com.projects.alshell.vokaturi.Vokaturi;
import com.projects.alshell.vokaturi.VokaturiException;

public class emo extends AppCompatActivity {

    TextView happiness, anger, sadness, fear, neutrality, mainEmotion, instructions;
    ProgressBar happy, angry, sad, afraid, neutral;
    Vokaturi vokaturiApi;
    String emotion;
    double happiness1, sadness1, neutrality1, angriness1, fear1;
    ImageButton startRecording, stopRecording, goBack;
    EmotionProbabilities emotionProbabilities;
    LinearLayout results, recording;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo);
        try {
            vokaturiApi = Vokaturi.getInstance(getApplicationContext());
        } catch (VokaturiException e) {
            e.printStackTrace();
        }

        stopRecording = findViewById(R.id.imageButton4);
        startRecording = findViewById(R.id.imageButton3);

        mainEmotion = findViewById(R.id.textView6);

        instructions = findViewById(R.id.textView3);

        recording = findViewById(R.id.ll2);
        results = findViewById(R.id.ll1);

        happiness = findViewById(R.id.textView8);
        happy = findViewById(R.id.progressBar);

        anger = findViewById(R.id.textView9);
        angry = findViewById(R.id.progressBar2);

        sadness = findViewById(R.id.textView10);
        sad = findViewById(R.id.progressBar3);

        fear = findViewById(R.id.textView11);
        afraid = findViewById(R.id.progressBar4);

        neutrality = findViewById(R.id.textView12);
        neutral = findViewById(R.id.progressBar5);

        goBack = findViewById(R.id.imageButton);
        goBack.setVisibility(View.GONE);

        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    vokaturiApi.startListeningForSpeech();
                    stopRecording.setVisibility(View.VISIBLE);
                    startRecording.setVisibility(View.GONE);
                    instructions.setText("Press the button below to finish recording");
                } catch (VokaturiException e) {
                    e.printStackTrace();
                }
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    emotionProbabilities = vokaturiApi.stopListeningAndAnalyze();
                    displayResults();
                    goBack.setVisibility(View.VISIBLE);
                } catch (VokaturiException e) {
                    e.printStackTrace();
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void displayResults () {
        recording.setVisibility(View.GONE);
        results.setVisibility(View.VISIBLE);

        emotionProbabilities.scaledValues(5);
        com.projects.alshell.vokaturi.Emotion capturedEmotion = Vokaturi.extractEmotion(emotionProbabilities);
        emotion = capturedEmotion.toString();
        happiness1 = emotionProbabilities.Happiness * 100;
        neutrality1 = emotionProbabilities.Neutrality * 100;
        angriness1 = emotionProbabilities.Anger * 100;
        sadness1 = emotionProbabilities.Sadness * 100;
        fear1 = emotionProbabilities.Fear * 100;

        mainEmotion.setText(emotion);

        happiness.setText("Happiness: " + Double.toString(happiness1) + "%");
        anger.setText("Anger: " + Double.toString(angriness1) + "%");
        sadness.setText("Sadness: " + Double.toString(sadness1) + "%");
        fear.setText("Fear: " + Double.toString(fear1) + "%");
        neutrality.setText("Neutrality: " + Double.toString(neutrality1) + "%");

        happy.setProgress((int) happiness1);
        angry.setProgress((int) angriness1);
        sad.setProgress((int) sadness1);
        afraid.setProgress((int) fear1);
        neutral.setProgress((int) neutrality1);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
