package com.example.jack.foodfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import android.view.MenuItem;
import android.content.Intent;

public class Main__Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main___menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void findRecipeClick(View view) {
        // set the user input to variable; ingredient
        EditText et1 = (EditText)findViewById(R.id.inputIngredient);
        String ingredient = et1.getText().toString().trim();

        // open listview activity, pass ingredient
        Intent recipeIntent = new Intent(this, Recipe_List.class);
        recipeIntent.putExtra("passedIngredient", ingredient);
        startActivity(recipeIntent);
    }
}