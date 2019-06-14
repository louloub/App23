package com.example.app23;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DISABLE TILE TOOLBAR
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // BUTTON PODCAST
        Button btn_podcast = findViewById(R.id.carre_podcast);
        btn_podcast.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openPodcastActivity();
            }
        });

        // BUTTON ARTISTES
        Button btn_artistes = findViewById(R.id.carre_artistes);
        btn_artistes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openArtistesActivity();
            }
        });

        // BUTTON ARTICLES
        Button btn_articles = findViewById(R.id.carre_articles);
        btn_articles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openArticlesActivity();
            }
        });

        // ALERTE DIALOG
        alertDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //----------------
    // BUTTONS TOOLBAR
    //----------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_search:
                Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //--------
    // INTENT
    //--------
    public void openPodcastActivity() {
        Intent intent = new Intent(this, PodcastActivity.class);
        startActivity(intent);
    }

    public void openArtistesActivity() {
        Intent intent = new Intent(this, ArtistesListActivity.class);
        startActivity(intent);
    }

    public void openArticlesActivity() {
        Intent intent = new Intent(this, ArticlesListActivity.class);
        startActivity(intent);
    }

    //--------------
    // ALERTE DIALOG
    //--------------
    public void alertDialog ()
    {
        android.support.v7.app.AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Salut");
        myPopup.setMessage("Salut les gens c'est ton téléphone qui te parle");
        myPopup.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Vous avez cliquez sur Oui", Toast.LENGTH_SHORT).show();
            }
        });

        myPopup.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Vous avez cliquez sur Non", Toast.LENGTH_SHORT).show();
            }

        });
        myPopup.setCancelable(false);

        myPopup.show();


    }
}
