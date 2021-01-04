package com.licence.projetreveil.info.BDDAlarme;

public class utilisateur {
    int id;
    String utilisateur;

    //Constructeur
    public utilisateur(){

    }

    public utilisateur(String utilisateur, int id){
        this.utilisateur = utilisateur;
        this.id = id;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getNote() {
        return this.utilisateur;
    }
}
