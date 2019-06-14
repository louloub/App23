package com.example.app23;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ArtistesListActivity extends AppCompatActivity
{
    private static final String TAG = "ArtistesActiviy";

    // this is the JSON Data URL

    // private static final String URL = "http://192.168.64.2/artistes.json";
    // private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes.json";
    private static final String URL = "https://yourdj.fr/themes/yourdj/layouts/page/artistes2.json";
    // private static final String URL_API = "http://192.168.64.2/ApiYourDJ.php";
    // private static final String URL = "http://pastebin.com/raw/Em972E5s";

    //a list to store all the products
    List<Artistes> artistesList;

    //the recyclerview
    RecyclerView recyclerView;
    private Context mContext;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes_list);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        artistesList = new ArrayList<>();

        mContext = getApplicationContext();

        // textView = findViewById(R.id.textView);

        loadArtistes();

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //------------
    // OPTION MENU
    //------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //------------------------
    // LOAD ARTISTES FROM JSON
    //------------------------
    private void loadArtistes()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayArtistesRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, response ->
        {
            try {
                Log.d(TAG, " JsonArrayRequest in try jsonArrayArtistesRequest " +response);

                // Browse request contain json
                for (int i = 0; i < response.length(); i++) {

                    Log.d(TAG, " JsonArrayRequest in for " +i);

                    // Getting object from json array
                    JSONObject artistesJsonObject = response.getJSONObject(i);

                    String firstName = artistesJsonObject.getString("name");
                    Log.d(TAG, " JsonArrayRequest in for " +firstName);

                    String lastName = artistesJsonObject.getString("bio");
                    String age = artistesJsonObject.getString("age");

                    Artistes artiste = new Artistes(firstName,lastName,age);

                    artistesList.add(artiste);

                    //creating adapter object and setting it to recyclerview
                    ArtistesAdapter adapter = new ArtistesAdapter(ArtistesListActivity.this, artistesList);
                    recyclerView.setAdapter(adapter);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
                },
                        error -> {
                            // Do something when error occurred
                        }
                );

        requestQueue.add(jsonArrayArtistesRequest);

        /*JSONObject objet = objet;
        String json = objet.toString();*/
    }
}