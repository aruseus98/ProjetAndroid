package com.licence.projetreveil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Create by SA E SILVA Eduardo on <DATE-ON-JOUR>
 */
public class AlarmReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            Toast.makeText(context, "C'est l'heure !!!",Toast.LENGTH_LONG).show();
            //On peut mettre ce que l'on veut. Vibreur, lecture d'un mp3 ou autre.
        } catch (Exception r) {
            Toast.makeText(context, "Erreur.",Toast.LENGTH_SHORT).show();
            r.printStackTrace();
        }
    }
}
