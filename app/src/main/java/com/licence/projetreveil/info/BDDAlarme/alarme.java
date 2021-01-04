package com.licence.projetreveil.info.BDDAlarme;

public class alarme {
    int id;
    String horaire;
    String jour;
    String son;

    // constructors
    public alarme() {

    }

    public alarme(String horaire) {
        this.horaire = horaire;
    }

    public alarme(int id, String horaire, String jour, String son) {
        this.id = id;
        this.horaire = horaire;
        this.jour = jour;
        this.son = son;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public void setJour(String jour){
        this.jour = jour;
    }

    public void setSon(String son){
        this.son = son;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getHoraire() {
        return this.horaire;
    }

    public String getJour() {
        return this.jour;
    }

    public String getSon() {
        return this.son;
    }
}
