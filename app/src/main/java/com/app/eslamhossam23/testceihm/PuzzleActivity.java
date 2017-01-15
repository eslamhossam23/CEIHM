package com.app.eslamhossam23.testceihm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {
    public List<Bitmap> suggestions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        initSuggestions();
        initImage();
        CustomAdapter customAdapter = new CustomAdapter();
        ListView listView = (ListView) findViewById(R.id.suggestions);
        listView.setAdapter(customAdapter);
    }

    private void initImage() {
        ImageView imageView = (ImageView) findViewById(R.id.image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.autumn_small);
//            imageView.setImageBitmap(Bitmap.createBitmap(bitmapSource, 0, 0 , bitmapSource.getWidth()/2, bitmapSource.getHeight()));
            Bitmap bitmap = null;
            bitmap = Bitmap.createBitmap(bitmapSource.getWidth(), bitmapSource.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(Bitmap.createBitmap(bitmapSource, 0, 0 , bitmapSource.getWidth()/2, bitmapSource.getHeight()), 0f, 0f, null);
            canvas.drawBitmap(Bitmap.createBitmap(bitmapSource, bitmapSource.getWidth()/2, 0 , bitmapSource.getWidth()/2, bitmapSource.getHeight()/2), bitmapSource.getWidth()/2, 0f, null);
            imageView.setImageBitmap(bitmap);
        }
    }

    private void initSuggestions() {
        Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.autumn_small);
        suggestions.add(Bitmap.createBitmap(bitmapSource, 0, 0 , bitmapSource.getWidth()/2, bitmapSource.getHeight()/2));
        suggestions.add(Bitmap.createBitmap(bitmapSource, bitmapSource.getWidth()/2, 0 , bitmapSource.getWidth()/2, bitmapSource.getHeight()/2));
        suggestions.add(Bitmap.createBitmap(bitmapSource, 0, bitmapSource.getHeight()/2 , bitmapSource.getWidth()/2, bitmapSource.getHeight()/2));
        suggestions.add(Bitmap.createBitmap(bitmapSource, bitmapSource.getWidth()/2, bitmapSource.getHeight()/2 , bitmapSource.getWidth()/2, bitmapSource.getHeight()/2));
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
