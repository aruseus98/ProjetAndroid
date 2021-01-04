package com.licence.projetreveil.info.BDDAlarme;

public class playlist {
    int id;
    String auteur;
    String annee;
    String titre;

    // constructors
    public playlist() {

    }

    public playlist(String auteur) {
        this.auteur = auteur;
    }

    public playlist(int id, String auteur, String annee, String titre) {
        this.id = id;
        this.auteur = auteur;
        this.annee = annee;
        this.titre = titre;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setAnnee(String annee){
        this.annee = annee;
    }

    public void setTitre(String titre){
        this.titre = titre;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public String getAnnee() {
        return this.annee;
    }

    public String getTitre() {
        return this.titre;
    }
}
