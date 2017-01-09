package com.app.eslamhossam23.testceihm;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AccueilActivity extends AppCompatActivity {
    public static final int PERIOD = 10000;
    public static final String PREFERENCE = "Preference";
    public static final String SEASON = "Nous sommes dans quel saison?";
    public static final String MONTH = "Nous sommes dans quel mois?";
    public static final String DAY = "Nous sommes dans quel jour?";
    public static final String BIRTHDAY = "Quelle est votre anniversaire?";
    public static final String FILM = "Quelle est votre film préféré?";
    public static final String MUSIC = "Quelle est votre musique préférée?";
    public static final String HINT_TEXT = "Appuyez sur une image pour selectionner la bonne réponse.";
    public static final int INACTIVITY_DELAY = 10000;
    List<Theme> themes = new ArrayList<>();
    public static Timer timer;
    public static List<String> questions = new ArrayList<>();
    public static List<String> reponses = new ArrayList<>();
    public static int question = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        initQuestions();
        initReponses();
        initQuestion();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        final ImageView cursor = (ImageView) findViewById(R.id.cursor);
                        showHint(HINT_TEXT);
                    }
                });
            }
        }, INACTIVITY_DELAY, PERIOD);
    }

    public void initQuestions() {
        questions.add(SEASON);
        questions.add(MONTH);
        questions.add(DAY);
        questions.add(BIRTHDAY);
//        questions.add(FILM);
//        questions.add(MUSIC);
    }

    public void initReponses() {
        reponses.add("Winter");
        reponses.add("Janvier");
        reponses.add("Lundi");
        reponses.add("19/3/1980");
//        reponses.add(PREFERENCE);
//        reponses.add(PREFERENCE);
    }

    public void initQuestion() {
        themes.add(new Theme(R.drawable.winter, "Winter"));
        themes.add(new Theme(R.drawable.autumn_small, "Autumn"));
        themes.add(new Theme(R.drawable.spring, "Spring"));
        themes.add(new Theme(R.drawable.summer, "Summer"));
        GridView gridView = (GridView) findViewById(R.id.grid);
        ArrayAdapter arrayAdapter = new AccueilActivity.ListAdapter();
        gridView.setAdapter(arrayAdapter);
        TextView textView = (TextView) findViewById(R.id.question_text);
        textView.setText(questions.get(question));
    }

    public void showHint(String hint) {
        AnimatorSet animatorSet = new AnimatorSet();
        final View guideView = findViewById(R.id.guide);
        guideView.setAlpha(0);
        guideView.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(guideView.getAlpha(), guideView.getAlpha() + 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                guideView.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        final ImageView cursor = (ImageView) findViewById(R.id.cursor);
        final float oldCursorLocationX = cursor.getX();
        final ImageView imageHolder = (ImageView) findViewById(R.id.image_placeholder);
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(cursor.getX(), imageHolder.getX() + 30f);
        valueAnimator2.setDuration(2000);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cursor.setX((Float) animation.getAnimatedValue());
                if ((float) animation.getAnimatedValue() == imageHolder.getX() + 30f) {
                    cursor.setImageResource(R.drawable.cursor_click);
                    hideHint(oldCursorLocationX);
                }
            }
        });
        animatorSet.play(valueAnimator).before(valueAnimator2);
        animatorSet.start();
        findViewById(R.id.question).setVisibility(View.INVISIBLE);
        TextView hintTextView = (TextView) guideView.findViewById(R.id.hint);
        hintTextView.setText(hint);
    }


    public void hideHint(final float oldCursorLocationX) {
        final View guideView = findViewById(R.id.guide);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(guideView.getAlpha(), 0f);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                guideView.setAlpha((Float) animation.getAnimatedValue());
                if ((float) animation.getAnimatedValue() == 0f) {
                    ImageView cursor = (ImageView) findViewById(R.id.cursor);
                    cursor.setImageResource(R.drawable.cursor);
                    cursor.setX(oldCursorLocationX);
                    guideView.setVisibility(View.INVISIBLE);
                    findViewById(R.id.question).setVisibility(View.VISIBLE);

                }
            }
        });
        valueAnimator.start();
    }


    public void showVictory() {
        final View victoryView = findViewById(R.id.victory);
        victoryView.setAlpha(0);
        victoryView.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(victoryView.getAlpha(), victoryView.getAlpha() + 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                victoryView.setAlpha((Float) animation.getAnimatedValue());
                if((Float) animation.getAnimatedValue() == 1f){
                    hideVictory();
                }
            }
        });
        valueAnimator.start();
        timer.cancel();
        findViewById(R.id.guide).setVisibility(View.INVISIBLE);
        findViewById(R.id.question).setVisibility(View.INVISIBLE);
        nextQuestion();
    }

    public void hideVictory() {
        final View victoryView = findViewById(R.id.victory);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(victoryView.getAlpha(), 0f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                victoryView.setAlpha((Float) animation.getAnimatedValue());
                if((Float)animation.getAnimatedValue() == 0){
                    findViewById(R.id.question).setVisibility(View.VISIBLE);
                }
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        question = 0;
    }

    public void nextQuestion() {
        question++;
        if(questions.size() == question){
            return;
        }
        TextView textView = (TextView) findViewById(R.id.question_text);
        textView.setText(questions.get(question));
        themes.clear();
        GridView gridView = (GridView) findViewById(R.id.grid);
        ArrayAdapter arrayAdapter;
        switch (questions.get(question)) {
            case MONTH: List<String> months = new ArrayList<>();
                months.add("Janvier");
                months.add("Février");
                months.add("Mars");
                months.add("Avril");
                months.add("Mai");
                months.add("Juin");
                months.add("Juillet");
                months.add("Août");
                months.add("Septembre");
                months.add("Octobre");
                months.add("Novembre");
                months.add("Décembre");
                arrayAdapter = new AccueilActivity.StringListAdapter(months);
                gridView.setAdapter(arrayAdapter);
                break;
            case DAY: List<String> days = new ArrayList<>();
                days.add("Samedi");
                days.add("Dimanche");
                days.add("Lundi");
                days.add("Mardi");
                days.add("Mercredi");
                days.add("Jeudi");
                days.add("Vendredi");
                arrayAdapter = new AccueilActivity.StringListAdapter(days);
                gridView.setAdapter(arrayAdapter);
                break;
            case BIRTHDAY: List<String> anniversaries = new ArrayList<>();
                anniversaries.add("29/10/1890");
                anniversaries.add("5/4/1970");
                anniversaries.add("15/3/1960");
                anniversaries.add("19/3/1980");
                arrayAdapter = new AccueilActivity.StringListAdapter(anniversaries);
                gridView.setAdapter(arrayAdapter);
                break;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        final ImageView cursor = (ImageView) findViewById(R.id.cursor);
                        showHint(HINT_TEXT);
                    }
                });
            }
        }, INACTIVITY_DELAY, PERIOD);
    }

    public void help(List<String> reponses, int reponseJuste){
        Random random = new Random();
        int removedItem = random.nextInt(reponses.size());
        while (removedItem == reponseJuste){
            removedItem = random.nextInt(reponses.size());
        }
        reponses.remove(removedItem);
        GridView gridView = (GridView) findViewById(R.id.grid);
        ArrayAdapter arrayAdapter = new AccueilActivity.StringListAdapter(reponses);
        gridView.setAdapter(arrayAdapter);
    }

    private class ListAdapter extends ArrayAdapter {

        public ListAdapter() {
            super(AccueilActivity.this, R.layout.simple_list_item_2, themes);
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.simple_list_item_2, parent, false);
            }
            final Theme theme = themes.get(position);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            imageView.setImageResource(theme.getIdImage());
            imageView.setTag(theme.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                //                String nom = theme.getName();
                @Override
                public void onClick(final View v) {
//                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(v.getY(), v.getY() + 50f);
//                    valueAnimator.setDuration(1000);
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            v.setY((Float) animation.getAnimatedValue());
//                        }
//                    });
//                    valueAnimator.start();
//                    Toast.makeText(getContext(), "Vous avez selectionné " + nom, Toast.LENGTH_SHORT).show();
                    if (!reponses.get(question).equals(PREFERENCE)) {
                        if (v.findViewById(R.id.imageView2).getTag().equals(reponses.get(question))) {
                            showVictory();
                        }
                    }
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }

    }

    private class StringListAdapter extends ArrayAdapter {
        public List<String> reponsesAvecErreurs;
        public StringListAdapter(List<String> reponses) {
            super(AccueilActivity.this, R.layout.simple_list_item_string, reponses);
            reponsesAvecErreurs = reponses;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.simple_list_item_string, parent, false);
            }
            String reponse = reponsesAvecErreurs.get(position);
            TextView textView = (TextView) itemView.findViewById(R.id.textView2);
            textView.setText(reponse);
            textView.setTag(reponse);
            textView.setOnClickListener(new View.OnClickListener() {
                //                String nom = theme.getName();
                @Override
                public void onClick(final View v) {
//                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(v.getY(), v.getY() + 50f);
//                    valueAnimator.setDuration(1000);
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            v.setY((Float) animation.getAnimatedValue());
//                        }
//                    });
//                    valueAnimator.start();
//                    Toast.makeText(getContext(), "Vous avez selectionné " + nom, Toast.LENGTH_SHORT).show();
                    if (!reponses.get(question).equals(PREFERENCE)) {
                        if (v.findViewById(R.id.textView2).getTag().equals(reponses.get(question))) {
                            showVictory();
                        }else {
                            help(reponsesAvecErreurs, reponsesAvecErreurs.indexOf(reponses.get(question)));
                        }
                    }
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }
    }
}