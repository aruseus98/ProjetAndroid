package com.licence.projetreveil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Create by SA E SILVA Eduardo on <DATE-ON-JOUR>
 */
public class Reveil extends Activity implements OnTimeSetListener {
    static final int ALARM_ID = 1234567;
    static Alarm alarm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Chargement des informations du reveil
        charger();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Affichage
        affichage();

        //Planification
        planifierAlarm();

    }
    private void affichage() {
        //Ici on a juste voulu créer un affichage de l'heure qui soit au format hh:mm.
        String heureReveil = "";
        heureReveil += alarm.getHeure().hour >10 ? alarm.getHeure().hour : "0" + alarm.getHeure().hour;
        heureReveil +=":";
        heureReveil += alarm.getHeure().minute >10 ? alarm.getHeure().minute : "0" + alarm.getHeure().minute;
        CheckBox ck_alarm = (CheckBox)findViewById(R.id.heure);
        ck_alarm.setText(heureReveil);
        ck_alarm.setChecked(alarm.isActive());
    }

    /*
     * changeHeure se déclenche automatiquement au click sur l'heure ou la CheckBox.
     * Active ou désactive le reveil.
     * Affiche un dialog pour choisir l'heure de reveil
     */
    public void changeHeure(View target){
        CheckBox ck_alarm = (CheckBox)findViewById(R.id.heure);

        //Si on active l'alarm alors on veut choisir l'heure.
        if(ck_alarm.isChecked()){
            TimePickerDialog dialog = new TimePickerDialog(this, this, alarm.getHeure().hour, alarm.getHeure().minute, true);
            dialog.show();
        }

        //On replanifie l'alarme.
        planifierAlarm();
    }

    /*
     * Chargement des informations du reveil.
     * Ici pour la sauvegarde on a simplement déserialiser l'objet Alarm.
     */
    public void charger(){
        alarm = null;
        try {
            ObjectInputStream alarmOIS= new ObjectInputStream(openFileInput("alarm.serial"));
            alarm = (Alarm) alarmOIS.readObject();
            alarmOIS.close();
        }
        catch(FileNotFoundException fnfe){
            alarm = new Alarm();
            alarm.setActive(true);
            Time t = new Time();
            t.hour = 7;
            t.minute = 30;
            alarm.setHeure(t);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    /*
     * Sauvegarde des informations du reveil
     */
    public void sauver(){
        try {
            ObjectOutputStream alarmOOS= new ObjectOutputStream(openFileOutput("alarm.serial",MODE_WORLD_WRITEABLE));
            alarmOOS.writeObject(alarm);
            alarmOOS.flush();
            alarmOOS.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*
     * Est activé après avoir valider l'heure du reveil.
     * En fait on sauvegarde simplement la nouvelle heure. On l'affiche comme il faut et on replanifie le reveil
     * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Time t = new Time();
        t.hour = hourOfDay;
        t.minute = minute;
        alarm.setHeure(t);
        affichage();
        planifierAlarm();
    }

    /*
     * Job de planification du reveil
     */
    private void planifierAlarm() {
        //Récupération de l'instance du service AlarmManager.
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //On instancie l'Intent qui va être appelé au moment du reveil.
        Intent intent = new Intent(this, AlarmReceiver.class);

        //On créer le pending Intent qui identifie l'Intent de reveil avec un ID et un/des flag(s)
        PendingIntent pendingintent = PendingIntent.getBroadcast(this, ALARM_ID, intent, 0);

        //On annule l'alarm pour replanifier si besoin
        am.cancel(pendingintent);

        //La on va déclencher un calcul pour connaitre le temps qui nous sépare du prochain reveil.
        Calendar reveil  = Calendar.getInstance();
        reveil.set(Calendar.HOUR_OF_DAY, alarm.getHeure().hour);
        reveil.set(Calendar.MINUTE, alarm.getHeure().minute);
        if(reveil.compareTo(Calendar.getInstance()) == -1)
            reveil.add(Calendar.DAY_OF_YEAR, 1);

        Calendar cal = Calendar.getInstance();
        reveil.set(Calendar.SECOND, 0);
        cal.set(Calendar.SECOND, 0);
        long diff = reveil.getTimeInMillis() - cal.getTimeInMillis();

        //On ajoute le reveil au service de l'AlarmManager
        am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis() + diff, pendingintent);
        Toast.makeText(this, "Alarme programm&eacute; le " +
                reveil.get(Calendar.DAY_OF_MONTH) + " à " +
                reveil.get(Calendar.HOUR_OF_DAY) + ":" + +
                reveil.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
    }
}
