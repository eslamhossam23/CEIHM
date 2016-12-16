package com.app.eslamhossam23.testceihm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Eslam on 0016 16/12/2016.
 */

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        String prenom = getIntent().getExtras().getString("prenom");
        Toast.makeText(this, prenom, Toast.LENGTH_SHORT).show();
    }
}
