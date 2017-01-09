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
import java.util.Timer;
import java.util.TimerTask;

public class AccueilActivity extends AppCompatActivity {
    public static final int PERIOD = 10000;
    List<Theme> themes = new ArrayList<>();
    public static Timer timer;
    public static String REPONSE = "Winter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        themes.add(new Theme(R.drawable.winter, "Winter"));
        themes.add(new Theme(R.drawable.autumn_small, "Autumn"));
        themes.add(new Theme(R.drawable.spring, "Spring"));
        themes.add(new Theme(R.drawable.summer, "Summer"));

        GridView gridView = (GridView) findViewById(R.id.grid);
        ArrayAdapter arrayAdapter = new AccueilActivity.ListAdapter();
        gridView.setAdapter(arrayAdapter);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        final ImageView cursor = (ImageView) findViewById(R.id.cursor);
                        showHint("Appuyez sur une image pour selectionner la bonne réponse.");
                    }
                });
            }
        }, 2000, PERIOD);
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
            }
        });
        valueAnimator.start();
        timer.cancel();
        findViewById(R.id.guide).setVisibility(View.INVISIBLE);
        findViewById(R.id.question).setVisibility(View.INVISIBLE);
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
                    if(v.findViewById(R.id.imageView2).getTag().equals(REPONSE)){
                        showVictory();
                    }
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }


    }
}