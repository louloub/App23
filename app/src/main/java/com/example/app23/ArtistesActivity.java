package com.example.app23;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONStringer;

public class ArtistesActivity extends AppCompatActivity
{
    private static final String TAG = "ArtistesActiviy";

    //this is the JSON Data URL
    // private static final String URL_API = "http://192.168.64.2/ApiYourDJ.php";
    private static final String URL = "http://pastebin.com/raw/Em972E5s";

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
        setContentView(R.layout.activity_artistes);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        artistesList = new ArrayList<>();

        mContext = getApplicationContext();

        // textView = findViewById(R.id.textView);

        loadArtistes2();
    }

    private void loadArtistes2()
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

                    // Getting product object from json array
                    JSONObject artistesJsonObject = response.getJSONObject(i);

                    String firstName = artistesJsonObject.getString("firstname");
                    String lastName = artistesJsonObject.getString("lastname");
                    String age = artistesJsonObject.getString("age");

                    Artistes artiste = new Artistes(firstName,lastName,age);

                    artistesList.add(artiste);

                    //creating adapter object and setting it to recyclerview
                    ArtistesAdapter adapter = new ArtistesAdapter(ArtistesActivity.this, artistesList);
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