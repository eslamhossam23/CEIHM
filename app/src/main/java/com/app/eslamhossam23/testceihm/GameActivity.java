package com.app.eslamhossam23.testceihm;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
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


/**
 * Created by Eslam on 0016 16/12/2016.
 */

public class GameActivity extends AppCompatActivity {
    //Periode entre deux affichages successifs de hints
    public static final int PERIOD = 20000;
    public static final int INACTIVITY_DELAY = 20000;
    public static final String HINT_TEXT = "Appuyez sur un thème pour le sélectionner.";
    List<Theme> themes = new ArrayList<>();
    public static Timer timer;
    public static int imageID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        imageID = getIntent().getIntExtra("image", R.drawable.accueil);
        themes.add(new Theme(imageID, "Accueil"));
        themes.add(new Theme(R.drawable.mariage, "Mariage"));
        themes.add(new Theme(R.drawable.family, "Famille"));
        themes.add(new Theme(R.drawable.children_1, "Enfants"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
//        String prenom = getIntent().getExtras().getString("prenom");
//        Toast.makeText(this, prenom, Toast.LENGTH_SHORT).show();
        GridView gridView = (GridView) findViewById(R.id.listCategories);
        ArrayAdapter arrayAdapter = new ListAdapter();
        gridView.setAdapter(arrayAdapter);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                        final ImageView cursor = (ImageView) findViewById(R.id.cursor);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showHint(HINT_TEXT);
                    }
                });

            }
        }, 500, PERIOD);

    }

    public void showHint(final String hint) {
        final AnimatorSet animatorSet = new AnimatorSet();
        final View guideView = findViewById(R.id.guide);
        guideView.setAlpha(0);
        guideView.setVisibility(View.VISIBLE);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(guideView.getAlpha(), guideView.getAlpha() + 1f);
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
        final ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(cursor.getX(), imageHolder.getX() + 30f);
        valueAnimator2.setDuration(2000);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cursor.setX((Float) animation.getAnimatedValue());
                if((float) animation.getAnimatedValue() == imageHolder.getX() + 30f){
                    cursor.setImageResource(R.drawable.cursor_click);
                    hideHint(oldCursorLocationX);
                }
            }
        });
        animatorSet.play(valueAnimator).before(valueAnimator2);
        animatorSet.start();
        TextView hintTextView = (TextView) guideView.findViewById(R.id.hint);
        hintTextView.setText(hint);
        findViewById(R.id.listCategories).setVisibility(View.INVISIBLE);
    }


    public void hideHint(final float oldCursorLocationX) {
        final View guideView = findViewById(R.id.guide);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(guideView.getAlpha(), 0f);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                guideView.setAlpha((Float) animation.getAnimatedValue());
                if((float) animation.getAnimatedValue() == 0f){
                    ImageView cursor = (ImageView) findViewById(R.id.cursor);
                    cursor.setImageResource(R.drawable.cursor);
                    cursor.setX(oldCursorLocationX);
                    guideView.setVisibility(View.INVISIBLE);
                    findViewById(R.id.listCategories).setVisibility(View.VISIBLE);
                }
            }
        });
        valueAnimator.start();
    }

    private class ListAdapter extends ArrayAdapter {
        public ListAdapter() {
            super(GameActivity.this, R.layout.simple_list_item_2, themes);
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
            imageView.setTag(theme.getIdImage());
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
                    if((int) v.findViewById(R.id.imageView2).getTag() == imageID){
                        Intent intent = new Intent(GameActivity.this, AccueilActivity.class);
                        startActivity(intent);
                    }else if((int) v.findViewById(R.id.imageView2).getTag() == R.drawable.mariage) {
                        Intent intent = new Intent(GameActivity.this, PuzzleActivity.class);
                        startActivity(intent);
                    }
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }
    }

}
