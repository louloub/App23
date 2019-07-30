package com.example.app23.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app23.R;

public class OptionMenuActivity extends AppCompatActivity {

    private static final String TAG = "OptionMenuActivity" ;
    public static final String CITY_CHOICE = "MyCityChoice";

    //------------
    // OPTION MENU
    //------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_main,menu);
        return true;
    }

    //--------------
    // ALERTE DIALOG
    //--------------
    public void alertDialogCityChoice()
    {
        int checkedItems = 0;

        SharedPreferences settings = getSharedPreferences("MyCityChoice", Context.MODE_PRIVATE);
        String cityChoiceSharedPreferences = settings.getString("MyCityChoice",null);

        assert cityChoiceSharedPreferences != null;

        if (cityChoiceSharedPreferences==null) {
            checkedItems = 0;
        } else {

            if(cityChoiceSharedPreferences.equals("Montpellier"))
            {
                checkedItems = 0;
            }
            else if (cityChoiceSharedPreferences.equals("Toulouse")) {
                checkedItems = 1;
            } else if (cityChoiceSharedPreferences.equals("Marseille")) {
                checkedItems = 2;
            } else if (cityChoiceSharedPreferences.equals("Bordeaux")) {
                checkedItems = 3;
            } else if (cityChoiceSharedPreferences.equals("Nantes")) {
                checkedItems = 4;
            }
            else {
                checkedItems = 0;
            }
        }

        Log.d(TAG,"cityChoiceSharedPreferences = " +cityChoiceSharedPreferences);

        // Setup Alert builder
        android.support.v7.app.AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis ta ville préférentielle");
        // Ddd a radio button list
        String[] villes = {"Montpellier", "Toulouse", "Marseille", "Bordeaux", "Nantes"};

        myPopup.setSingleChoiceItems(villes, checkedItems, (dialog, which) -> {
        });
        myPopup.setPositiveButton("Valider", (dialogInterface, i) -> {
            ListView lw = ((AlertDialog)dialogInterface).getListView();
            Object checkedItemObject = lw.getAdapter().getItem(lw.getCheckedItemPosition());
            Toast.makeText(getApplicationContext(), "Tu as choisi " + checkedItemObject, Toast.LENGTH_LONG).show();
            String checkedItemString = checkedItemObject.toString();

            // SHARED PREFERECE INSTANCE
            // SharedPreferences settings = getSharedPreferences("MyCityChoice", Context.MODE_PRIVATE);
            Log.d(TAG,"setSingleChoiceItems settings = " +settings);

            // EDITOR SHARED PREFERENCE
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("MyCityChoice", checkedItemString);
            editor.apply();

            // checkedItems = 1 ;

            // FOR RELOAD CONTENT WHEN CITY IS CHOICE
            finish();
            startActivity(getIntent());
        });

        /*myPopup.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Vous avez cliquez sur Non", Toast.LENGTH_SHORT).show();
            }
        });*/

        myPopup.setCancelable(false);

        // create and show the alert dialog
        AlertDialog dialog = myPopup.create();
        myPopup.show();
    }

    //--------------------
    // ITEM IN OPTION MENU
    //--------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            /*case R.id.menu_activity_main_params:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
                return true;*/
            /*case R.id.menu_activity_main_search:
                Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
                return true;*/
            case R.id.menu_activity_logo:
                alertDialogCityChoice();
                Toast.makeText(this, "Choisis ta ville préférentielle ", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
