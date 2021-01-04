package com.licence.projetreveil.info.BDDAlarme;

public class citation {
    int id;
    String auteur;
    String annee;
    String texte;

    // constructors
    public citation() {

    }

    public citation(String auteur) {
        this.auteur = auteur;
    }

    public citation(int id, String auteur, String annee, String texte) {
        this.id = id;
        this.auteur = auteur;
        this.annee = annee;
        this.texte = texte;
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

    public void setTexte(String texte){
        this.texte = texte;
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

    public String getTexte() {
        return this.texte;
    }
}
