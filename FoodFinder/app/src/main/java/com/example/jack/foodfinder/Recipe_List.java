package com.example.jack.foodfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Recipe_List extends AppCompatActivity {

    //ingredient is passed from the previous page and contains the string the user input on the main menu
    String ingredient;
    //json is the string passed into the jparser, it is retrieved via httpConnect or savedJson.txt
    String json;
    //recipeArray is used to store all the recipes when the JSON is parsed.
    ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();
    //connected is used to determine the method to be used to retrieve json data.
    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__list);

        //Retrieve the user input from the main menu
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ingredient = extras.getString("passedIngredient");
        }

        //Set a textView to show search term
        TextView tv1 = (TextView) findViewById(R.id.ingredientText);
        tv1.setText("Recipes with " + ingredient);

        //Check if there is a connection, set bool connected to be used later
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //Connection is detected
            Toast.makeText(Recipe_List.this, "Connection Successful", Toast.LENGTH_SHORT).show();
            connected = true;
        } else {
            //No connection detected, retrieve json from local storage
            Toast.makeText(Recipe_List.this, "Connection Failed - Using last Search result", Toast.LENGTH_SHORT).show();
            connected = false;
            json = readFromFile();
        }
        new AsyncTaskParseJson().execute();
    }

    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
        //Create API url using search term, removes any spaces to avoid breaking
        String food2forkrequest = "http://food2fork.com/api/search?key=8ebf34f93ed3716ca8f6ff91b69c8ce9&q=" + ingredient.replaceAll(" ","");
        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String...arg0) {
            try{
                //Check if json is to be retrieved based on connection.
                if(connected){
                    //if there is a connection retrieve json from network
                    httpConnect jParser = new httpConnect();
                    json = jParser.getJSONFromUrl(food2forkrequest);
                    writeToFile(json);
                }

                //Trim relevant data from json, set to new json array
                json = json.substring(25, json.length() - 1);
                JSONArray jsonArray = new JSONArray(json);

                //loop through json objects
                for (int i = 0; 1 < jsonArray.length(); i++) {
                    JSONObject json_message = jsonArray.getJSONObject(i);

                    //convert json objects into recipe objects, add them to recipe array
                    if (json_message != null) {
                        Recipe r = new Recipe();
                        r.setTitle(json_message.getString("title"));
                        r.setSource_url(json_message.getString("source_url"));
                        r.setF2f_url(json_message.getString("f2f_url"));
                        r.setImage_url(json_message.getString("image_url"));
                        r.setPublisher(json_message.getString("publisher"));
                        r.setPublisher_url(json_message.getString("publisher_url"));
                        recipeArray.add(r);
                    }
                }
            }catch(JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {

            //Call custom Array adapter using listView layout file and Array of Recipes
            RecipeAdapter r_adapter = new RecipeAdapter(Recipe_List.this, R.layout.listview_layout, recipeArray);
            ListView list = (ListView)findViewById(R.id.recipeList);
            list.setAdapter(r_adapter);

            //Detect when user selects an item in the list
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //select correlated Recipe based on id of selected list item
                    Recipe selectedRecipe = recipeArray.get(i);
                    Toast.makeText(Recipe_List.this, "Loading " + selectedRecipe.getTitle(), Toast.LENGTH_SHORT).show();

                    //pass all data from selected recipe to new intent using bundle, open recipe page
                    Intent recipePage = new Intent(Recipe_List.this, Recipe_Page.class);
                    Bundle extras = new Bundle();
                    extras.putString("title", selectedRecipe.getTitle());
                    extras.putString("f2f_url", selectedRecipe.getF2f_url());
                    extras.putString("image_url", selectedRecipe.getImage_url());
                    extras.putString("publisher", selectedRecipe.getPublisher());
                    extras.putString("publisher_url", selectedRecipe.getPublisher_url());
                    extras.putString("source_url", selectedRecipe.getSource_url());
                    recipePage.putExtras(extras);
                    startActivity(recipePage);
                }
            });
        }
    }

    //used to overwrite existing savedJson.text file
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("savedJson.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //used to read text from existing savedJson.text file
    private String readFromFile(){
        String file = "";
        try {
            InputStream inputStream = openFileInput("savedJson.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                file = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("ReadFromFile", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("ReadFromFile", "Cannot read file: " + e.toString());
        }

        return file;
    }
}
