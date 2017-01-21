package com.app.eslamhossam23.testceihm;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    List<Profil> profilList = new ArrayList<>();
    Context context;
    public static Timer timer;
    public static final String HINT_TEXT = "Appuyez pour selectionner votre image.";
    public static final int PERIOD = 20000;
    public static final int INACTIVITY_DELAY = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        profilList.add(new Profil("Dubois","Vanessa", R.drawable.old_1));
        profilList.add(new Profil("Durand", "Roger", R.drawable.old_2));
//        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_4));
//        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_5));
        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_6));
//        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_7));
//        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_8));
//        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_3_small));
        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_11));
//        profilList.add(new Profil("Matthew", "Pierre", R.drawable.old_11));

        timer = new Timer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getBaseContext();
        GridView gridView = (GridView) findViewById(R.id.listProfils);
        ArrayAdapter arrayAdapter = new ListAdapter();
        gridView.setAdapter(arrayAdapter);
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
        findViewById(R.id.listProfils).setVisibility(View.INVISIBLE);
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
                    findViewById(R.id.listProfils).setVisibility(View.VISIBLE);
                }
            }
        });
        valueAnimator.start();
    }


    private class ListAdapter extends ArrayAdapter {
        public ListAdapter() {
            super(context, R.layout.simple_list_item_1, profilList);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.simple_list_item_1, parent, false);
            }
            final Profil profil = profilList.get(position);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(profil.getIdImage());
//            TextView textView = (TextView) itemView.findViewById(R.id.nom);
//            textView.setText(profil.getNom());
//            TextView textView1 = (TextView) itemView.findViewById(R.id.prenom);
//            textView1.setText(profil.getPrenom());
            itemView.setOnClickListener(new View.OnClickListener() {
                int idImage = profil.getIdImage();
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Hello " + prenom, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("image", idImage);
                    startActivity(intent);
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }
    }
}

