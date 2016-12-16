package com.app.eslamhossam23.testceihm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    List<Profil> profilList = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int i = 0; i < 15; i ++) {
            if(i % 2 == 0){
                profilList.add(new Profil("Hossam", "Eslam", R.drawable.avatar_male));
            }else {
                profilList.add(new Profil("Chebaane", "Meriem", R.drawable.avatar_female));
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getBaseContext();
        GridView gridView = (GridView) findViewById(R.id.listProfils);
        ArrayAdapter arrayAdapter = new ListAdapter();
        gridView.setAdapter(arrayAdapter);

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
                String prenom = profil.getPrenom();
                String nom = profil.getNom();
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Hello " + prenom, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("prenom", prenom);
                    startActivity(intent);
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }
    }
}

