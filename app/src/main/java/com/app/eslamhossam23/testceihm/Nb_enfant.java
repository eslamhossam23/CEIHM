package com.app.eslamhossam23.testceihm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Nb_enfant extends AppCompatActivity {
    public String[] n=new String[3];
public String question="Quelle est le nombre de vos enfants ?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nb_enfant);
    }
    public void creer(View v)
    {
        Button button=(Button)v;
        TextView text1=(TextView)findViewById(R.id.unite1);
        TextView text2=(TextView)findViewById(R.id.unite2);
       int i=0;
        n[i]=(String)button.getText();
       switch (i){
               case 1:text1.setText(n[1]);

               case2:  text2.setText(n[2]);
               case3:break ;
       }

        i++;
    }
    public  void valider(View v){

    }
    public void annuler (View v){
        TextView text1=(TextView)findViewById(R.id.unite1);
        TextView text2=(TextView)findViewById(R.id.unite2);
        String t1= (String)text1.getText();
        String t2= (String)text2.getText();
        if(! t1.equals("_")){text1.setText("_");}
        else if (! t2.equals("_")){text1.setText("_");}
    }
}
