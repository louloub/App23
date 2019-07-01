package com.example.app23.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app23.R;

public class OptionMenuActivity extends AppCompatActivity {

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
        // Setup Alert builder
        android.support.v7.app.AlertDialog.Builder myPopup = new AlertDialog.Builder(this);
        myPopup.setTitle("Choisis ta ville préférentielle");
        // Ddd a radio button list
        String[] villes = {"Montpellier", "Toulouse", "Marseille", "Bordeaux", "Nantes"};
        int checkedItems = 0;
        myPopup.setSingleChoiceItems(villes, checkedItems, (dialog, which) -> {
        });
        myPopup.setPositiveButton("Valider", (dialogInterface, i) -> {
            ListView lw = ((AlertDialog)dialogInterface).getListView();
            Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
            Toast.makeText(getApplicationContext(), "Tu as choisi " + checkedItem, Toast.LENGTH_LONG).show();
            // TODO : utiliser "checkedItem" pour le choix du contenu par ville
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_activity_main_params:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_main_search:
                Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_activity_logo:
                alertDialogCityChoice();
                Toast.makeText(this, "Choisis ta ville préférentielle ", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
