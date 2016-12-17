package com.app.eslamhossam23.testceihm;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

=======
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

>>>>>>> a91ba8039f199156ffbf28d95f156774942a923f
/**
 * Created by Eslam on 0016 16/12/2016.
 */

public class GameActivity extends AppCompatActivity {
<<<<<<< HEAD
    List<Theme> themes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        themes.add(new Theme(R.drawable.accueil, "Accueil"));
        themes.add(new Theme(R.drawable.mariage, "Mariage"));
        themes.add(new Theme(R.drawable.famille, "Famille"));
        themes.add(new Theme(R.drawable.enfants, "Enfants"));
=======

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
>>>>>>> a91ba8039f199156ffbf28d95f156774942a923f
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        String prenom = getIntent().getExtras().getString("prenom");
        Toast.makeText(this, prenom, Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
        GridView gridView = (GridView) findViewById(R.id.listCategories);
        ArrayAdapter arrayAdapter = new ListAdapter();
        gridView.setAdapter(arrayAdapter);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                String nom = theme.getName();
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Vous avez selectionné " + nom, Toast.LENGTH_SHORT).show();
                }
            });
            return itemView;
//            return super.getView(position, convertView, parent);
        }
=======
>>>>>>> a91ba8039f199156ffbf28d95f156774942a923f
    }
}
