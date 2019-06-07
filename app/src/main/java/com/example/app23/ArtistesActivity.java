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
    private static final String URL_API = "http://pastebin.com/raw/Em972E5s";


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

        textView = findViewById(R.id.textView);

        loadArtistes2();
    }

    private void loadArtistes()
    {
        /*String string = "string";
        Log.d(TAG,"String loadArtistes = " + string);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_API, response ->
        {
            String string2 = "string2";
            Log.d(TAG,"String2 loadArtistes = " + string2);

            try
            {
                //converting the string to json array object
                JSONArray array = new JSONArray(response);

                //traversing through all the object
                for (int i = 0; i < array.length(); i++)
                {

                    //getting product object from json array
                    JSONObject product = array.getJSONObject(i);

                    //adding the product to product list
                    artistesList.add(new Artistes(
                            product.getInt("id"),
                            product.getString("title"),
                            product.getString("shortdesc"),
                            product.getString("image")
                    ));
                }

                //creating adapter object and setting it to recyclerview
                ArtistesAdapter adapter = new ArtistesAdapter(ArtistesActivity.this, artistesList);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> {
            //
        });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);*/
    }

    private void loadArtistes2()
    {
        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL_API, null,
                        response -> textView.setText("test"),
                        error -> {
                    // TODO: Handle error
                });

        String json = jsonObjectRequest.toString();

        Log.d(TAG, "Json to string = " +json);*/

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL_API, null, response -> {
                    try {
                        //converting the string to json array object
                        JSONArray array = new JSONArray(response);

                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            Log.d(TAG,"String loadArtistes = " + i);

                            //getting product object from json array
                            JSONObject artistes = array.getJSONObject(i);

                            String firstName = artistes.getString("firstname");
                            String lastName = artistes.getString("lastname");
                            String age = artistes.getString("age");

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

        requestQueue.add(jsonArrayRequest);

        /*JSONObject objet = objet;
        String json = objet.toString();*/
    }
}