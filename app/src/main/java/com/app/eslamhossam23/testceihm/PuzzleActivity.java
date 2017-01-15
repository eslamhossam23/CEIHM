package com.app.eslamhossam23.testceihm;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleActivity extends AppCompatActivity {
    public List<Bitmap> suggestions = new ArrayList<>();
    public List<Integer> questions = new ArrayList<>();
    public static int currentQuestion = 0;
    public static int missingPiece;
    public static final int PERIOD = 20000;
    public static final int INACTIVITY_DELAY = 20000;
    public static final String HINT_TEXT = "Appuyez sur une image pour selectionner la bonne réponse.";
    public static Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        initQuestions();
        initSuggestions();
        CustomAdapter customAdapter = new CustomAdapter();
        GridView gridView = (GridView) findViewById(R.id.suggestions);
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == missingPiece){
                    showVictory();
                    pauseTimer();
                    startTimer();
                }else{
                    help();
                    pauseTimer();
                    startTimer();
                }
            }
        });
        TextView textView = (TextView) findViewById(R.id.question_text);
        textView.setText("Choisissez la partie qui manque.");
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
        }, 500, PERIOD);
    }

    private void startTimer() {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pauseTimer();
        startTimer();
        return super.onTouchEvent(event);
    }

    private void pauseTimer() {
        timer.cancel();
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
        findViewById(R.id.question).setVisibility(View.GONE);
        findViewById(R.id.suggestions).setVisibility(View.GONE);
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
                    guideView.setVisibility(View.GONE);
                    findViewById(R.id.question).setVisibility(View.VISIBLE);
                    findViewById(R.id.suggestions).setVisibility(View.VISIBLE);
                }
            }
        });
        valueAnimator.start();
    }


    private void initQuestions() {
        questions.add(R.drawable.old_7);
        questions.add(R.drawable.old_8);
        questions.add(R.drawable.old_9);
        questions.add(R.drawable.old_10);
        questions.add(R.drawable.old_11);
    }

    private void showVictory() {
        final View victoryView = findViewById(R.id.victory);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), questions.get(currentQuestion));
        imageView.setImageBitmap(bitmapSource);
        victoryView.setAlpha(0);
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
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator2.setDuration(2000);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageView.setAlpha((Float) animation.getAnimatedValue());
                if((Float) animation.getAnimatedValue() == 1f){
                    victoryView.setVisibility(View.VISIBLE);
                    findViewById(R.id.guide).setVisibility(View.GONE);
                    findViewById(R.id.question).setVisibility(View.GONE);
                    findViewById(R.id.suggestions).setVisibility(View.GONE);
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).after(valueAnimator2);
        animatorSet.start();
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
//                    findViewById(R.id.question).setVisibility(View.VISIBLE);
                    victoryView.setVisibility(View.GONE);
                }
            }
        });
        valueAnimator.start();
        nextQuestion();
    }

    private void nextQuestion() {
        currentQuestion++;
        if(currentQuestion == questions.size()){
            return;
        }
        createPuzzle(2);
        findViewById(R.id.question).setVisibility(View.VISIBLE);
        findViewById(R.id.suggestions).setVisibility(View.VISIBLE);
    }

    private void help() {
        Random random = new Random();
        int removedItem = random.nextInt(suggestions.size());
        while (removedItem == missingPiece){
            removedItem = random.nextInt(suggestions.size());
        }
        suggestions.remove(removedItem);
        GridView gridView = (GridView) findViewById(R.id.suggestions);
        ArrayAdapter arrayAdapter = new CustomAdapter();
        gridView.setAdapter(arrayAdapter);
        showSecondChance();
    }

    private void showSecondChance() {
        final View tryAgain = findViewById(R.id.secondChance);
        tryAgain.setAlpha(0);
        tryAgain.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tryAgain.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1f, 0f);
        valueAnimator2.setDuration(1500);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tryAgain.setAlpha((Float) animation.getAnimatedValue());
                if((float)animation.getAnimatedValue() == 0f){
                    tryAgain.setVisibility(View.GONE);
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).before(valueAnimator2);
        animatorSet.start();
    }



    private void initSuggestions() {
        createPuzzle(2);
    }

    private void createPuzzle(int division) {
        suggestions.clear();
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), questions.get(currentQuestion));
        Bitmap bitmap = Bitmap.createBitmap(bitmapSource.getWidth(), bitmapSource.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Random random = new Random();
        missingPiece = random.nextInt((int) Math.pow(division, 2));
        int count = 0;
        for(int i = 0; i < division; i++){
            for (int j = 0; j < division; j++){
                Bitmap piece = Bitmap.createBitmap(bitmapSource, (j * bitmapSource.getWidth())/division, (i * bitmapSource.getHeight())/division, bitmapSource.getWidth()/division, bitmapSource.getHeight()/division);
                suggestions.add(piece);
                if(missingPiece != count){
                    canvas.drawBitmap(piece, (j * bitmapSource.getWidth())/division, (i * bitmapSource.getHeight())/division, null);
                }
                count++;
            }
        }
        imageView.setImageBitmap(bitmap);
        Bitmap rightBitmap = suggestions.get(missingPiece);
        Collections.shuffle(suggestions);
        missingPiece = suggestions.indexOf(rightBitmap);
    }

    public class CustomAdapter extends ArrayAdapter {

        public CustomAdapter() {
            super(PuzzleActivity.this, R.layout.list_item_image, suggestions);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.list_item_image, parent, false);
            }
            ImageView imageView = (ImageView) itemView.findViewById(R.id.suggestion);
            imageView.setImageBitmap(suggestions.get(position));
            return itemView;
        }
    }
}
