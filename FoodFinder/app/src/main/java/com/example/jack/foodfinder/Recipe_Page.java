package com.example.jack.foodfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Recipe_Page extends AppCompatActivity {

    String title;
    String f2f_url;
    String publisher;
    String source_url;
    String image_url;
    String publisher_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__page);

        //collected data passed from intent, contains all recipe information
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            f2f_url = extras.getString("f2f_url");
            publisher = extras.getString("publisher");
            source_url = extras.getString("source_url");
            image_url = extras.getString("image_url");
            publisher_url = extras.getString("publisher_url");
        }

        //Create TextViews
        TextView tv1 = (TextView) findViewById(R.id.titleText);
        TextView tv2 = (TextView) findViewById(R.id.publisherText);
        ImageView iv1 = (ImageView) findViewById(R.id.recipeImage);
        //set relevant data to textViews
        tv1.setText(title);
        tv2.setText("Publisher: " + publisher);
        //set ImageView using Picasso
        Picasso.with(Recipe_Page.this).load(image_url).into(iv1);

    }
    public void f2fClick(View view){
        //Open f2f URL
        Uri uri = Uri.parse(f2f_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void publisherClick(View view){
        //Open publisher URL
        Uri uri = Uri.parse(publisher_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
