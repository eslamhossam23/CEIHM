package com.app.eslamhossam23.testceihm;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Nb_enfant extends AppCompatActivity {
    public String[] n = new String[3];
    public String question = "Quel est le nombre de vos enfants ?";
    public int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nb_enfant);
        TextView textView = (TextView) findViewById(R.id.question_text);
        textView.setText(question);
    }
    public void creer(View v)
    {
        Button button=(Button)v;
        TextView text1=(TextView)findViewById(R.id.unite1);
        TextView text2=(TextView)findViewById(R.id.unite2);
        n[i]=(String)button.getText();
        switch (i){
            case 0:text1.setText(n[0]);break;
            case 1:  text2.setText(n[1]);break;
            case 2: break;
        }
        i++;
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
//        findViewById(R.id.guide).setVisibility(View.GONE);
        findViewById(R.id.question).setVisibility(View.GONE);
        findViewById(R.id.caculatrice).setVisibility(View.GONE);
        findViewById(R.id.partie_text).setVisibility(View.GONE);
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
                    findViewById(R.id.caculatrice).setVisibility(View.VISIBLE);
                    findViewById(R.id.partie_text).setVisibility(View.VISIBLE);
                    victoryView.setVisibility(View.GONE);
                }
            }
        });
        valueAnimator.start();
    }

    public  void valider(View v){
        showVictory();
    }
    public void annuler (View v){
        TextView text1=(TextView)findViewById(R.id.unite1);
        TextView text2=(TextView)findViewById(R.id.unite2);
        String t1= (String)text1.getText();
        String t2= (String)text2.getText();
        if(!t1.equals("_")){
            text1.setText("_");
        }
        if(!t2.equals("_")){
            text2.setText("_");
        }
        i = 0;
    }
}
