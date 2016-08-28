package com.example.jack.foodfinder;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//Custom Array adapter used to create custom ListView to suit needs of the program
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    // declaring the ArrayList of custom Type; Recipe
    private ArrayList<Recipe> objects;

    // Override the constructor for ArrayAdapter
    public RecipeAdapter(Context context, int textViewResourceId, ArrayList<Recipe> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }
    // Override getView for ArrayAdapter
    public View getView(int position, View convertView, ViewGroup parent){

        // store the view that is being converted
        View v = convertView;

        // inflate the view
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_layout, null);
        }

		// Gets recipe at current position, stores to local variable
        Recipe i = objects.get(position);

        if (i != null) {
            //Create a TextView/ImageView for each element in Lisview_layout.xml
            TextView tt = (TextView) v.findViewById(R.id.topText);
            TextView mt = (TextView) v.findViewById(R.id.middleText);
            TextView mtd = (TextView) v.findViewById(R.id.middleTextData);
            ImageView iv1 = (ImageView) v.findViewById(R.id.recipeImage);

            // check to see if each individual display element is null
            // if not, assign appropriate information from the Recipe
            // External Library; Picasso is used for the process of image_url to ImageView
            if (tt != null){
                tt.setText(i.getTitle());
            }
            if (mt != null){
                mt.setText("Publisher: ");
            }
            if (mtd != null){
                mtd.setText(i.getPublisher());
            }
            if (iv1 != null){
                Picasso.with(getContext()).load(i.getImage_url()).into(iv1);
            }
        }
        // return the view to the activity
        return v;
    }
}