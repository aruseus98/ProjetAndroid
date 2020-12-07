package com.licence.projetreveil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

import static android.app.ProgressDialog.show;


//Première version à revoir
public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        //Chargement des informations du réveil
        charger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        affichage();

        planifierAlarm();
    }

    //Fonction qui réactive le réveil si le téléphone est reboot en sauvegardant les informations
    public static class Alarm implements Serializable{
        private static final long serialVersionUID = 1L;
        private Time heure;
        private boolean active;

        public Time getHeure(){
            return heure;
        }

        public void setHeure(Time heure){
            this.heure = heure;
        }

        public boolean isActive()
        {
            return active;
        }

        public void setActive(boolean active){
            this.active = active;
        }
    }

    //Charger les informations du réveil
    public void charger(){
        Alarm alarm = null;
        try{
            ObjectInputStream alarmOIS = new ObjectInputStream(openFileInput("alarm.serial"));
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sauver(){
        try {
            ObjectOutputStream alarmOOS= new ObjectOutputStream(openFileOutput(“alarm.serial”,MODE_WORLD_WRITEABLE));
            alarmOOS.writeObject(alarm);
            alarmOOS.flush();
            alarmOOS.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void affichage() {
    //Affichage de l’heure qui soit au format hh:mm.
        String heureReveil = “”;
        heureReveil += alarm.getHeure().hour >10 ? alarm.getHeure().hour : “0” + alarm.getHeure().hour;
        heureReveil +=”:”;
        heureReveil += alarm.getHeure().minute >10 ? alarm.getHeure().minute : “0” + alarm.getHeure().minute;
        CheckBox ck_alarm = (CheckBox)findViewById(R.id.heure);
        ck_alarm.setText(heureReveil);
        ck_alarm.setChecked(alarm.isActive());
    }

    public void changeHeure(View target){
        CheckBox ck_alarm = (CheckBox)findViewById(R.id.heure);
    //On choisit l'heure si on active l'alarme
        if(ck_alarm.isChecked()){
            TimePickerDialog dialog = new TimePickerDialog(this, this, alarm.getHeure().hour, alarm.getHeure().minute, true);
            dialog.show();
        }
    //On replanifie l’alarme.
        planifierAlarm();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Time t = new Time();
        t.hour = hourOfDay;
        t.minute = minute;
        alarm.setHeure(t);
        affichage();
        planifierAlarm();
    }

    private void planifierAlarm() {
    //Récupération de l’instance du service AlarmManager.
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    //On instancie l’Intent qui va être appelé au moment du reveil.
        Intent intent = new Intent(this, AlarmReceiver.class);

    //On créer le pending Intent qui identifie l’Intent de reveil avec un ID et un/des flag(s)
        PendingIntent pendingintent = PendingIntent.getBroadcast(this, ALARM_ID, intent, 0);

    //On annule l’alarm pour replanifier si besoin
        am.cancel(pendingintent);

        if(alarm.isActive()){
    //on va déclencher un calcul pour connaitre le temps qui nous sépare du prochain reveil.
            Calendar reveil = Calendar.getInstance();
            reveil.set(Calendar.HOUR_OF_DAY, alarm.getHeure().hour);
            reveil.set(Calendar.MINUTE, alarm.getHeure().minute);
            if(reveil.compareTo(Calendar.getInstance()) == -1)
                reveil.add(Calendar.DAY_OF_YEAR, 1);
            Calendar cal = Calendar.getInstance();
            reveil.set(Calendar.SECOND, 0);
            cal.set(Calendar.SECOND, 0);
            long diff = reveil.getTimeInMillis() – cal.getTimeInMillis();

    //On ajoute le reveil au service de l’AlarmManager
            am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis() + diff, pendingintent);
            Toast.makeText(this, "Alarme programmé le " + reveil.get(Calendar.DAY_OF_MONTH) + " à " + reveil.get(Calendar.HOUR_OF_DAY) + “:” + reveil.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        }
    }

    public class AlarmReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Toast.makeText(context, "C’est l’heure !!!",Toast.LENGTH_LONG).show();
    //On peut mettre ce que l’on veut. Vibreur, lecture d’un mp3 ou autre.
            }
            catch (Exception r) {
                Toast.makeText(context, "Erreur.",Toast.LENGTH_SHORT).show();
                r.printStackTrace();
            }
        }
    }

    public class AlarmBootReceiver extends BroadcastReceiver{
        Alarm alarm;
        Context context;
        @Override
        public void onReceive(Context context, Intent intent) {
            this.context = context;
            charger();
            planifierAlarm();
        }
}